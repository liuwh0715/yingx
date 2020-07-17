package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (YxCategory)实体类
 *
 * @author makejava
 * @since 2020-07-02 18:25:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    private static final long serialVersionUID = -62434801468916734L;

    private String id;

    private String cateName;

    private Integer levels;

    private String parentId;

}