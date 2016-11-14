package com.soubu.goldensteward.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyUploadCertificatesFragmentDelegate;
import com.soubu.goldensteward.module.OssConst;
import com.soubu.goldensteward.module.server.VerificationServerParams;
import com.soubu.goldensteward.utils.CameraUtil;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.utils.GlideUtils;
import com.soubu.goldensteward.utils.OssUtil;
import com.soubu.goldensteward.utils.PermissionUtil;
import com.soubu.goldensteward.utils.PhoneUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.widget.ProgressImageView;

import java.io.File;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lakers on 16/10/28.
 */
@RuntimePermissions
public class StoreOwnerVerifyUploadCertificatesFragment extends FragmentPresenter<StoreOwnerVerifyUploadCertificatesFragmentDelegate>
        implements View.OnClickListener {
    OnClickNextStepListener mOnClickNextStep;
    Context mContext;
    ProgressImageView mIvLastClick;
    int mLastImgRes;
    private int mFileType = 1;
    private String[] mPaths = new String[5];
    private int mClickIndex;

    @Override
    protected Class<StoreOwnerVerifyUploadCertificatesFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyUploadCertificatesFragmentDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_next_step, R.id.iv_business_license, R.id.iv_business_code, R.id.iv_camera_hand,
                R.id.iv_camera_front, R.id.iv_camera_back, R.id.ll_help1, R.id.ll_help2);
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setFileType(mFileType);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnClickNextStepListener) {
            mOnClickNextStep = (OnClickNextStepListener) context;
        }
    }

    @Override
    public void onClick(View v) {
        boolean clickImage = false;
        View mVHelp = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_store_owner_verify_help1, null);
        switch (v.getId()) {
            case R.id.btn_next_step:
                if (mOnClickNextStep != null) {
                    VerificationServerParams params = new VerificationServerParams();
                    if (checkGoNext(params)) {
                        mOnClickNextStep.onClickStep2(params);
                    }
                }
                break;
            case R.id.iv_business_license:
                mClickIndex = 0;
                clickImage = true;
                break;
            case R.id.iv_business_code:
                mClickIndex = 1;
                clickImage = true;
                break;
            case R.id.iv_camera_hand:
                mClickIndex = 2;
                clickImage = true;
                break;
            case R.id.iv_camera_front:
                mClickIndex = 3;
                clickImage = true;
                break;
            case R.id.iv_camera_back:
                mClickIndex = 4;
                clickImage = true;
                break;
            case R.id.ll_help1:
                if (mFileType == 1) {
                    ((ImageView) mVHelp.findViewById(R.id.iv_help)).setImageResource(R.drawable.auth_group_demo);
                } else {
                    ((ImageView) mVHelp.findViewById(R.id.iv_help)).setImageResource(R.drawable.auth_many_demo);
                }
                ShowWidgetUtil.showCustomDialog(mVHelp, true);
                break;
            case R.id.ll_help2:
                ((ImageView) mVHelp.findViewById(R.id.iv_help)).setImageResource(R.drawable.auth_id_demo);
                ShowWidgetUtil.showCustomDialog(mVHelp, true);
                break;
        }
        if (clickImage) {
            mIvLastClick = (ProgressImageView) v;
            mLastImgRes = R.drawable.auth_reload;
            ShowWidgetUtil.showMultiItemDialog(getActivity(), R.string.choose_photo, R.array.choose_photo, false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        CameraUtil.doChoosePhoto(StoreOwnerVerifyUploadCertificatesFragment.this);
                    } else {
                        StoreOwnerVerifyUploadCertificatesFragmentPermissionsDispatcher.takePhotoWithCheck(StoreOwnerVerifyUploadCertificatesFragment.this);
                    }
                }
            });
        }
    }

    private boolean checkGoNext(VerificationServerParams params) {
        for (int i = 0; i < mPaths.length; i++) {
            switch (i) {
                case 0:
                    if (TextUtils.isEmpty(mPaths[i])) {
                        ShowWidgetUtil.showShort(R.string.please_choose_business_license);
                        return false;
                    } else {
                        params.setBusiness_img(mPaths[i]);
                    }
                    break;
                case 1:
                    if (TextUtils.isEmpty(mPaths[i])) {
                        if (mFileType == 1) {
                            ShowWidgetUtil.showShort(R.string.please_choose_business_code);
                            return false;
                        }
                    } else {
                        params.setCode_img(mPaths[i]);
                    }
                    break;
                case 2:
                    if (TextUtils.isEmpty(mPaths[i])) {
                        ShowWidgetUtil.showShort(R.string.please_choose_id_hand);
                        return false;
                    } else {
                        params.setHold_img(mPaths[i]);
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(mPaths[i])) {
                        ShowWidgetUtil.showShort(R.string.please_choose_id_front);
                        return false;
                    } else {
                        params.setUp_img(mPaths[i]);
                    }
                    break;
                case 4:
                    if (TextUtils.isEmpty(mPaths[i])) {
                        ShowWidgetUtil.showShort(R.string.please_choose_id_back);
                        return false;
                    } else {
                        params.setDown_img(mPaths[i]);
                    }
                    break;
            }

        }
        return true;
    }

    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.CAMERA})
    void takePhoto() {
        CameraUtil.doTakePhoto(StoreOwnerVerifyUploadCertificatesFragment.this);
    }

    @OnShowRationale({Manifest.permission.CAMERA})
    void showDialog(PermissionRequest request) {
        PermissionUtil.showPermissionExplainDialog(getActivity(), R.string.permission_explain_camera, request);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA})
    void onPermissionDenied() {

    }

    @OnNeverAskAgain({Manifest.permission.CAMERA})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(getActivity(), R.string.permission_explain_camera, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        StoreOwnerVerifyUploadCertificatesFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void uploadData(final String path, final Uri uri) {
        GlideUtils.loadRoundedImage(getContext(), mIvLastClick, uri, mLastImgRes, mLastImgRes);
        OssUtil.uploadSingleImage(path, OssConst.DIY_CERTIFICATION, new OssUtil.UploadCallBack() {
            @Override
            public void onSuccess(String fileName) {
                String path = OssConst.DIY_CERTIFICATION + File.separator + fileName;
                mPaths[mClickIndex] = path;
                Log.e("xxxxxxxSuccess", fileName);
            }

            @Override
            public void onFailure(String fileName) {
                if(!PhoneUtil.isConnected(GoldenStewardApplication.getContext())){
                    ShowWidgetUtil.showShort(R.string.please_check_internet);
                }
                Log.e("xxxxxxxFailure", fileName);
                mIvLastClick.setImageResource(R.drawable.auth_reload);
                mIvLastClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mIvLastClick = (ProgressImageView) v;
                        uploadData(path, uri);
                    }
                });
            }

            @Override
            public void onProgress(int progress) {
                mIvLastClick.setProgress(progress);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CameraUtil.REQUEST_GALLERY:
                    if (data != null) {
                        String path = ConvertUtil.uriToPath(getActivity(), data.getData());
                        uploadData(path, data.getData());
                    }
                    break;
                case CameraUtil.REQUEST_CAMERA:
                    File takePhoto = CameraUtil.getTakePhoto();
                    Uri uri = Uri.fromFile(takePhoto);
                    uploadData(takePhoto.getPath(), uri);
            }
        }
    }

    public interface OnClickNextStepListener {
        void onClickStep2(VerificationServerParams params);

    }

    public void setFileType(String type) {
        mFileType = Integer.valueOf(type);
        if (viewDelegate != null) {
            viewDelegate.setFileType(mFileType);
        }
    }


}
