package com.soubu.goldensteward.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.greendao.Address;
import com.soubu.goldensteward.base.greendao.AddressDao;
import com.soubu.goldensteward.base.greendao.DBHelper;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.home.HomeActivity;
import com.soubu.goldensteward.login.LoginActivity;
import com.soubu.goldensteward.utils.PermissionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                initProvinceAndCity();
            }
        }).start();
        SplashActivityPermissionsDispatcher.loadWithCheck(SplashActivity.this);
    }

    //需要验证权限的方法
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void load() {
        if (GoldenStewardApplication.getContext().initUser()) {
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


    private void initProvinceAndCity() {
        AddressDao addressDao = DBHelper.getInstance(getApplicationContext()).getAddressDao();
        if (addressDao.count() > 0) {
            return;
        } else {
            JSONObject addressJson = initJsonData();
            try {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private JSONObject initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("areas.json");
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "UTF-8"));
            }
            is.close();
            return new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
