package com.soubu.goldensteward.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.google.gson.Gson;
import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.RegisterSupplierRvAdapter;
import com.soubu.goldensteward.base.greendao.Address;
import com.soubu.goldensteward.base.greendao.AddressDao;
import com.soubu.goldensteward.base.greendao.DBHelper;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.delegate.RegisterSupplierActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.MainProductParams;
import com.soubu.goldensteward.module.RegisterRvItem;
import com.soubu.goldensteward.module.server.BaseResp;
import com.soubu.goldensteward.module.server.LoginServerParams;
import com.soubu.goldensteward.module.server.RegisterServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.LocationUtils;
import com.soubu.goldensteward.utils.PermissionUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.widget.optionspickerview.OptionsPickerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by lakers on 16/10/27.
 */
@RuntimePermissions
public class RegisterSupplierActivity extends ActivityPresenter<RegisterSupplierActivityDelegate> implements View.OnClickListener {


    private static final int REQUEST_CHOOSE_MAIN_PRODUCT = 1010;

    private boolean mGoNextStep = false;
    private LocationUtils mUtils;
    private AddressDao mAddressDao;
    private boolean mHaveLocated;


    private List<Address> mProvinces;
    private List<List<Address>> mCities;
    private OptionsPickerView mOpv;
    private RegisterServerParams mParams;
    private MainProductParams[] mMainProductParams;


    @Override
    protected Class<RegisterSupplierActivityDelegate> getDelegateClass() {
        return RegisterSupplierActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.supplier_register);
        mParams = (RegisterServerParams) getIntent().getSerializableExtra(Constant.EXTRA_PARAMS);
    }


    @Override
    protected void initView() {
        super.initView();
        // 创建选项选择器对象
        mOpv = new OptionsPickerView<>(this);
        // 设置标题
        mOpv.setTitle(getResources().getString(R.string.please_choose));
    }

    @Override
    protected void initData() {
        super.initData();
        initRegisterFirst();
        mUtils = LocationUtils.getInstance(this);
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
                viewDelegate.refreshArea(mProvinces.get(options1).getArea_name() + mCities.get(options1).get(options2).getArea_name());
            }
        });
        // 设置是否循环滚动
        mOpv.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        mOpv.setSelectOptions(0, 0, 0);

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION})
    void getLocation() {
        mUtils.getLocation(listener);
        mUtils.start();
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
                        List<Address> provinceList = mAddressDao.queryBuilder().where(AddressDao.Properties.Area_name.eq(bdLocation.getProvince().replace("省", "")), AddressDao.Properties.Parent_id.eq(0)).list();
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
                        String address = bdLocation.getAddrStr().replace("省", "").replace("市", "");
                        viewDelegate.refreshArea(area);
                        viewDelegate.refreshAddress(address);
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
        RegisterSupplierActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PermissionUtil.REQUEST_PERMISSION_SETTING:
                    getLocation();
                    break;
                case REQUEST_CHOOSE_MAIN_PRODUCT:
                    Parcelable[] parcelables = data.getParcelableArrayExtra(Constant.EXTRA_PARAMS);
                    if (parcelables != null) {
                        mMainProductParams = new MainProductParams[parcelables.length];
                        for (int i = 0; i < parcelables.length; i++) {
                            mMainProductParams[i] = (MainProductParams) parcelables[i];
                        }
                    }
                    String json = new Gson().toJson(mMainProductParams);
                    mParams.setMain_product(json);
                    String message = data.getStringExtra(Constant.EXTRA_RESULT);
                    if (!TextUtils.isEmpty(message)) {
                        viewDelegate.refreshMainProducts(message);
                    }
                    break;
                default:
                    break;
            }
        }
//        else {
//            PermissionUtil.showGoToSettingDialog(this, R.string.permission_explain_location, true);
//        }
    }

    void initRegisterFirst() {
        List<RegisterRvItem> list = new ArrayList<>();
        list.add(createItem(R.string.store_name, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0));
        list.add(createItem(R.string.contact_name, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0));
        list.add(createItem(R.string.position_in, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, R.array.job));
        list.add(createItem(R.string.email, null, RegisterSupplierRvAdapter.TYPE_ITEM_CAN_FILL, InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS, 0));
        list.add(createItem(R.string.phone, null, RegisterSupplierRvAdapter.TYPE_ITEM_CAN_FILL, InputType.TYPE_CLASS_PHONE, 0));
        viewDelegate.setStep1Data(list);
    }

    void initRegisterSecond() {
        if (!viewDelegate.ifStep2Init()) {
            List<RegisterRvItem> list = new ArrayList<>();
            list.add(createItem(R.string.company_name, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0));
            list.add(createItem(R.string.main_industry, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, R.array.main_industry));
            list.add(createItem(R.string.main_products, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, 0));
            list.add(createItem(R.string.management_model, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, R.array.operation_mode));
            list.add(createItem(R.string.location_at, null, RegisterSupplierRvAdapter.TYPE_ITEM_LOCATE, 0, 0));
            list.add(createItem(R.string.detail_address, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_FILL, 0, 0));
            list.add(createItem(R.string.annual_turnover, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, R.array.turnover));
            list.add(createItem(R.string.employees_num, null, RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE, 0, R.array.company_size));
            list.add(createItem(R.string.company_profile, getString(R.string.company_profile_desc), RegisterSupplierRvAdapter.TYPE_ITEM_MULTILINE, 0, 0));
            viewDelegate.setStep2Data(list);
        }

    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_next_step);
        viewDelegate.setOnChooseAreaListener(new RegisterSupplierRvAdapter.OnChooseAreaOrProductClickListener() {
            @Override
            public void onClick(boolean chooseArea) {
                if (chooseArea) {
                    mOpv.show();
                } else {
                    Intent intent = new Intent(RegisterSupplierActivity.this, ChooseMainProductsActivity.class);
                    intent.putExtra(Constant.EXTRA_PARAMS, mMainProductParams);
                    startActivityForResult(intent, REQUEST_CHOOSE_MAIN_PRODUCT);
                }
            }
        });
    }

    private RegisterRvItem createItem(int titleRes, String content, int type, int editType, int arrayRes) {
        RegisterRvItem item = new RegisterRvItem();
        item.setTitleRes(titleRes);
        item.setType(type);
        item.setContent(content);
        if (editType != 0) {
            item.setEditType(editType);
        }
        if (arrayRes != 0) {
            item.setArrayRes(arrayRes);
        }
        return item;
    }


    @Override
    public void onClick(View v) {
        //需要通过再次清除焦点达到数据回调
        View view = getCurrentFocus();
        if (view instanceof EditText) {
            view.clearFocus();
        }
        switch (v.getId()) {
            case R.id.btn_next_step:
                if (!mGoNextStep) {
                    if (viewDelegate.checkComplete(mParams, 1)) {
                        if (!mHaveLocated) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    RegisterSupplierActivityPermissionsDispatcher.getLocationWithCheck(RegisterSupplierActivity.this);
                                }
                            }).start();
                            mHaveLocated = true;
                        }
                        initRegisterSecond();
                        viewDelegate.clickNextStep();
                        mGoNextStep = true;
                    }
                } else {
                    if (viewDelegate.checkComplete(mParams, 2)) {
                        RetrofitRequest.getInstance().register(mParams);
                    }
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void goNext(BaseResp resp){
        if(resp.getResult() instanceof LoginServerParams){
            if(resp.status == 200){
                LoginServerParams params = (LoginServerParams) resp.getResult();
                GoldenStewardApplication.getContext().setUid(params.getUid());
                GoldenStewardApplication.getContext().setToken(params.getToken());
                GoldenStewardApplication.getContext().setName(mParams.getPhone());
                Intent intent = new Intent(this, StoreOwnerVerifyActivity.class);
                startActivity(intent);
                finish();
            }
            ShowWidgetUtil.showShort(resp.msg);
        }
    }

    @Override
    public void onBackPressed() {
        if (mGoNextStep) {
            viewDelegate.onClickBackOnSecondStep();
            mGoNextStep = false;
        } else {
            super.onBackPressed();
        }
    }


}
