package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.entity.Video;
import com.baizhi.dao.VideoDao;
import com.baizhi.po.VideoPo;
import com.baizhi.repository.VideoRepository;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunOssUtil;
import com.baizhi.util.InterceptVideoCoverUtil;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.VideoVo;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * (Video)表服务实现类
 *
 * @author makejava
 * @since 2020-07-05 10:38:16
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoDao videoDao;
    @Resource
    HttpServletRequest request;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    VideoRepository videoRepository;

    /**
     * 分页查询多条数据
     *
     * @param rows 每页显示条数
     * @param page 当前页
     * @return 实例对象
     */
    @Override
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        /*
         * 1. 计算起始下标
         * 2. 计算总页数
         *
         * page: 当前页
         * rows: 查询到的数据
         * total: 总页数
         * records: 总条数
         * */
        //起始条数
        Integer start = (page - 1) * rows;
        //查询总条数
        Integer records = videoDao.queryCount();
        //总页数
        Integer total = null;
        if (records % rows == 0) {
            total = records / rows;
        } else {
            total = records / rows + 1;
        }
        List<Video> videos = videoDao.queryByPage(start, rows);
        map.put("page", page);
        map.put("rows", videos);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    /**
     * 上传视频
     *
     * @param videoPath MultipartFile类型的文件
     * @param id
     * @return
     */
    @Override
    @DelCache
    @Transactional
    public void videoUpload(MultipartFile videoPath, String id) {
        //文件上传
        //3.获取文件名
        String filename = videoPath.getOriginalFilename();
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //2.判断上传文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();//创建文件夹
        }
        //给文件拼接时间戳
        String newName = System.currentTimeMillis() + "-" + filename;
        //设置目录
        String menuName = "video/" + newName;
        //把文件变成数组
        byte[] fil = null;
        try {
            fil = videoPath.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4.文件上传
        AliyunOssUtil.uploadByteFile("hang-yingx", menuName, fil);
        //2.截取视频封面
        //2.1.拆分字符串
        //newName=1594000325773-树叶.mp4
        String coverName = newName.substring(0, newName.lastIndexOf("."));
        //2.2.拼接封面名
        String coverNames = coverName + ".jpg";
        String videoFilePath = "https://hang-yingx.oss-cn-beijing.aliyuncs.com/" + menuName;
        String localFilePath = realPath + "\\" + coverNames;
        try {
            InterceptVideoCoverUtil.fetchFrame(videoFilePath, localFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * 3.上传视频封面至阿里云
         * 参数：
         *   netPath:网络路径
         *   bucketName：存储空间名
         *   fileName：指定文件名
         * */
        AliyunOssUtil.uploadFile("hang-yingx", "cover/" + coverNames, localFilePath);
        //5.数据修改
        Video video = new Video();
        video.setCoverPath("https://hang-yingx.oss-cn-beijing.aliyuncs.com/cover/" + coverNames); //设置封面
        video.setId(id);
        video.setVideoPath(videoFilePath);
        int i = videoDao.update(video);
        if (i == 1) {
            //添加索引
            Video searchVideo = videoDao.queryById(id);
            videoRepository.save(searchVideo);
        }

        //6.删除本地文件
        File files = new File(localFilePath);
        //判断是一个文件，并且文件存在
        if (files.exists() && files.isFile()) {
            //删除文件
            boolean isDel = files.delete();
        }
    }

    /**
     * 新增类别
     *
     * @param video 实例对象
     * @return 实例对象
     */
    @Override
    @DelCache
    @Transactional
    public String add(Video video) {
        String uuid = UUIDUtil.getUUID();
        video.setId(uuid);
        videoDao.add(video);
        return uuid;
    }

    /**
     * 修改类别数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    @Override
    @DelCache
    @Transactional
    public void update(Video video) {
        if (video.getVideoPath() == "") {
            video.setVideoPath(null);
        }
        videoDao.update(video);
        //修改索引
        videoRepository.save(video);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @DelCache
    @Transactional
    public HashMap<String, Object> deleteById(String id) {
        HashMap<String, Object> map = new HashMap<>();
        Video video = videoDao.queryById(id);
        //获取视频和封面路径
        String path = video.getVideoPath();
        String cover = video.getCoverPath();
        //截取视频封面名称
        String videoName = path.replace("https://hang-yingx.oss-cn-beijing.aliyuncs.com/", "");
        String coverName = cover.replace("https://hang-yingx.oss-cn-beijing.aliyuncs.com/", "");
        /*
         * 2.删除视频
         *   删除封面
         * 参数：
         *   bucketName：存储空间名
         *   fileName：文件名
         * */
        try {
            videoDao.deleteById(id);
            AliyunOssUtil.deleteFile("hang-yingx", videoName);
            AliyunOssUtil.deleteFile("hang-yingx", coverName);
            //删除索引
            videoRepository.delete(video);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("msg", "error");
        }
        return map;
    }

    /**
     * 查询前台首页数据
     *
     * @return List<VideoVo>
     */
    @Override
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<VideoVo> queryByReleaseTime() {
        List<VideoPo> videoPos = videoDao.queryByReleaseTime();
        List<VideoVo> videoVos = new ArrayList<>();
        for (VideoPo videoPo : videoPos) {
            videoVos.add(new VideoVo(
                    videoPo.getVId(),
                    videoPo.getVTitle(),
                    videoPo.getVCover(),
                    videoPo.getVPath(),
                    videoPo.getVUploadTime(),
                    videoPo.getVDescription(),
                    20,
                    videoPo.getCateName(),
                    videoPo.getHeadImg()
            ));
        }
        return videoVos;
    }

    /**
     * 首页搜索视频
     *
     * @return List<VideoVo>
     */
    @Override
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<VideoVo> queryByLikeVideoName(String content) {
        Video video = new Video();
        video.setBrief(content);
        List<VideoPo> videoPos = videoDao.queryByLikeVideoName(video);
        List<VideoVo> videoVos = new ArrayList<>();
        for (VideoPo videoPo : videoPos) {
            videoVos.add(new VideoVo(
                    videoPo.getVId(),
                    videoPo.getVTitle(),
                    videoPo.getVCover(),
                    videoPo.getVPath(),
                    videoPo.getVUploadTime(),
                    videoPo.getVDescription(),
                    20,
                    videoPo.getCateName(),
                    videoPo.getCategoryId(),
                    videoPo.getUserId(),
                    videoPo.getUserName()
            ));
        }
        return videoVos;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public Video queryById(String id) {
        return this.videoDao.queryById(id);
    }

    /**
     * 检索视频
     *
     * @return List<Video>
     */
    @Override
    public List<Video> searchVideo(String content) {
        //设置查询条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("video")
                .withTypes("doc")
                .withQuery(QueryBuilders.queryStringQuery(content).field("title"))//指定查询条件和字段
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Video.class);

    }


}