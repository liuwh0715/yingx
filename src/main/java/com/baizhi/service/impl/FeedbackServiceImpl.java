package com.baizhi.service.impl;

import com.baizhi.entity.Feedback;
import com.baizhi.dao.FeedbackDao;
import com.baizhi.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (YxFeedback)表服务实现类
 *
 * @author makejava
 * @since 2020-07-06 22:46:29
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackDao feedbackDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Feedback queryById(String id) {
        return this.feedbackDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Feedback> queryAllByLimit(int offset, int limit) {
        return this.feedbackDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    @Override
    public Feedback insert(Feedback feedback) {
        this.feedbackDao.insert(feedback);
        return feedback;
    }

    /**
     * 修改数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    @Override
    public Feedback update(Feedback feedback) {
        this.feedbackDao.update(feedback);
        return this.queryById(feedback.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.feedbackDao.deleteById(id) > 0;
    }
}