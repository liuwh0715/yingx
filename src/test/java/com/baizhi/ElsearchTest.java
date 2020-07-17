package com.baizhi;

import com.baizhi.dao.VideoDao;
import com.baizhi.entity.Video;
import com.baizhi.repository.VideoRepository;
import jdk.nashorn.internal.objects.NativeRegExpExecResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.bind.SchemaOutputResolver;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElsearchTest {
    @Resource
    VideoRepository videoRepository;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    VideoDao videoDao;

    //添加所索引
    @Test
    public void method() {
        //Video video = new Video("100","橘子","我想买橘子","1.mp4","2.jpg",new Date(),"1","1","1","2","2");
        //videoRepository.save(video);
        List<Video> videos = videoDao.queryAll();
        videoRepository.saveAll(videos);
        //降序查询Elsearch
        //videoRepository.findAll(Sort.by(Sort.Order.desc("likeNum")));
        List<Video> title = videoRepository.findByTitle("橘子");
        title.forEach(video -> System.out.println(video));
    }

    @Test
    public void query() {
        String content = "橘子";
        //设置查询条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("video")
                .withTypes("doc")
                .withQuery(QueryBuilders.queryStringQuery(content).field("title"))//指定查询条件和字段
                .build();
        List<Video> videos = elasticsearchTemplate.queryForList(searchQuery, Video.class);
        videos.forEach(video -> System.out.println(video));
    }
}
