package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * (YxCategory)表服务接口
 *
 * @author makejava
 * @since 2020-07-02 18:25:39
 */
public interface CategoryService {

    /**
     * 分页查询数据
     *
     * @param page   当前页
     * @param rows   每页显示条数
     * @param levels 每页显示条数
     * @return 多条数据
     */
    Map<String, Object> queryByPage(Integer page, Integer rows, Integer levels);

    /**
     * 分页查询数据
     *
     * @param page     当前页
     * @param rows     每页显示条数
     * @param parentId 父级id
     * @return 多条数据
     */
    Map<String, Object> queryTwoByPage(Integer page, Integer rows, String parentId);

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    void add(Category category);

    /**
     * 修改类别数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    void update(Category category);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    void deleteById(String id);

    /**
     * 通过父级ID查询
     *
     * @param parentId 父级id
     * @return 集合
     */
    List<Category> queryByParentId(String parentId);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Category queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Category> queryAllByLimit(int offset, int limit);


}