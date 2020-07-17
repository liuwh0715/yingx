package com.baizhi.service.impl;

import com.baizhi.entity.Admin;
import com.baizhi.dao.AdminDao;
import com.baizhi.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2020-06-30 17:44:42
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao AdminDao;

    /**
     * 通过ID查询单条数据
     *
     * @param admin
     * @return 实例对象
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin query(Admin admin) {
        Admin ad = AdminDao.query(admin.getUsername());
        if (ad == null) {
            throw new RuntimeException("账号不存在");
        } else {
            if (ad.getPassword().equals(admin.getPassword())) {
                return ad;
            } else {
                throw new RuntimeException("密码错误");
            }
        }
    }

//    /**
//     * 查询多条数据
//     *
//     * @param offset 查询起始位置
//     * @param limit 查询条数
//     * @return 对象列表
//     */
//    @Override
//    public List<Admin> queryAllByLimit(int offset, int limit) {
//        return this.AdminDao.queryAllByLimit(offset, limit);
//    }

    /**
     * 新增数据
     *
     * @param yxAdmin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin insert(Admin yxAdmin) {
        this.AdminDao.insert(yxAdmin);
        return yxAdmin;
    }

    @Override
    public Admin update(Admin yxAdmin) {
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
        return this.AdminDao.deleteById(id) > 0;
    }
}