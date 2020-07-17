package com.baizhi.service.impl;

import com.baizhi.entity.Category;
import com.baizhi.dao.CategoryDao;
import com.baizhi.service.CategoryService;
import com.baizhi.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (YxCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-07-02 18:25:40
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryDao categoryDao;

    /**
     * 分页查询数据
     *
     * @param page 当前页
     * @param rows 每页显示条数
     * @return 多条数据
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(Integer page, Integer rows, Integer levels) {
        /*
         * 1. 计算起始下标
         * 2. 计算总页数
         *
         * page: 当前页
         * rows: 查询到的数据
         * total: 总页数
         * records: 总条数
         * */
        Map<String, Object> map = new HashMap<>();
        //起始条数
        Integer start = (page - 1) * rows;
        //查询总条数
        Integer records = categoryDao.queryCount(levels);
        //总页数
        Integer total = null;
        if (records % rows == 0) {
            total = records / rows;
        } else {
            total = records / rows + 1;
        }

        List<Category> categories = categoryDao.queryByPage(start, rows, levels);
        map.put("page", page);
        map.put("rows", categories);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryTwoByPage(Integer page, Integer rows, String parentId) {
        Map<String, Object> map = new HashMap<>();
        //起始条数
        Integer start = (page - 1) * rows;
        //查询总条数
        Integer records = categoryDao.queryTwoCount(parentId);
        //总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Category> categories = categoryDao.queryTwoByPage(start, rows, parentId);
        map.put("page", page);
        map.put("rows", categories);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    /**
     * 新增类别
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void add(Category category) {
        String uuid = UUIDUtil.getUUID();
        category.setId(uuid);
        if (category.getParentId() != null) {
            category.setLevels(2);
        } else {
            category.setLevels(1);
        }
        categoryDao.add(category);
    }

    /**
     * 修改类别数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void update(Category category) {
        categoryDao.update(category);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public void deleteById(String id) {
        categoryDao.deleteById(id);
    }

    /**
     * 通过父级ID查询
     *
     * @param parentId 父级id
     * @return 集合
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryByParentId(String parentId) {
        return categoryDao.queryByParentId(parentId);

    }


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Category queryById(String id) {
        return this.categoryDao.queryById(id);
    }


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Category> queryAllByLimit(int offset, int limit) {
        return this.categoryDao.queryAllByLimit(offset, limit);
    }


}