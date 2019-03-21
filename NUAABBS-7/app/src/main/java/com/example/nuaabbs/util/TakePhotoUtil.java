package com.example.nuaabbs.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.example.nuaabbs.action.BaseActivity;
import com.example.nuaabbs.common.Constant;

import java.io.File;
import java.io.IOException;

public class TakePhotoUtil {

    public static Uri imageUri;

    public static void executeTakePhoto(BaseActivity activity){
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(activity.getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(activity,
                    "com.example.nuaabbs.fileProvider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, Constant.TAKE_PHOTO);
    }
}
