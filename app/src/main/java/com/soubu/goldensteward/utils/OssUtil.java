package com.soubu.goldensteward.utils;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.module.OssConst;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static com.soubu.goldensteward.GoldenStewardApplication.oss;

/**
 * Created by lakers on 16/11/3.
 */

public class OssUtil {


    public static void uploadImgPosPathByOss(final List<String> uploadList, String dir, final UploadCallBack callBack) {
        List<String> compressList = new ArrayList();
        for (int i = 0; i < uploadList.size(); i++) {
            compressList.add(BitmapUtils.getimage(uploadList.get(i)).getPath());
        }
//        String endpoint = OssConst.END_POINT;
//        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OssConst.ACCESSKEYID, OssConst.ACCESSKEYSECRET);
//        OSS oss = new OSSClient(App.getInstance(), endpoint, credentialProvider);
        final Stack stack = new Stack();
        for (int i = 0; i < compressList.size(); i++) {
            stack.push("" + i);
        }

        final List<String> imagelist = new ArrayList<>();
        final long start = System.currentTimeMillis();
        for (int i = 0; i < compressList.size(); i++) {
            String time = System.currentTimeMillis() + "";
            String random = "" + new Random().nextInt(100000);
            String fakeName = time + random + "." + getExtend(new File(compressList.get(i)).getName(), "jpg");
            PutObjectRequest put = new PutObjectRequest(OssConst.BUCKET, dir + File.separator + fakeName, compressList.get(i));
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest putObjectRequest, long l, long l1) {
                    if (callBack != null) {
                        int progress = (int) (l * 100 / l1);
                        callBack.onProgress(progress);
                    }
                }
            });
            oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                    Log.e("onSuccess", putObjectRequest.getObjectKey() + (System.currentTimeMillis() - start));
                    imagelist.add(putObjectRequest.getObjectKey());
                    stack.pop();

                    if (stack.size() == 0) {
                        Log.e("imagelistsize", imagelist.size() + "");
                        String uploadFileNames = new Gson().toJson(imagelist);
                        Log.e("uploadFileNames", "json:" + uploadFileNames);
                        if (imagelist.size() == 0) {
                            callBack.onFailure(uploadFileNames);
                        } else {
                            callBack.onSuccess(uploadFileNames);
                        }
                    }
                }

                @Override
                public void onFailure(PutObjectRequest putObjectRequest, ClientException
                        clientExcepion, ServiceException serviceException) {
                    Log.e("onFailure", putObjectRequest.getObjectKey() + (System.currentTimeMillis() - start));
                    if (clientExcepion != null) {
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {
                        Log.e("ErrorCode", serviceException.getErrorCode());
                        Log.e("RequestId", serviceException.getRequestId());
                        Log.e("HostId", serviceException.getHostId());
                        Log.e("RawMessage", serviceException.getRawMessage());
                    }
                    stack.pop();
                    if (stack.size() == 0) {
                        String uploadFileNames = new Gson().toJson(imagelist);
                        Log.e("imagelistsizeonFailure", imagelist.size() + "");
                        if (imagelist.size() == 0) {
                            callBack.onFailure(uploadFileNames);
                        } else {
                            callBack.onSuccess(uploadFileNames);
                        }
                    }
                }

            });
        }
    }

    private static String getExtend(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i > 0) && (i < (filename.length() - 1))) {
                return (filename.substring(i + 1)).toLowerCase();
            }
        }
        return defExt.toLowerCase();
    }


    public static void uploadSingleImage(final String uploadPath, final String dir, final UploadCallBack callBack) {
        String path = BitmapUtils.getimage(uploadPath).getPath();
        final String fakeOriginal = System.currentTimeMillis() + new Random().nextInt(100000) + "." + getExtend(new File(path).getName(), "jpg");
        String fakeName = fakeOriginal;
        PutObjectRequest put = new PutObjectRequest(OssConst.BUCKET, dir + File.separator + fakeName, path);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest putObjectRequest, long l, long l1) {
                if (callBack != null) {
                    int progress = (int) (l * 100 / l1);
                    callBack.onProgress(progress);
                }
            }
        });
        GoldenStewardApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        Log.e("onSuccess", putObjectRequest.getObjectKey());
                        String singlePath = putObjectRequest.getObjectKey();
                        if(callBack != null){
                            callBack.onSuccess(singlePath);
                        }
                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException
                            clientException, ServiceException serviceException) {
                        Log.e("onFailure", putObjectRequest.getObjectKey());
                        if (clientException != null) {
                            clientException.printStackTrace();
                        }
                        if (serviceException != null) {
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                        String singlePath = putObjectRequest.getObjectKey();
                        if(callBack != null){
                            callBack.onFailure(singlePath);
                        }
                    }

                }

        );

    }

    public interface UploadCallBack {
        void onSuccess(String fileName);

        void onFailure(String fileName);

        void onProgress(int progress);
    }
}
