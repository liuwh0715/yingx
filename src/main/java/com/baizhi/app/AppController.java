package com.baizhi.app;

import com.baizhi.common.CommonResult;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import com.baizhi.service.VideoService;
import com.baizhi.util.AliyunPhoneSendUtil;
import com.baizhi.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppController {
    @Resource
    private VideoService videoService;

    @ResponseBody
    @RequestMapping("getPhoneCode")
    public Object getPhoneCode(String phone, HttpSession session) {
        String randCode = AliyunPhoneSendUtil.getRandCode(6);
        //储存验证码
        session.setAttribute("randCode", randCode);
        //发送验证码
        String message = AliyunPhoneSendUtil.sendPhoneCode(phone, randCode);
        //判断是否发送成功
        if (message.equals("发送成功")) {
            return new CommonResult().success(phone, message, "100");
        } else {
            return new CommonResult().failed(null, message, "104");
        }
    }

    /**
     * 查询前台首页数据
     *
     * @return List<Video>
     */
    @ResponseBody
    @RequestMapping("queryByReleaseTime")
    public Object queryByReleaseTime() {
        List<Video> videos = null;
        try {
            List<VideoVo> videoVos = videoService.queryByReleaseTime();
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     * 首页搜索视频
     */
    @ResponseBody
    @RequestMapping("queryByLikeVideoName")
    public Object queryByLikeVideoName(String content) {
        List<Video> videos = null;
        try {
            List<VideoVo> videoVos = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }


}
