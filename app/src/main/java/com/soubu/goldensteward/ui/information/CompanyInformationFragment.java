package com.soubu.goldensteward.ui.information;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.constant.Constant;
import com.soubu.goldensteward.support.bean.InformationRvItem;
import com.soubu.goldensteward.support.bean.OssConst;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.greendao.User;
import com.soubu.goldensteward.support.greendao.UserDao;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.utils.CameraUtil;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.OssUtil;
import com.soubu.goldensteward.support.utils.PermissionUtil;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.widget.ProgressImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dingsigang on 16-10-19.
 */
@RuntimePermissions
public class CompanyInformationFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {

    private List<InformationRvItem> mList;
    private ProgressImageView mIvLastClick;
    private UserDao mUserDao;
    private User mUser;
    private UserServerParams mParams;
    private UserServerParams mLocationParams;
    private boolean mHaveChanged = false;
    private boolean mHaveAddressChanged = false;

    private final int REQUEST_LOCATION = 0x00;
    private final int REQUEST_COMPANY_PROFILE = 0x01;

    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CameraUtil.REQUEST_GALLERY:
                    if (data != null) {
                        String path = ConvertUtil.uriToPath(getActivity(), data.getData());
                        OssUtil.uploadSingleImage(path, OssConst.DIY_USER, new OssUtil.UploadCallBack() {
                            @Override
                            public void onSuccess(String fileName) {
                                saveInfo(fileName, 0);
                            }

                            @Override
                            public void onFailure(String fileName) {
                                mIvLastClick.setImageResource(R.drawable.auth_reload);
                            }

                            @Override
                            public void onProgress(int progress) {
                                mIvLastClick.setProgress(progress);
                            }
                        });
                        GlideUtils.loadRoundedImage(getContext(), mIvLastClick, data.getData(), R.drawable.auth_reload, R.drawable.auth_reload);
                    }
                    break;
                case CameraUtil.REQUEST_CAMERA:
                        File takePhoto = CameraUtil.getTakePhoto();
                        Uri uri = Uri.fromFile(takePhoto);
                        GlideUtils.loadRoundedImage(getContext(), mIvLastClick, uri, R.drawable.auth_reload, R.drawable.auth_reload);
                        OssUtil.uploadSingleImage(takePhoto.getPath(), OssConst.DIY_USER, new OssUtil.UploadCallBack() {
                            @Override
                            public void onSuccess(String fileName) {
                                saveInfo(fileName, 0);
                            }

                            @Override
                            public void onFailure(String fileName) {
                                mIvLastClick.setImageResource(R.drawable.auth_reload);
                            }

                            @Override
                            public void onProgress(int progress) {
                                mIvLastClick.setProgress(progress);
                            }
                        });
                    break;
                case REQUEST_LOCATION:
                    if (data != null) {
                        UserServerParams params = (UserServerParams) data.getSerializableExtra(Constant.EXTRA_PARAMS);
                        mLocationParams.deltaCopy(params);
                        mUser.setAddress(params.getAddress());
                        mUser.setProvince(mLocationParams.getProvince());
                        mUser.setProvince_id(mLocationParams.getProvince_id());
                        mUser.setCity(mLocationParams.getCity());
                        mUser.setCity_id(mLocationParams.getCity_id());
                        mList.get(4).setContent(params.getAddress());
                        viewDelegate.notifyItemChanged(4);
                        if(!mHaveAddressChanged){
                            mHaveAddressChanged = true;
                        }
                    }
                    break;
                case REQUEST_COMPANY_PROFILE:
                    if (data != null) {
                        String companyProfile = data.getStringExtra(Constant.EXTRA_CONTENT);
                        saveInfo(companyProfile, 8);
                    }
                    break;
            }
        }
    }


    private void saveInfo(String content, int position) {
        if(!mHaveChanged){
            mHaveChanged = true;
        }
        switch (position) {
            case 0:
                mUser.setPortrait(OssConst.END_POINT + File.separator + content);
                String path = content;
                mParams.setPortrait(path);
                break;
            case 5:
                mUser.setTurnover(content);
                mParams.setTurnover(content);
                mList.get(position).setContent(content);
                viewDelegate.notifyItemChanged(position);
                break;
            case 6:
                mUser.setCompany_size(content);
                mParams.setCompany_size(content);
                mList.get(position).setContent(content);
                viewDelegate.notifyItemChanged(position);
                break;
            case 8:
                mUser.setCompany_profile(content);
                mParams.setCompany_profile(content);
                mList.get(position).setContent(content);
                viewDelegate.notifyItemChanged(position);
                break;

        }

    }


    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.CAMERA})
    void takePhoto() {
        CameraUtil.doTakePhoto(CompanyInformationFragment.this);
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
        PermissionUtil.showGoToSettingDialog(getActivity(), R.string.permission_explain_camera, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        CompanyInformationFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    protected void initData() {
        super.initData();
        mParams = new UserServerParams();
        mLocationParams = new UserServerParams();
        InformationSingleRvAdapter adapter = new InformationSingleRvAdapter(getActivity()){
            @Override
            public void onItemClick(BaseViewHolder holder, InformationRvItem item, int position) {
                boolean needChoose = false;
                switch (position) {
                    case 0:
                        mIvLastClick = (ProgressImageView) viewDelegate.getClickView(position).findViewById(R.id.iv_avatar);
                        ShowWidgetUtil.showMultiItemDialog(getActivity(), R.string.choose_photo, R.array.choose_photo, false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    CameraUtil.doChoosePhoto(CompanyInformationFragment.this);
                                } else {
                                    CompanyInformationFragmentPermissionsDispatcher.takePhotoWithCheck(CompanyInformationFragment.this);
                                }
                            }
                        });
                        break;
                    case 5:
                    case 6:
                        needChoose = true;
                        break;
                    case 4:
                        UserServerParams params = new UserServerParams();
                        params.setCity_id(mUser.getCity_id());
                        params.setCity(mUser.getCity());
                        params.setProvince_id(mUser.getProvince_id());
                        params.setProvince(mUser.getProvince());
                        params.setAddress(mUser.getAddress());
                        Intent intent = new Intent(getActivity(), ModifyInfoActivity.class);
                        intent.putExtra(Constant.EXTRA_TYPE, ModifyInfoActivity.TYPE_LOCATION);
                        intent.putExtra(Constant.EXTRA_PARAMS, params);
                        intent.putExtra(Constant.EXTRA_TITLE, getString(R.string.company_address));
                        startActivityForResult(intent, REQUEST_LOCATION);
                        break;
                    case 8:
                        Intent intent1 = new Intent(getActivity(), ModifyInfoActivity.class);
                        intent1.putExtra(Constant.EXTRA_TYPE, ModifyInfoActivity.TYPE_EDIT);
                        intent1.putExtra(Constant.EXTRA_TITLE, getString(R.string.company_profile));
                        intent1.putExtra(Constant.EXTRA_CONTENT, mUser.getCompany_profile());
                        intent1.putExtra(Constant.EXTRA_HINT, getString(R.string.company_profile_desc));
                        intent1.putExtra(Constant.EXTRA_LABEL, getString(R.string.company_profile));
                        startActivityForResult(intent1, REQUEST_COMPANY_PROFILE);
                        break;
                }
                if (needChoose) {
                    ShowWidgetUtil.showMultiItemDialog(getActivity(), item.getTitleRes(), item.getArrayRes(), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveInfo(which + 1 + "", position);
                            dialog.dismiss();
                        }
                    });
                }
            }
        };
        viewDelegate.setAdapter(adapter);
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        mUserDao = DBHelper.getInstance(getActivity()).getUserDao();
        List<User> list = mUserDao.queryBuilder().where(UserDao.Properties.Phone.eq(phone)).list();
        if (list.size() > 0) {
            initRecyclerData(list.get(0));
        }
    }


    private void initRecyclerData(User user) {
        mUser = user;
        mList = new ArrayList<>();
        InformationRvItem item = new InformationRvItem();
        item.setTitleRes(R.string.sign_up_now);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_AVATAR);
        item.setContent(user.getPortrait());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.company_name);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent(user.getCompany());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.main_industry);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setArrayRes(R.array.main_industry);
        item.setContent(user.getMain_industry());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.management_model);
        item.setContent(user.getOperation_mode());
        item.setArrayRes(R.array.operation_mode);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.company_address);
        item.setContent(user.getAddress());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.annual_turnover);
        item.setContent(user.getTurnover());
        item.setArrayRes(R.array.turnover);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.employees_num);
        item.setContent(user.getCompany_size());
        item.setArrayRes(R.array.company_size);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.main_products);
        item.setContent(user.getMain_product());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CONTENT_MULTILINE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.company_profile);
        item.setContent(user.getCompany_profile());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_COMPANY_PROFILE);
        mList.add(item);
        viewDelegate.setData(mList);
    }

//    public void modifyToServer(){
//        RetrofitRequest.getInstance().changeUserInfo(mParams);
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onModifySuccess(BaseResp resp){
//        if(resp.getResult() instanceof UserServerParams){
//            ShowWidgetUtil.showShort(resp.msg);
//        }
//    }

    public UserServerParams getParams(){
        return mParams;
    }


    public UserServerParams getLocationParams(){
        return mLocationParams;
    }

    public void updateToDb(){
        mUserDao.update(mUser);
    }

    public boolean isAddressChanged(){
        return mHaveAddressChanged;
    }


    public boolean isChanged(){
        return mHaveChanged;
    }
}
