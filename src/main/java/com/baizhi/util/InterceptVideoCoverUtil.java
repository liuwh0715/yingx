package com.baizhi.util;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;


/*
*
<dependency>
    <groupId>org.bytedeco</groupId>
    <artifactId>javacv</artifactId>
    <version>0.8</version>
</dependency>
* */

public class InterceptVideoCoverUtil {

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videofile 源视频文件路径   阿里云
     * @param localfile 截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchFrame(String videofile, String localfile) throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(localfile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);

    }

    public static void main(String[] args) {
        try {
            String videoFilePath = "https://yingx-192.oss-cn-beijing.aliyuncs.com/video/1594000325773-树叶.mp4";
            String localFilePath = "C:\\Users\\NANAN\\Pictures\\Saved Pictures\\1594000325773-树叶.jpg";  //"D:\\biudata\\vedio\\test5.jpg"
            fetchFrame(videoFilePath, localFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
