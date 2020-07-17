package com.baizhi.service;

import com.baizhi.entity.Category;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import com.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (YxVideo)表服务接口
 *
 * @author makejava
 * @since 2020-07-05 10:38:16
 */
public interface VideoService {
    /**
     * 分页查询多条数据
     *
     * @param rows 每页显示条数
     * @param page 当前页
     * @return 实例对象
     */
    Map<String, Object> queryByPage(Integer rows, Integer page);

    /**
     * 上传视频
     *
     * @param videoPath MultipartFile类型的文件
     * @param id
     * @return
     */
    void videoUpload(MultipartFile videoPath, String id);

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return uuid
     */
    String add(Video video);

    /**
     * 修改类别数据
     *
     * @param video 实例对象
     * @return 实例对象
     */
    void update(Video video);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    HashMap<String, Object> deleteById(String id);

    /**
     * 查询前台首页数据
     *
     * @return List<VideoVo>
     */
    List<VideoVo> queryByReleaseTime();

    /**
     * 首页搜索视频
     *
     * @return List<VideoVo>
     */
    List<VideoVo> queryByLikeVideoName(String content);


    /**
     * 检索视频
     *
     * @return List<Video>
     */
    List<Video> searchVideo(String content);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Video queryById(String id);


}