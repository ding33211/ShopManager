package com.soubu.goldensteward.ui.information;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.constant.Constant;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.greendao.Address;
import com.soubu.goldensteward.support.greendao.AddressDao;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.LocationUtils;
import com.soubu.goldensteward.support.utils.PermissionUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.widget.optionspickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * Created by lakers on 16/11/4.
 */
@RuntimePermissions
public class ModifyInfoActivity extends ActivityPresenter<ModifyInfoActivityDelegate> implements View.OnClickListener {
    public static final int TYPE_EDIT = 0x01;
    public static final int TYPE_LOCATION = 0x02;

    private int mType;
    private String mProvince;
    private String mCity;
    private int mProvinceId;
    private int mCityId;
    private int mAddress;
    private OptionsPickerView mOpv;

    private LocationUtils mUtils;
    private AddressDao mAddressDao;
    private List<Address> mProvinces;
    private List<List<Address>> mCities;
    private UserServerParams mParams;


    @Override
    protected Class<ModifyInfoActivityDelegate> getDelegateClass() {
        return ModifyInfoActivityDelegate.class;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        String title = getIntent().getStringExtra(Constant.EXTRA_TITLE);
        viewDelegate.setTitle(title);

    }

    @Override
    protected void initView() {
        super.initView();

        Intent intent = getIntent();
        mType = intent.getIntExtra(Constant.EXTRA_TYPE, 0);

        switch (mType) {
            case TYPE_LOCATION:
                mParams = (UserServerParams) intent.getSerializableExtra(Constant.EXTRA_PARAMS);
                String pc = mParams.getProvince() + mParams.getCity();
                viewDelegate.refreshProvinceAndCity(pc);
                viewDelegate.refreshEditText(mParams.getAddress(), null, null);
                // 创建选项选择器对象
                mOpv = new OptionsPickerView<>(this);
                // 设置标题
                mOpv.setTitle(getResources().getString(R.string.please_choose));

                mAddressDao = DBHelper.getInstance(getApplicationContext()).getAddressDao();
                mProvinces = mAddressDao.queryBuilder().where(AddressDao.Properties.Parent_id.eq(0)).list();
                mCities = new ArrayList<>();
                ArrayList<String> provinces = new ArrayList<>();
                ArrayList<List<String>> cities = new ArrayList<>();
                for (Address province : mProvinces) {
                    provinces.add(province.getArea_name());
                    List<Address> list = mAddressDao.queryBuilder().where(AddressDao.Properties.Parent_id.eq(province.getArea_id())).list();
                    mCities.add(list);
                    List<String> cityList = new ArrayList<>();
                    for (Address city : list) {
                        cityList.add(city.getArea_name());
                    }
                    cities.add(cityList);
                }
                // 设置二级联动效果
                mOpv.setPicker(provinces, cities, true);
                // 监听确定选择按钮
                mOpv.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3) {
                        mParams.setProvince(mProvinces.get(options1).getArea_name());
                        mParams.setCity(mCities.get(options1).get(options2).getArea_name());
                        mParams.setProvince_id(mProvinces.get(options1).getArea_id() + "");
                        mParams.setCity_id(mCities.get(options1).get(options2).getArea_id() + "");
                        viewDelegate.refreshProvinceAndCity(mProvinces.get(options1).getArea_name() + mCities.get(options1).get(options2).getArea_name());
                    }
                });
                // 设置是否循环滚动
                mOpv.setCyclic(false, false, false);
                // 设置默认选中的三级项目
                mOpv.setSelectOptions(0, 0, 0);
                break;
            case TYPE_EDIT:
                String content = intent.getStringExtra(Constant.EXTRA_CONTENT);
                String label = intent.getStringExtra(Constant.EXTRA_LABEL);
                String hint = intent.getStringExtra(Constant.EXTRA_HINT);
                viewDelegate.refreshEditText(content, label, hint);
                break;
        }

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_location_change, R.id.iv_locate, R.id.btn_confirm);
    }

    @Override
    protected void initData() {
        super.initData();
        mUtils = LocationUtils.getInstance(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION})
    void getLocation() {
        mUtils.getLocation(listener);
        mUtils.start();
    }


    //之前拒绝过这个请求,当再次请求这个权限的时候调起的方法
    //建议是对话框的方式,告知用户请求这个权限的原因
    //注意由于是在build中生成的类文件,因此每次对注释方法有有修改需要clean,rebuild.
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION})
    void showDialog(PermissionRequest request) {
        PermissionUtil.showPermissionExplainDialog(getApplicationContext(), R.string.permission_explain_location, request);
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION})
    void onPermissionDenied() {
        ShowWidgetUtil.showShort(R.string.location_not_match_toast);
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION})
    void showGoToSettingDialog() {
        PermissionUtil.showGoToSettingDialog(this, R.string.permission_explain_location, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        ModifyInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PermissionUtil.REQUEST_PERMISSION_SETTING:
                    getLocation();
                    break;
                default:
                    break;
            }
        }
//        }
    }


    private BDLocationListener listener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (bdLocation.getProvince() != null && bdLocation.getCity() != null && !bdLocation.getProvince().equals("null")
                            && !bdLocation.getCity().equals("null")) {
                        // 获取地址信息
                        String area = null;
                        // 获取省，市Id
                        List<Address> provinceList = mAddressDao.queryBuilder().where(AddressDao.Properties.Area_name.eq(bdLocation.getProvince().replace("省", "").replace("市", "")), AddressDao.Properties.Parent_id.eq(0)).list();
                        List<Address> cityList = mAddressDao.queryBuilder().where(AddressDao.Properties.Area_name.eq(bdLocation.getCity().replace("市", "")), AddressDao.Properties.Parent_id.notEq(0)).list();
                        if (provinceList.size() == 0 || cityList.size() == 0) {
                            ShowWidgetUtil.showShort(R.string.location_not_match_toast);
                        } else {
                            mParams.setProvince_id(provinceList.get(0).getArea_id() + "");
                            mParams.setCity_id(cityList.get(0).getArea_id() + "");
                            mParams.setProvince(provinceList.get(0).getArea_name());
                            mParams.setCity(cityList.get(0).getArea_name());
                            area = (bdLocation.getProvince() + bdLocation.getCity()).replace("省", "").replace("市", "");
                        }
                        String addr = bdLocation.getAddrStr();
                        //截取市之后的信息
                        String address = addr.substring(addr.indexOf(bdLocation.getCity()) + bdLocation.getCity().length(), addr.length());
                        viewDelegate.refreshProvinceAndCity(area);
                        viewDelegate.refreshEditText(address, null, null);
                    } else {
                        ShowWidgetUtil.showShort(R.string.location_not_match_toast);
                    }
                    // 关闭获取地理位置
                    mUtils.stop();
                    mUtils.unregisterListener(listener);
                }
            });
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_location_change:
                mOpv.show();
                break;
            case R.id.iv_locate:
                ModifyInfoActivityPermissionsDispatcher.getLocationWithCheck(this);
                break;
            case R.id.btn_confirm:
                String content = viewDelegate.checkComplete();
                if (!TextUtils.isEmpty(content)) {
                    Intent intent = new Intent();
                    if (mType == TYPE_LOCATION) {
                        mParams.setAddress(content);
                        intent.putExtra(Constant.EXTRA_PARAMS, mParams);
                    } else {
                        intent.putExtra(Constant.EXTRA_CONTENT, content);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
        }
    }
}
