package com.soubu.goldensteward.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.greendao.Address;
import com.soubu.goldensteward.support.greendao.AddressDao;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.helper.UserManager;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.FileUtil;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.PermissionUtil;
import com.soubu.goldensteward.ui.home.HomeActivity;
import com.soubu.goldensteward.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by lakers on 16/10/25.
 */
//标注这个类需要使用到6.0动态权限请求

@RuntimePermissions
public class SplashActivity extends ActivityPresenter<SplashActivityDelegate> {
    private final int REQUEST_LOGIN = 1002;


    @Override
    protected Class<SplashActivityDelegate> getDelegateClass() {
        return SplashActivityDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.print("");

        Observable.just(1)
                .map(var -> initProvinceAndCity())
                .subscribeOn(BaseApplication.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        SplashActivityPermissionsDispatcher.loadWithCheck(SplashActivity.this);
    }

    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void load() {
        if (UserManager.initUser()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
        finish();
    }

    //之前拒绝过这个请求,当再次请求这个权限的时候调起的方法
    //建议是对话框的方式,告知用户请求这个权限的原因
    //注意由于是在build中生成的类文件,因此每次对注释方法有有修改需要clean,rebuild.
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void showDialog(PermissionRequest request) {
        PermissionUtil.showPermissionExplainDialog(getApplicationContext(), R.string.permission_explain_storage, request);
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void onPermissionDenied() {
        finish();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(this, R.string.permission_explain_storage, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PermissionUtil.REQUEST_PERMISSION_SETTING:
                case REQUEST_LOGIN:
                    load();
                default:
                    break;
            }
        } else {
            finish();
        }
    }


    private boolean initProvinceAndCity() {
        try {
            AddressDao addressDao = DBHelper.getInstance(getApplicationContext()).getAddressDao();
            if (addressDao.count() > 0) {
                return false;
            } else {
                String json = FileUtil.getFromAssets(this, "areas.json");
                JSONObject addressJson = new JSONObject(json);
                JSONArray jsonArray = addressJson.getJSONArray("RECORDS");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Address address = new Address();
                    address.setArea_id(object.getInt("area_id"));
                    address.setArea_name(object.getString("area_name"));
                    address.setParent_id(object.getInt("parent_id"));
                    address.setSort(object.getInt("sort"));
                    address.setTag(object.getString("tag"));
                    addressDao.insert(address);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
