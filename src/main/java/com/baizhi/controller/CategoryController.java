package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (YxCategory)表控制层
 *
 * @author makejava
 * @since 2020-07-02 18:25:40
 */
@Controller
@RequestMapping("category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 分页查询数据
     *
     * @param page 当前页
     * @param rows 每页显示条数
     * @return 多条数据
     */
    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Integer levels = 1;
        return categoryService.queryByPage(page, rows, levels);
    }

    /**
     * 分页查询数据
     *
     * @param page 当前页
     * @param rows 每页显示条数
     * @return 多条数据
     */
    @RequestMapping("queryTwoByPage")
    @ResponseBody
    public Map<String, Object> queryTwoByPage(Integer page, Integer rows, String parentId) {
        return categoryService.queryTwoByPage(page, rows, parentId);
    }

    /**
     * 增删改操作
     *
     * @param cate 类别的对象
     * @param oper 增删改的操作
     * @return 多条数据
     */
    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> edit(Category cate, String oper, String[] id) {
        Map<String, Object> map = new HashMap<>();
        if (oper.equals("add")) {
            categoryService.add(cate);
        }
        if (oper.equals("edit")) {
            categoryService.update(cate);
        }
        if (oper.equals("del")) {
            if (cate.getParentId() != null) {
                for (String s : id) {
                    categoryService.deleteById(s);
                }
            } else {
                for (String s : id) {
                    List<Category> categories = categoryService.queryByParentId(s);
                    if (!categories.isEmpty()) {
                        map.put("msg", "error");
                        return map;
                    } else {
                        categoryService.deleteById(s);
                    }
                }
            }
        }
        return null;
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Category selectOne(String id) {
        return this.categoryService.queryById(id);
    }

}