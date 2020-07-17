package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-07-01 22:21:31
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 分页查询多条数据
     *
     * @param rows 每页显示条数
     * @param page 当前页
     * @return 实例对象
     */
    @GetMapping("queryByPage")
    public Map<String, Object> queryByPage(Integer rows, Integer page) {
        return userService.queryByPage(rows, page);
    }

    /**
     * 每月注册人数
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("echarts")
    public Map<String, Object> queryecharts() {
        userService.queryecharts();
        return null;
    }

}