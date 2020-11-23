package com.fosun.storekeeper.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片处理工具类
 * @version 
 * @author hengjb 2019年8月16日下午2:49:21
 * @since 1.8
 */
public class ImageUtil {

    /**
     * 等比例压缩算法： 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     * @param srcUrl 原图地址
     * @param deskUrl 缩略图地址
     * @param comBase 压缩基数
     * @param scale 压缩限制(宽/高)比例 一般用1：当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
     * @throws IOException
     * @Description: 
     * @author hengjb
     * @create date 2019年8月16日下午2:54:41
     */
    public static void saveMinPhoto(String srcUrl, String deskUrl, double comBase, double scale) throws IOException {
        File srcFile = new java.io.File(srcUrl);
        Image src = ImageIO.read(srcFile);
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);
        // 缩略图高
        int deskHeight = 0;
        // 缩略图宽
        int deskWidth = 0;
        double srcScale = (double) srcHeight / srcWidth;
        /** 缩略图宽高算法 */
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                }
                else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
            else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                }
                else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        }
        else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        // 绘制缩小后的图
        tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);
        // 输出到文件流
        FileOutputStream deskImage = new FileOutputStream(deskUrl);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
        // 近JPEG编码
        encoder.encode(tag);
        deskImage.close();
    }

    /**
     * 返回文件压缩后的byte[]
     * @param img BufferedImage
     * @param originalFilename 原文件名称
     * @param comBase 压缩基数
     * @param scale 压缩限制(宽/高)比例 一般用1：当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
     * @return
     * @throws IOException
     * @Description: 
     * @create date 2018年6月19日下午7:15:28
     */
    public static byte[] saveMinPhoto(Image img, String originalFilename, double comBase, double scale)
            throws IOException {
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        int srcHeight = img.getHeight(null);
        int srcWidth = img.getWidth(null);
        // 缩略图高
        int deskHeight = 0;
        // 缩略图宽
        int deskWidth = 0;
        double srcScale = (double) srcHeight / srcWidth;
        /** 缩略图宽高算法 */
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                }
                else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
            else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                }
                else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        }
        else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        // 绘制压缩后的图
        tag.getGraphics().drawImage(img, 0, 0, deskWidth, deskHeight, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(tag, extName, stream);
        byte[] contents = stream.toByteArray();
        stream.flush();
        stream.close();
        return contents;
    }

    /**
     * 直接指定压缩后的宽高： (先保存原文件，再压缩、上传)
     * @param oldFile 要进行压缩的文件全路径
     * @param width 压缩后的宽度
     * @param height 压缩后的高度
     * @param quality 压缩质量
     * @param smallIcon 文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon).jpg
     * @return 返回压缩后的文件的全路径
     * @throws IOException
     * @Description: 
     * @author hengjb
     * @create date 2019年8月16日下午2:53:22
     */
    public static String zipImageFile(String oldFile, int width, int height, float quality, String smallIcon)
            throws IOException {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        /** 对服务器上的临时文件进行处理 */
        Image srcFile = ImageIO.read(new File(oldFile));
        /** 宽,高设定 */
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
        String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
        /** 压缩后的文件名 */
        newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
        /** 压缩之后临时存放位置 */
        FileOutputStream out = new FileOutputStream(newImage);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
        /** 压缩质量 */
        jep.setQuality(quality, true);
        encoder.encode(tag, jep);
        out.close();
        return newImage;
    }

    /**
     * 保存文件到服务器临时路径(用于文件上传)
     * @param fileName
     * @param is
     * @return
     * @throws IOException
     * @Description: 
     * @author hengjb
     * @create date 2019年8月16日下午2:52:58
     */
    public static String writeFile(String fileName, InputStream is) throws IOException {
        if (fileName == null || fileName.trim().length() == 0) {
            return null;
        }
        /** 首先保存到临时文件 */
        FileOutputStream fos = new FileOutputStream(fileName);
        // 缓冲大小
        byte[] readBytes = new byte[512];
        int readed = 0;
        while ((readed = is.read(readBytes)) > 0) {
            fos.write(readBytes, 0, readed);
        }
        fos.close();
        is.close();
        return fileName;
    }

    /**
    public static void main(String args[]) throws Exception {
        //ImageUtil.zipImageFile("f:/13.jpg", 1280, 1280, 1f, "x2");
        File srcFile = new java.io.File("f:/micro/1.jpg");
        Image src = ImageIO.read(srcFile);
        //ImageUtil.saveMinPhoto("f:/micro/1.jpg", "f:/micro/1-min2.jpg", 2000, 0.9d);
        ImageUtil.saveMinPhoto(src, "1.jpg", 2000, 0.9d);
    }
    */
}