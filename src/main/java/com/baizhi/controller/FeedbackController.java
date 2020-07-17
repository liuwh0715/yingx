package com.baizhi.controller;

import com.baizhi.entity.Feedback;
import com.baizhi.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (YxFeedback)表控制层
 *
 * @author makejava
 * @since 2020-07-06 22:46:29
 */
@RestController
@RequestMapping("feedback")
public class FeedbackController {
    /**
     * 服务对象
     */
    @Resource
    private FeedbackService feedbackService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Feedback selectOne(String id) {
        return this.feedbackService.queryById(id);
    }

}