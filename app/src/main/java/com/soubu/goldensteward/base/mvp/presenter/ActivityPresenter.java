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
package com.soubu.goldensteward.base.mvp.presenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.BaseActivity;
import com.soubu.goldensteward.base.mvp.view.IDelegate;

import org.greenrobot.eventbus.EventBus;


/**
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 */
public abstract class ActivityPresenter<T extends IDelegate> extends BaseActivity {
    protected T viewDelegate;

    public ActivityPresenter() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewDelegate.ifNeedEventBus()) {
            EventBus.getDefault().register(this);
        }
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        if (viewDelegate.ifNeedFullScreen()) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(viewDelegate.getRootView());
        initToolbar();
        initView();
        viewDelegate.initWidget();
        bindEvenListener();
        initData();
    }

    //初始化一些布局view
    protected void initView() {
    }

    //初始化数据
    protected void initData() {
    }

    //绑定event
    protected void bindEvenListener() {
    }

    protected void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolbar();
        if (toolbar != null) {
            toolbar.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //默认toolbar显示返回按钮
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            toolbar.setLogo(getResources().getDrawable(R.drawable.bg_filter_and_sort));
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onBackPressed();
//                }
//            });
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewDelegate.ifNeedEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();

}
