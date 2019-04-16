package com.hrada.oms.util;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.UploadRepository;
import com.hrada.oms.model.common.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shin on 2018/11/16.
 */
@Component
public class UploadUtil {

    @Autowired
    UploadRepository uploadRepository;

    public JSONObject upload(MultipartFile file){
        JSONObject obj = new JSONObject();
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String oldName = fileName;
                // 文件保存目录路径
                String savePath = "D:/site/landa/oms/upload/";
                //File path = new File(ResourceUtils.getURL("classpath:").getPath());
                //String savePath = path.getAbsolutePath()+"/static/upload/";

                // 文件保存目录URL
                String saveUrl = "/upload/";

                String dirName = "file";
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if(fileExt.equals("jpg") || fileExt.equals("jpeg")|| fileExt.equals("png")|| fileExt.equals("gif")|| fileExt.equals("bmp")){
                    dirName = "img";
                }
                // 创建文件夹
                savePath += dirName + "/";
                saveUrl += dirName + "/";
                File saveDirFile = new File(savePath);
                if (!saveDirFile.exists()) {
                    saveDirFile.mkdirs();
                }
                //创建年文件夹
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                String ymd = sdf.format(new Date());
                savePath += ymd + "/";
                saveUrl += ymd + "/";
                File dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                //创建月文件夹
                sdf = new SimpleDateFormat("MM");
                ymd = sdf.format(new Date());
                savePath += ymd + "/";
                saveUrl += ymd + "/";
                dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
                String time = sdf.format(new Date());
                saveUrl += time + "." + fileExt;
                File saveFile = new File(savePath + time + "." + fileExt);
                file.transferTo(saveFile);


                Upload upload = new Upload();
                upload.setUrl(saveUrl);
                upload.setOldName(oldName);
                upload = uploadRepository.save(upload);

                obj.put("rs", true);
                obj.put("id", upload.getId());
                obj.put("url", saveUrl);
                obj.put("oldName", oldName);

            } catch (FileNotFoundException e) {
                obj.put("rs", false);
                e.printStackTrace();
            } catch (IOException e) {
                obj.put("rs", false);
                e.printStackTrace();
            }
        }else{
            obj.put("rs", false);
        }
        return obj;
    }

    /**
     * 截取图片
     * @param file  图片
     * @param x    截取时的x坐标
     * @param y    截取时的y坐标
     * @param w   截取的宽度
     * @param h   截取的高度
     */
    public JSONObject imgCut(MultipartFile file, Integer x, Integer y, Integer w, Integer h) {
        JSONObject obj = new JSONObject();
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String oldName = fileName;
                // 文件保存目录路径
                String savePath = "D:/site/landa/oms/upload/";
                //File path = new File(ResourceUtils.getURL("classpath:").getPath());
                //String savePath = path.getAbsolutePath()+"/static/upload/";

                // 文件保存目录URL
                String saveUrl = "/upload/";

                String dirName = "file";
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if(fileExt.equals("jpg") || fileExt.equals("jpeg")|| fileExt.equals("png")|| fileExt.equals("gif")|| fileExt.equals("bmp")){
                    dirName = "img";
                }
                // 创建文件夹
                savePath += dirName + "/";
                saveUrl += dirName + "/";
                File saveDirFile = new File(savePath);
                if (!saveDirFile.exists()) {
                    saveDirFile.mkdirs();
                }
                //创建年文件夹
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                String ymd = sdf.format(new Date());
                savePath += ymd + "/";
                saveUrl += ymd + "/";
                File dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                //创建月文件夹
                sdf = new SimpleDateFormat("MM");
                ymd = sdf.format(new Date());
                savePath += ymd + "/";
                saveUrl += ymd + "/";
                dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
                String time = sdf.format(new Date());
                saveUrl += time + "." + fileExt;
                File saveFile = new File(savePath + time + "." + fileExt);
                file.transferTo(saveFile);

                Image img;
                ImageFilter cropFilter;
                BufferedImage bi = ImageIO.read(saveFile);
                int srcWidth = bi.getWidth();
                int srcHeight = bi.getHeight();
                if (srcWidth >= w && srcHeight >= h) {
                    Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);
                    cropFilter = new CropImageFilter(x, y, w, h);
                    img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                    BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                    Graphics g = tag.getGraphics();
                    g.drawImage(img, 0, 0, null);
                    g.dispose();
                    //输出文件
                    ImageIO.write(tag, "JPEG", saveFile);
                }

                Upload upload = new Upload();
                upload.setUrl(saveUrl);
                upload.setOldName(oldName);
                upload = uploadRepository.save(upload);

                obj.put("rs", true);
                obj.put("id", upload.getId());
                obj.put("url", saveUrl);
                obj.put("oldName", oldName);

            } catch (FileNotFoundException e) {
                obj.put("rs", false);
                e.printStackTrace();
            } catch (IOException e) {
                obj.put("rs", false);
                e.printStackTrace();
            }
        }else{
            obj.put("rs", false);
        }
        return obj;
    }
}
