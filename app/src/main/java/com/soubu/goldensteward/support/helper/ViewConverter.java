package com.soubu.goldensteward.support.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.goldensteward.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 视图变换器
 * <p>
 * 1、自动给ContenView加上EmptyView
 * 2、自动给ContenView加上InternetView
 * 3、自动给ContenView加上ServerView
 * <p>
 * 作者：余天然 on 2016/12/19 下午4:12
 */
public class ViewConverter {

    public static View setErrorView(View srcView, ViewType type) {
        switch (type) {
            case ERROR_EMPTY:
                return setEmptyView(srcView);
            case ERROR_INTERNET:
                return setInternetView(srcView);
            case ERROR_SERVER:
                return setServerView(srcView);
            default:
                return srcView;
        }
    }

    public static View setEmptyView(View contentView) {
        return setEmptyView(contentView, R.string.sign_up_empty_product_desc);
    }

    public static View setEmptyView(View contentView, int tipId) {
        View view = replaceView(contentView, R.layout.error_empty);
        TextView tvEmptyDesc = (TextView) view.findViewById(R.id.tv_empty_desc);
        tvEmptyDesc.setText(tipId);
        return view;
    }

    public static View setInternetView(View contentView) {
        return replaceView(contentView, R.layout.error_internet);
    }

    public static View setServerView(View contentView) {
        return replaceView(contentView, R.layout.error_server);
    }

    public static View replaceView(View stcView, View destView) {
        Context context = stcView.getContext();
        ViewGroup viewParent = (ViewGroup) stcView.getParent();
        if (destView.getParent() != null) {
            destView.setVisibility(VISIBLE);
        } else {
            viewParent.addView(destView);
        }
        stcView.setVisibility(GONE);
        return destView;
    }

    public static View replaceView(View srcView, int destId) {
        Context context = srcView.getContext();
        ViewGroup viewParent = (ViewGroup) srcView.getParent();
        View destView = LayoutInflater.from(context).inflate(destId, viewParent, false);
        if (destView.getParent() != null) {
            destView.setVisibility(VISIBLE);
        } else {
            viewParent.addView(destView);
        }
        srcView.setVisibility(GONE);
        return destView;
    }

    public static View addToolBar(Activity activity, int contentId, int titleId) {
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.view_with_toolbar, null, false);
        View contentView = LayoutInflater.from(activity).inflate(contentId, rootView, false);
        //数据初始化完成后，再显示ContentView
        FrameLayout frContent = (FrameLayout) rootView.findViewById(R.id.fr_content);
        frContent.addView(contentView);

        View rlBack = rootView.findViewById(R.id.rl_back);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_toolbar_title);

        tvTitle.setText(titleId);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return rootView;
    }

}
