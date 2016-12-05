package com.soubu.goldensteward.information;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

/**
 * Created by lakers on 16/11/4.
 */

public class ModifyInfoActivityDelegate extends AppDelegate {

    EditText mEtContent;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_modify_info;
    }

    public void refreshProvinceAndCity(String pc) {
        get(R.id.ll_location_change).setVisibility(View.VISIBLE);
        ((TextView) get(R.id.tv_province_and_city)).setText(pc);
    }

    public void refreshEditText(String content, String label, String hint) {
        if (!TextUtils.isEmpty(label)) {
            TextView tvLabel = get(R.id.tv_label);
            tvLabel.setVisibility(View.VISIBLE);
            tvLabel.setText(label);
        }
        mEtContent = get(R.id.et_content);
        mEtContent.setText(content);
        mEtContent.setHint(hint);
        mEtContent.setSelection(mEtContent.getText().length());
    }


    public String checkComplete() {
        String content = mEtContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ShowWidgetUtil.showShort(getActivity().getString(R.string.something_can_not_empty, getTitle()));
            return null;
        }
        return content;
    }
}
