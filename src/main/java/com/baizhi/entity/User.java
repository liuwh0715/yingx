package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (YxUser)实体类
 *
 * @author makejava
 * @since 2020-07-01 22:21:30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 240395299061831238L;

    private String id;

    private String phone;

    private String username;

    private String headImg;

    private String brief;

    private String wechat;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private String sex;
    private String city;
    private Integer fansCount;
    private Integer videoCount;
    private String score;
    private Integer status;

}