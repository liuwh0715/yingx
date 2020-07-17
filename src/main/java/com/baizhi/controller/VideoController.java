package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunPhoneSendUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (YxVideo)表控制层
 *
 * @author makejava
 * @since 2020-07-05 10:38:16
 */
@RestController
@RequestMapping("video")
public class VideoController {
    /**
     * 服务对象
     */
    @Resource
    private VideoService videoService;


    /**
     * 分页查询多条数据
     *
     * @param rows 每页显示条数
     * @param page 当前页
     * @return 实例对象
     */
    @GetMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer rows, Integer page) {
        return videoService.queryByPage(rows, page);
    }

    /**
     * 增删改操作
     *
     * @param video 类别的对象
     * @param oper  增删改的操作
     * @return 多条数据
     */
    @RequestMapping("edit")
    @ResponseBody
    public Object edit(Video video, String oper, String[] id) {
        String uuid = null;
        if (oper.equals("add")) {
            uuid = videoService.add(video);
        }
        if (oper.equals("edit")) {
            videoService.update(video);
        }
        if (oper.equals("del")) {
            for (String s : id) {
                HashMap<String, Object> map = videoService.deleteById(s);
                return map;
            }
        }
        return uuid;
    }

    /**
     * 上传视频
     *
     * @param videoPath MultipartFile类型的文件
     * @param id
     * @return
     */
    @RequestMapping("videoUpload")
    public void videoUpload(MultipartFile videoPath, String id) {
        videoService.videoUpload(videoPath, id);
    }

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return
     */
    @ResponseBody
    @RequestMapping("sendPhoneCode")
    public Map<String, Object> sendPhoneCode(String phone) {
        String randCode = AliyunPhoneSendUtil.getRandCode(6);
        String s = AliyunPhoneSendUtil.sendPhoneCode(phone, randCode);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", s);
        return map;
    }

    /**
     * 检索视频
     *
     * @return List<Video>
     */
    @ResponseBody
    @RequestMapping("searchVideo")
    public List<Video> searchVideo(String content) {
        return videoService.searchVideo(content);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Video selectOne(String id) {
        return this.videoService.queryById(id);
    }

}