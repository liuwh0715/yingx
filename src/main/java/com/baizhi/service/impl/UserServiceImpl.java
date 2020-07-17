package com.baizhi.service.impl;

import com.baizhi.entity.User;
import com.baizhi.dao.UserDao;
import com.baizhi.po.BoysPo;
import com.baizhi.service.UserService;
import com.baizhi.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (YxUser)表服务实现类
 *
 * @author makejava
 * @since 2020-07-01 22:21:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 分页查询多条数据
     *
     * @param rows 每页显示条数
     * @param page 当前页
     * @return 实例对象
     */
    @Override
    public Map<String, Object> queryByPage(Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        /*
         * 1. 计算起始下标
         * 2. 计算总页数
         *
         * page: 当前页
         * rows: 查询到的数据
         * total: 总页数
         * records: 总条数
         * */
        //起始条数
        Integer start = (page - 1) * rows;
        //查询总条数
        Integer records = userDao.queryCount();
        //总页数
        Integer total = records % rows == 0 ? records % rows : records % rows + 1;
        List<User> users = userDao.queryByPage(start, rows);
        map.put("page", page);
        map.put("rows", users);
        map.put("total", total);
        map.put("records", records);
        return map;
    }

    /**
     * 上传文件
     *
     * @param headImg MultipartFile类型的文件
     * @param id
     * @return
     */
    @Override
    public void uploadAli(MultipartFile headImg, String id) {

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryecharts() {
//        List<BoysPo> boysPo = userDao.queryByMonthBoys();
//        UserVo userVo = new UserVo();
//        Arrays month = userVo.getMonth();
//        List<String> monthList = month.asList();
//        Arrays boys = userVo.getBoys();
//        List<Object> boysList = boys.asList();
//        for (BoysPo boy : boysPo) {
//            boysList.add(boy.getBoys());
//            monthList.add(boy.getMonth());
//        }

        return null;
    }


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.userDao.deleteById(id) > 0;
    }
}