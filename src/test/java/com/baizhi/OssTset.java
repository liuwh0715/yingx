package com.baizhi;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class OssTset {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    String accessKeyId = "LTAI4FyfQ5vDESMMNyzjNBrg";
    String accessKeySecret = "AuBZwzbi1UWXDc7dI9tu7BPa8w8pWi";

    @Test
    public void method() {

        String bucketName = "192test";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void method1() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void deleteBucket() {
        String bucketName = "192t";  //存储空间名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除存储空间。
        ossClient.deleteBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传
    @Test
    public void upmethod() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String bucketName = "hang-yingxue";  //存储空间名
        String objectName = "photos/aaa.jpg";  //文件名  可以指定文件目录
        String localFile = "C:\\Users\\liuya\\Desktop\\test.jpg";  //本地视频路径
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFile));
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传文件流
    @Test
    public void uploadAliyunInputStream() throws Exception {
        String bucketName = "hang-yingxue";  //存储空间名
        String objectName = "aaa.jpg";  //文件名  可以指定文件目录
        String localFile = "C:\\Users\\liuya\\Desktop\\test.jpg";  //本地视频路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream(localFile);
        //上传文件
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件下载
    @Test
    public void testDownLoad() {

        String bucketName = "hang-yingxue";  //存储空间名
        String objectName = "aaa.jpg";  //文件名
        String localFile = "C:\\Users\\liuya\\Desktop\\" + objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除文件
    @Test
    public void deleteFile() {
        String bucketName = "hang-yingxue";  //存储空间名
        String objectName = "aaa.jpg";  //文件名
        System.out.println(System.getProperty("java.library.path"));
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
