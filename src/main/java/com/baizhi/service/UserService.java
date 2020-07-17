package com.baizhi.service;

import com.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * (YxUser)表服务接口
 *
 * @author makejava
 * @since 2020-07-01 22:21:30
 */
public interface UserService {

    /**
     * 分页查询多条数据
     *
     * @param rows 每页显示条数
     * @param page 当前页
     * @return 实例对象
     */
    Map<String, Object> queryByPage(Integer rows, Integer page);

    /**
     * 上传文件
     *
     * @param headImg MultipartFile类型的文件
     * @param id
     * @return
     */
    void uploadAli(MultipartFile headImg, String id);

    /**
     * 每月注册人数
     *
     * @return
     */
    Map<String, Object> queryecharts();


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}