package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.Video;
import com.baizhi.po.VideoPo;
import com.baizhi.vo.VideoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (YxVideo)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-05 10:38:16
 */
public interface VideoDao {
    /**
     * 通过ID查询数据
     *
     * @return 总条数
     */
    Integer queryCount();

    /**
     * @param start 起始条数
     * @param rows  每页显示条数
     * @return 集合
     */
    List<Video> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    /**
     * 修改数据
     *
     * @param video 实例对象
     * @return 影响行数
     */
    int update(Video video);

    /**
     * 新增数据
     *
     * @param video 实例对象
     * @return 影响行数
     */
    int add(Video video);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    void deleteById(String id);

    /**
     * 查询前台首页数据
     *
     * @return List<VideoPo>
     */
    List<VideoPo> queryByReleaseTime();

    /**
     * 首页搜索视频
     *
     * @return List<VideoPo>
     */
    List<VideoPo> queryByLikeVideoName(Video video);

    /**
     * 查询所有
     */
    List<Video> queryAll();


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Video queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Video> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param video 实例对象
     * @return 对象列表
     */
    List<Video> queryAll(Video video);


}