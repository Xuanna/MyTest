package com.example.xuchichi.mytest.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    //文件路径
    public static final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MKZYPlay";
    //图片路径
    public static final String imagePath = rootPath + "/Images";
    //视频路径
    public static final String recordPath = rootPath + "/Record";

    public static File imgFile;
    public static File vedioFile;

    public static void createFile() {
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            imgFile = new File(imagePath);
            if (!imgFile.exists()) {
                imgFile.mkdirs();
            }

            vedioFile = new File(recordPath);
            if (!vedioFile.exists()) {
                vedioFile.mkdirs();
            }
        }
    }


    //保存照片
    public static void saveBitmap(Bitmap b) {

        String jpegName = imgFile.getAbsolutePath() + "/" + getTime() + ".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取视频存储路径
    public static String getMediaOutputPath() {
        return vedioFile.getAbsolutePath() + "/" + getTime() + ".mp4";
    }

    private static String getTime() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis()));
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
