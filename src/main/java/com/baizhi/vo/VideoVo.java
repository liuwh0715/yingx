package com.baizhi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 表现对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;
    private String description;
    private Integer likeCount;
    private String cateName;
    private String userPhoto;
    private String categoryId;
    private String userId;
    private String userName;

    public VideoVo(String id, String videoTitle, String cover, String path, Date uploadTime, String description, Integer likeCount, String cateName, String categoryId, String userId, String userName) {
        this.id = id;
        this.videoTitle = videoTitle;
        this.cover = cover;
        this.path = path;
        this.uploadTime = uploadTime;
        this.description = description;
        this.likeCount = likeCount;
        this.cateName = cateName;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
    }

    public VideoVo(String id, String videoTitle, String cover, String path, Date uploadTime, String description, Integer likeCount, String cateName, String userPhoto) {
        this.id = id;
        this.videoTitle = videoTitle;
        this.cover = cover;
        this.path = path;
        this.uploadTime = uploadTime;
        this.description = description;
        this.likeCount = likeCount;
        this.cateName = cateName;
        this.userPhoto = userPhoto;
    }
}
