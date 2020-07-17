package com.baizhi.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 持久层对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPo {
    private String vId;
    private String vTitle;
    private String vCover;
    private String vPath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date vUploadTime;
    private String vDescription;
    private String cateName;
    private String headImg;
    private String categoryId;
    private String userId;
    private String userName;
}
