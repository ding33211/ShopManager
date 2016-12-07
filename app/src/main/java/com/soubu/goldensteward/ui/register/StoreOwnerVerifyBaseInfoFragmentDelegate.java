package com.soubu.goldensteward.ui.register;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.support.base.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.delegate.BaseFragmentDelegate;
import com.soubu.goldensteward.support.bean.server.VerificationServerParams;
import com.soubu.goldensteward.support.utils.RegularUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyBaseInfoFragmentDelegate extends BaseFragmentDelegate {

    private int mOperatorEmptyMessageRes = R.string.please_fill_enterprise_name;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_store_owner_verify_base_info;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ((TextView) get(R.id.et_mobile)).setText(GoldenStewardApplication.getContext().getPhone());
    }


    public void refreshContent(int resId, String content) {
        TextView textView = get(resId);
        textView.setText(content);
        textView.setTextColor(getActivity().getResources().getColor(R.color.title_black));
    }

    public void refreshOperator(int titleRes, int hintRes) {
        ((TextView) get(R.id.tv_operator)).setText(titleRes);
        ((EditText) get(R.id.et_operator)).setHint(hintRes);
        mOperatorEmptyMessageRes = hintRes;
    }


    public boolean checkComplete(VerificationServerParams params) {
        if (TextUtils.isEmpty(params.getCom_type())) {
            ShowWidgetUtil.showShort(R.string.please_choose_company_type);
            return false;
        }

        if (TextUtils.isEmpty(params.getFile_type())) {
            ShowWidgetUtil.showShort(R.string.please_choose_certification_type);
            return false;
        }
        String company = ((EditText) get(R.id.et_operator)).getText().toString();
        if (TextUtils.isEmpty(company)) {
            ShowWidgetUtil.showShort(mOperatorEmptyMessageRes);
            return false;
        }
        String name = ((EditText) get(R.id.et_legal_person)).getText().toString();
        if (TextUtils.isEmpty(name)) {
            ShowWidgetUtil.showShort(R.string.please_fill_legal_person);
            return false;
        }
        String id = ((EditText) get(R.id.et_id_num)).getText().toString();
        if (TextUtils.isEmpty(id)) {
            ShowWidgetUtil.showShort(R.string.please_fill_id_num);
            return false;
        }
        if (!RegularUtil.isId(id)) {
            ShowWidgetUtil.showShort(R.string.wrong_id);
            return false;
        }
        String mobile = ((EditText) get(R.id.et_mobile)).getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ShowWidgetUtil.showShort(R.string.please_fill_mobile);
            return false;
        }
//        if (!RegularUtil.isMobile(mobile)) {
//            ShowWidgetUtil.showShort(R.string.wrong_phone);
//            return false;
//        }
        params.setCompany(company);
        params.setPerson(name);
        params.setId_num(id);
        params.setPhone(mobile);
        return true;
    }
}
