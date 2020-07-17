package com.baizhi.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.vo.AliyunVo;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class AliyunOssUtil {
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI4FyfQ5vDESMMNyzjNBrg";
    private static String accessKeySecret = "AuBZwzbi1UWXDc7dI9tu7BPa8w8pWi";


    /*
     * 创建bucket空间
     * 参数 bucketName 存储空间名字
     **/
    public static void createBucket(String bucketName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     * 展示所有存储空间
     * 参数 name 存储空间名字
     **/
    public static void showBucket() {
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

    /*
     * 删除存储空间存
     * 参数 bucketName 存储空间名字
     **/
    public static void deleteBucket(String bucketName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除存储空间。
        ossClient.deleteBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     * 上传文件
     * 参数 bucketName 存储空间名字
     *      objectName 文件名  可以指定文件目录
     *      localFile //本地文件路径
     **/
    public static void uploadFile(String bucketName, String objectName, String localFile) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
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

    /*
     * 上传文件流
     * 参数 bucketName 存储空间名字
     *      objectName 文件名  可以指定文件目录
     *      localFile //本地文件路径
     **/
    public static void uploadInputStream(String bucketName, String objectName, String localFile) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //上传文件
        ossClient.putObject(bucketName, objectName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     * 上传byte数组文件
     * 参数 bucketName 存储空间名字
     *      objectName 文件名  可以指定文件目录
     *      localFile //本地文件路径
     **/
    public static void uploadByteFile(String bucketName, String objectName, byte[] content) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     * 删除文件
     * 参数：
     *   bucketName：存储空间名
     *   fileName：文件名
     * */
    public static void deleteFile(String bucketName, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     * 视频截取帧
     * 参数：
     *   bucketName：存储空间名
     *   fileName：文件名
     * */
    public static URL videoInterceptCover(String bucketName, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。e
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        //System.out.println(signedUrl);
        // 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl;
    }

    /*
     * 上传网络流至阿里云
     * 参数：
     *   netPath:网络路径
     *   bucketName：存储空间名
     *   fileName：指定文件名
     * */
    public static void uploadNetFile(String netPath, String bucketName, String fileName) throws IOException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传网络流。
        InputStream inputStream = new URL(netPath).openStream();
        ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 网络地址下载到本地
     * 参数：
     * netPath:网络路径
     * bucketName：存储空间名
     * objectName：指定文件名
     */
    public static void downloadAliyun(String bucketName, String objectName, File file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), file);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void test() {
    }
}
