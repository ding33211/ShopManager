package com.soubu.goldensteward.delegate;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.server.MergeServerParams;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.server.RetrofitRequest;
import com.soubu.goldensteward.utils.RegularUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.widget.flowlayout.FlowLayout;
import com.soubu.goldensteward.widget.flowlayout.FlowLayoutController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyStoreMergeFragmentDelegate extends BaseFragmentDelegate implements FlowLayoutController.OnEventCallBackListener<String> {
    FlowLayout mFlPhones;
    FlowLayoutController mController;
    List<String> mPhones;
    EditText etChildPhone;
    View vBottomLine;
//    EditText etMainPhone;


    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_store_owner_store_merge;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        int colorPrimary = getActivity().getResources().getColor(R.color.colorPrimary);
        ((TextView) get(R.id.tv_step2)).setTextColor(colorPrimary);
        get(R.id.tv_step2_num).setBackgroundResource(R.drawable.bg_orange_circle);
        get(R.id.v_2_to_3).setBackgroundColor(colorPrimary);
        ((TextView) get(R.id.tv_step3)).setTextColor(colorPrimary);
        get(R.id.tv_step3_num).setBackgroundResource(R.drawable.bg_orange_circle);
        mFlPhones = get(R.id.fl_child_phones);
        etChildPhone = get(R.id.et_child_phone);
        vBottomLine = get(R.id.v_bottom_line);
        mController = new FlowLayoutController(mFlPhones);
        mController.setOnEventCallBack(this);
        mPhones = new ArrayList<>();
//        etMainPhone = get(R.id.et_main_phone);
//        etMainPhone.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(TextUtils.isEmpty(s)){
//                    get(R.id.iv_clear).setVisibility(View.INVISIBLE);
//                } else {
//                    get(R.id.iv_clear).setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    public void moveTop() {
        get(R.id.include).setVisibility(View.GONE);
    }

    public void addPhoneItem(String phone) {
        mController.addPhoneItem(phone, true);
    }


    public void clickAdd() {
        String phone = etChildPhone.getText().toString();
        if (RegularUtil.isMobile(phone)) {
            UserServerParams params = new UserServerParams();
            params.setPhone(phone);
            RetrofitRequest.getInstance().checkChildPhone(params);
        } else {
            ShowWidgetUtil.showShort(R.string.wrong_phone);
        }
    }

    public void onCheckSuccess() {
        addPhoneItem(etChildPhone.getText().toString());
        etChildPhone.setText("");
    }

    @Override
    public void onAdd(String content) {
        mPhones.add(content);
        if (mPhones.size() > 0) {
            mFlPhones.setVisibility(View.VISIBLE);
            vBottomLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDelete(String content) {
        mPhones.remove(content);
        if (mPhones.size() == 0) {
            mFlPhones.setVisibility(View.GONE);
            vBottomLine.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSelected(String content) {
        return true;
    }

    @Override
    public void onUnSelected(String content) {

    }

    public boolean checkComplete(MergeServerParams params) {
        if (mPhones.size() == 0) {
            ShowWidgetUtil.showShort(R.string.please_input_child_phone);
            return false;
        }
        params.setChild_phone(mPhones.toArray(new String[mPhones.size()]));
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
