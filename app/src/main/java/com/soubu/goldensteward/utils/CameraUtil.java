package com.soubu.goldensteward.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by lakers on 16/11/3.
 */

public class CameraUtil {

    public static final int REQUEST_GALLERY = 1001;
    public static final int REQUEST_CAMERA = 1002;

    static File mCurrentFile;

    /**
     * 拍照的照片存储位置
     */
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/SoubuShopManager/");

    /**
     * 拍照获取图片
     */
    public static void doTakePhoto(Activity context) {

        try {
            if (!PHOTO_DIR.exists())
                PHOTO_DIR.mkdirs();// 创建照片的存储目录 BV
            mCurrentFile = new File(PHOTO_DIR, "ShopManager_" + System.currentTimeMillis() + ".jpg");// 给新照的照片文件命名
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentFile));
            context.startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            Toast.makeText(context, "所选择的照片不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public static void doTakePhoto(Fragment context) {

        try {
            if (!PHOTO_DIR.exists())
                PHOTO_DIR.mkdirs();// 创建照片的存储目录 BV
            mCurrentFile = new File(PHOTO_DIR, "ShopManager_" + System.currentTimeMillis() + ".jpg");// 给新照的照片文件命名
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentFile));
            context.startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            Toast.makeText(context.getActivity(), "所选择的照片不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public static File getTakePhoto(){
        File file = mCurrentFile;
        mCurrentFile = null;
        return file;
    }


    /**
     * 拍照获取图片
     */
    public static void doChoosePhoto(Activity context) {

        try {
            // 激活系统图库，选择一张图片
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
            context.startActivityForResult(intent, REQUEST_GALLERY);
        } catch (Exception e) {
            Toast.makeText(context, "所选择的照片不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public static void doChoosePhoto(Fragment context) {
        try {
            // 激活系统图库，选择一张图片
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
            context.startActivityForResult(intent, REQUEST_GALLERY);
        } catch (Exception e) {
            Toast.makeText(context.getActivity(), "所选择的照片不存在", Toast.LENGTH_SHORT).show();
        }
    }





}
