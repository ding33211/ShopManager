package com.soubu.goldensteward.view.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.SubAccountFragmentDelegate;
import com.soubu.goldensteward.view.activity.SubAccountSpecActivity;

/**
 * Created by dingsigang on 16-10-18.
 */
public class SubAccountFragment extends FragmentPresenter<SubAccountFragmentDelegate> {
    @Override
    protected Class<SubAccountFragmentDelegate> getDelegateClass() {
        return SubAccountFragmentDelegate.class;
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnRvItemSelectListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), SubAccountSpecActivity.class);
                startActivity(intent);
            }
        });
    }


}
