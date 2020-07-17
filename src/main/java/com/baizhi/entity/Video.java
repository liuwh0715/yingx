package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (YxVideo)实体类
 *
 * @author makejava
 * @since 2020-07-05 10:38:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "video", type = "doc")
public class Video implements Serializable {
    private static final long serialVersionUID = 285062730894039909L;
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word") //分词
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String brief;
    @Field(type = FieldType.Keyword)
    private String coverPath;
    @Field(type = FieldType.Keyword) //不分词
    private String videoPath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Field(type = FieldType.Date)
    private Date createDate;
    @Field(type = FieldType.Keyword)
    private String categoryId;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Keyword)
    private String group;
    @Field(type = FieldType.Keyword)
    private String likeNum;
    @Field(type = FieldType.Keyword)
    private String playNum;
    private User user;
    private Category category;
}