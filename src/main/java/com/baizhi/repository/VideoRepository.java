package com.baizhi.repository;

import com.baizhi.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

//泛型   <操作对象类型,序列化主键的类型>
public interface VideoRepository extends ElasticsearchRepository<Video, String> {
    List<Video> findByTitle(String title);

    List<Video> findByTitleAndBrief(String title, String brief);
}
