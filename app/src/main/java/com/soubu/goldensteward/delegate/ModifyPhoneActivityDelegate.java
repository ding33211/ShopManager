package com.soubu.goldensteward.delegate;

import android.view.View;
import android.widget.Button;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;


/**
 * Created by lakers on 16/10/31.
 */

public class ModifyPhoneActivityDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        get(R.id.ll_tab).setVisibility(View.GONE);
        get(R.id.ll_modify_phone_top).setVisibility(View.VISIBLE);
        ((Button) get(R.id.btn_confirm)).setText(R.string.next_step);
    }


}
