/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soubu.goldensteward.base.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.utils.ShowWidgetUtil;


/**
 * View delegate base class
 * 视图层代理的基类
 */
public abstract class AppDelegate implements IDelegate {
    protected final SparseArray<View> mViews = new SparseArray<View>();

    //根布局
    protected View rootView;

    //减去toolbar的内容布局
    protected View contentView;

    /**
     * 获取布局id
     *
     * @return R文件布局id (R.layout.xxxx)
     */
    public abstract int getRootLayoutId();

    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = getRootLayoutId();
        contentView = inflater.inflate(rootLayoutId, container, false);
        rootView = inflater.inflate(R.layout.activity_base_with_toolbar, container, false);
        //如果需要隐藏toolbar或者是要求全屏,就不添加toolbar
        if (!ifNeedHideToolBar() && !ifNeedFullScreen()) {
            ((LinearLayout) rootView.findViewById(R.id.ll_content)).addView(contentView);
            if (ifNeedSearch()) {
                get(R.id.rl_normal).setVisibility(View.GONE);
                get(R.id.rl_search).setVisibility(View.VISIBLE);
                get(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }
        }
    }

    public int getOptionsMenuId() {
        return 0;
    }

    public Toolbar getToolbar() {
        if (ifNeedHideToolBar() || ifNeedFullScreen()) {
            return null;
        }
        return get(R.id.toolbar);
    }

    /**
     * 获取当前根布局view
     *
     * @return rootVIew
     */
    public View getRootView() {
        if (ifNeedHideToolBar() || ifNeedFullScreen()) {
            return contentView;
        }
        return rootView;
    }

    /**
     * 设置当前根布局view
     *
     * @param rootView
     */
    public void setRootView(View rootView) {
        if (ifNeedHideToolBar()) {
            this.contentView = rootView;
        } else {
            this.rootView = rootView;
        }
    }

    /**
     * 初始化类内部独有控件
     */
    public void initWidget() {
    }

    private <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) getRootView().findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    /**
     * 直接通过id获取view
     *
     * @param id  控件id
     * @param <T> T extends View
     * @return T
     */
    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    /**
     * 快捷设置监听
     *
     * @param listener
     * @param ids
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public void showToast(String msg) {
        ShowWidgetUtil.showShort(msg);
    }

    /**
     * 获取Activity对象
     *
     * @param <T> T extends AppCompatActivity
     * @return T
     */
    public <T extends AppCompatActivity> T getActivity() {
        return (T) getRootView().getContext();
    }

    /**
     * 是否需要全屏展示
     *
     * @return
     */
    public boolean ifNeedFullScreen() {
        return false;
    }


    /**
     * 是否需要全屏展示
     *
     * @return
     */
    public boolean ifNeedSearch() {
        return false;
    }

    /**
     * 是否需要隐藏toolbar
     */
    public boolean ifNeedHideToolBar() {
        return false;
    }

    //默认不需要使用到EventBus
    @Override
    public boolean ifNeedEventBus() {
        return false;
    }

    /**
     * 设置toolbar 标题
     *
     * @param titleRes
     */
    public void setTitle(int titleRes) {
        ((TextView) getToolbar().findViewById(R.id.tv_toolbar_title)).setText(titleRes);
    }

    public void setTitle(String title) {
        ((TextView) getToolbar().findViewById(R.id.tv_toolbar_title)).setText(title);
    }

    public void setRightMenuOne(int drawableRes, View.OnClickListener listener) {
        ImageView imageView = (ImageView) getToolbar().findViewById(R.id.iv_menu_r_1);
        imageView.setImageResource(drawableRes);
        View v = get(R.id.ll_menu_r_1);
        v.setOnClickListener(listener);
        v.setVisibility(View.VISIBLE);
    }

    public void setRightMenuTwo(int drawableRes, View.OnClickListener listener) {
        ImageView imageView = (ImageView) getToolbar().findViewById(R.id.iv_menu_r_2);
        imageView.setImageResource(drawableRes);
        View v = get(R.id.ll_menu_r_2);
        v.setOnClickListener(listener);
        v.setVisibility(View.VISIBLE);
    }

    public void setSettingMenuListener(final int menuRes, final PopupMenu.OnMenuItemClickListener listener) {
        final ImageView imageView = (ImageView) getToolbar().findViewById(R.id.iv_setting);
        View v = get(R.id.ll_setting_image);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(imageView.getContext(), v);
                popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(listener);
                popupMenu.show();
            }
        });
        v.setVisibility(View.VISIBLE);
    }

    public void setSettingText(int titleRes, View.OnClickListener listener) {
        TextView textView = (TextView) getToolbar().findViewById(R.id.tv_setting);
        textView.setText(titleRes);
        View v = get(R.id.ll_setting_text);
        v.setOnClickListener(listener);
        v.setVisibility(View.VISIBLE);
    }


}
