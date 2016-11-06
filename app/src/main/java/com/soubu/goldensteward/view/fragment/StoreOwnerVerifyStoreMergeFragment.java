package com.soubu.goldensteward.view.fragment;

import android.content.Context;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.StoreOwnerVerifyStoreMergeFragmentDelegate;
import com.soubu.goldensteward.module.server.MergeServerParams;

/**
 * Created by lakers on 16/10/28.
 */

public class StoreOwnerVerifyStoreMergeFragment extends FragmentPresenter<StoreOwnerVerifyStoreMergeFragmentDelegate>
        implements View.OnClickListener {
    OnClickFinishListener mOnClickFinishListener;
    Context mContext;

    @Override
    protected Class<StoreOwnerVerifyStoreMergeFragmentDelegate> getDelegateClass() {
        return StoreOwnerVerifyStoreMergeFragmentDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.btn_next_step, R.id.tv_add_phone);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnClickFinishListener) {
            mOnClickFinishListener = (OnClickFinishListener) context;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_step:
                MergeServerParams params = new MergeServerParams();
                if(viewDelegate.checkComplete(params)){
                    if (mOnClickFinishListener != null) {
                        mOnClickFinishListener.onClickFinish(params);
                    }
                }
                break;
            case R.id.tv_add_phone:
                viewDelegate.clickAdd();
                break;
        }
    }

    public interface OnClickFinishListener {
        void onClickFinish(MergeServerParams params);
    }

}
