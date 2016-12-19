package com.soubu.goldensteward.ui.register;

import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.delegate.BaseFragmentDelegate;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyUploadCertificatesFragmentDelegate extends BaseFragmentDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_store_owner_verify_upload_certificates;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        int colorPrimary = getActivity().getResources().getColor(R.color.colorPrimary);
        ((TextView) get(R.id.tv_step2)).setTextColor(colorPrimary);
        get(R.id.tv_step2_num).setBackgroundResource(R.drawable.bg_orange_circle);
        get(R.id.v_2_to_3).setBackgroundColor(colorPrimary);
    }

    public void setFileType(int type) {
        if (type == 2) {
            get(R.id.iv_business_code).setVisibility(View.GONE);
            ((TextView) get(R.id.tv_file_name)).setText(R.string.many_license_in_one);
        } else {
            get(R.id.iv_business_code).setVisibility(View.VISIBLE);
            ((TextView) get(R.id.tv_file_name)).setText(R.string.business_license_and_organization_code_certificate);
        }
    }

    public void setName(String name) {
        ((TextView) get(R.id.tv_name)).setText(name);
    }
}
