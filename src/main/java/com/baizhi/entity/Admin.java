package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (YxAdmin)实体类
 *
 * @author makejava
 * @since 2020-06-30 17:44:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    private static final long serialVersionUID = 387331373229393549L;

    private String id;

    private String username;

    private String password;

    private String status;


}