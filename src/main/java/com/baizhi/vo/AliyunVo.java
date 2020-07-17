package com.baizhi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Component :表示将当前类的创建工作交给Spring容器管理
 * @ConfigurationProperties :自定义实体类 后缀注入
 * @since 2020-07-03 21:10:15
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyunvo")
@NoArgsConstructor
@AllArgsConstructor
public class AliyunVo {
    /**
     * endpoint:阿里云地址
     * accessKeyId：用户唯一id
     * accessKeySecret ：用户密钥
     * bucketName ：buck存储空间名字
     * headImg  ：上传文件
     * id   ：用户id
     * localPath  ：本地
     */
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private MultipartFile headImg;

    private String id;
    private String localPath;
    private String netAddress;
}
