package com.soubu.goldensteward.ui.setting;

import android.content.Intent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.mvp.view.AppDelegate;
import com.soubu.goldensteward.ui.register.ProtocolActivity;

/**
 * Created by lakers on 16/10/31.
 */

public class AboutActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initWidget() {
        super.initWidget();
//        get(R.id.tv_contact_customer_service).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + getActivity().getString(R.string.customer_service_phone_number)));
//                getActivity().startActivity(intent);
//            }
//        });
        get(R.id.tv_service_protocol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProtocolActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
