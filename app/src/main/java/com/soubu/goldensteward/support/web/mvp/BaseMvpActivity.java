package com.soubu.goldensteward.support.web.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseActivity;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * 作者：余天然 on 2016/12/12 上午11:22
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    //根布局
    protected View rootView;

    //减去toolbar的内容布局
    protected View contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ifNeedFullScreen()) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        create(getLayoutInflater(), null, savedInstanceState);
        setContentView(getRootView());
        if (ifNeedEventBus()) {
            EventBus.getDefault().register(this);
        }
        initToolbar();
        ButterKnife.bind(this);
        presenter = createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter is null");
        }
        presenter.attachView(this);
    }

    private void initToolbar() {
        if (ifNeedHideToolBar()) {
            return;
        }
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private View getRootView() {
        if (ifNeedHideToolBar() || ifNeedFullScreen()) {
            return contentView;
        }
        return rootView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView(false);
    }

    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = createLayoutId();
        contentView = inflater.inflate(rootLayoutId, container, false);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView = inflater.inflate(R.layout.activity_base_with_toolbar, container, false);
        //如果需要隐藏toolbar或者是要求全屏,就不添加toolbar
        if (!ifNeedHideToolBar() && !ifNeedFullScreen()) {
            ((FrameLayout) rootView.findViewById(R.id.ll_content)).addView(contentView);
            if (ifNeedSearch()) {
                findViewById(R.id.rl_normal).setVisibility(View.GONE);
                findViewById(R.id.rl_search).setVisibility(View.VISIBLE);
                findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }

    /**
     * 创建布局id
     */
    protected abstract int createLayoutId();


    /**
     * 创建Presenter对象
     */
    protected abstract P createPresenter();

    /**
     * 获取Presenter对象
     */
    public P getPresenter() {
        return presenter;
    }

    /**
     * 是否需要全屏展示
     */
    public boolean ifNeedFullScreen() {
        return false;
    }

    /**
     * 是否需要搜索栏
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

    /**
     * 默认不需要使用到EventBus
     */
    public boolean ifNeedEventBus() {
        return false;
    }

    /**
     * 设置toolbar 标题
     *
     * @param titleRes
     */
    public void setTitleText(int titleRes) {
        ((TextView) findViewById(R.id.tv_toolbar_title)).setText(titleRes);
    }

    public void setTitleText(String title) {
        ((TextView) findViewById(R.id.tv_toolbar_title)).setText(title);
    }

    public String getTitleText() {
        return ((TextView) findViewById(R.id.tv_toolbar_title)).getText().toString();
    }

    public void setRightMenuOne(int drawableRes, View.OnClickListener listener) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_menu_r_1);
        imageView.setImageResource(drawableRes);
        View v = findViewById(R.id.ll_menu_r_1);
        v.setOnClickListener(listener);
        v.setVisibility(View.VISIBLE);
    }

    public void setRightMenuTwo(int drawableRes, View.OnClickListener listener) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_menu_r_2);
        imageView.setImageResource(drawableRes);
        View v = findViewById(R.id.ll_menu_r_2);
        v.setOnClickListener(listener);
        v.setVisibility(View.VISIBLE);
    }

    public void setSettingMenuListener(final int menuRes, final PopupMenu.OnMenuItemClickListener listener) {
        final ImageView imageView = (ImageView) findViewById(R.id.iv_setting);
        View v = findViewById(R.id.ll_setting_image);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(imageView.getContext(), v);
                popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(listener);
                try {
                    Field mPopup = popupMenu.getClass().getDeclaredField("mPopup");
                    mPopup.setAccessible(true);
                    MenuPopupHelper mHelper = (MenuPopupHelper) mPopup.get(popupMenu);
                    mHelper.setForceShowIcon(true);
                } catch (Exception e) {

                }
                popupMenu.show();
            }
        });
        v.setVisibility(View.VISIBLE);
    }

    public void setSettingText(int titleRes, View.OnClickListener listener) {
        TextView textView = (TextView) findViewById(R.id.tv_setting);
        textView.setText(titleRes);
        View v = findViewById(R.id.ll_setting_text);
        v.setOnClickListener(listener);
        v.setVisibility(View.VISIBLE);
    }

    @Override
    public void showShort(int resId) {
        ShowWidgetUtil.showShort(resId);
    }
}
