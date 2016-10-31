package com.soubu.goldensteward.delegate;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.R;
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
    }

    public void addPhoneItem(String phone) {
        mController.addPhoneItem(phone, true);
    }


    public void clickAdd() {
        String phone = etChildPhone.getText().toString();
        if (RegularUtil.isMobile(phone)) {
            addPhoneItem(phone);
            etChildPhone.setText("");
        } else {
            ShowWidgetUtil.showShort(R.string.please_input_correct_phone);
        }
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
    public void onSelected(String content) {

    }

    @Override
    public void onUnSelected(String content) {

    }
}
