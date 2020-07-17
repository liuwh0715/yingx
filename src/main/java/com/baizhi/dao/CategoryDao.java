package com.baizhi.dao;

import com.baizhi.entity.Category;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (YxCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-02 18:25:39
 */
public interface CategoryDao {
    /**
     * 通过ID查询数据
     *
     * @param levels 级别
     * @return 总条数
     */
    Integer queryCount(Integer levels);


    /**
     * @param start  起始条数
     * @param rows   每页显示条数
     * @param levels 级别
     * @return 集合
     */
    List<Category> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows, @Param("levels") Integer levels);

    /**
     * 通过ID查询数据
     *
     * @param parentId 父级Id
     * @return 总条数
     */
    Integer queryTwoCount(String parentId);

    /**
     * @param start    起始条数
     * @param rows     每页显示条数
     * @param parentId 父级Id
     * @return 集合
     */
    List<Category> queryTwoByPage(@Param("start") Integer start, @Param("rows") Integer rows, @Param("parentId") String parentId);

    /**
     * 新增数据
     *
     * @param category 实例对象
     */
    void add(Category category);

    /**
     * 修改一级类别数据
     *
     * @param category 实例对象
     */
    void update(Category category);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
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
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Category> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param category 实例对象
     * @return 对象列表
     */
    List<Category> queryAll(Category category);


}