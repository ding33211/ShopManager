package com.soubu.goldensteward.storepreview;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.storepreview.ProductClassificationActivity;
import com.soubu.goldensteward.storepreview.StorePreviewActivityDelegate;
import com.soubu.goldensteward.module.Constant;
import com.soubu.goldensteward.module.server.ProductPreviewSeverParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-11-25.
 */

public class StorePreviewActivity extends ActivityPresenter<StorePreviewActivityDelegate> implements View.OnClickListener {
    private int mFrom = 0;
    //来自产品分类
    public static final int FROM_PRODUCT_CLASSIFICATION = 0x01;


    @Override
    protected Class<StorePreviewActivityDelegate> getDelegateClass() {
        return StorePreviewActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mFrom = getIntent().getIntExtra(Constant.EXTRA_FROM, 0);
        if (mFrom != 0) {
            String title = getIntent().getStringExtra(Constant.EXTRA_CONTENT);
            if (!TextUtils.isEmpty(title)) {
                viewDelegate.setTitle(title);
                viewDelegate.initFromProductClassification();
            }
        } else {
            viewDelegate.getToolbar().setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        viewDelegate.initTabLayout(new String[]{getString(R.string.store_home), getString(R.string.all_product),
                getString(R.string.new_arrivals), getString(R.string.evaluate) + "(" + "100" + ")"});
        List<ProductPreviewSeverParams> list = new ArrayList<>();
        ProductPreviewSeverParams params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdio无法服务发挖坟啊发骚发啊pajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi3/1086822446/TB2J7xAmXXXXXb3XXXXXXXXXXXX_!!1086822446.jpg_290x290xz.jpg");
        params.setTitle("jsdsa发擦拭发起问题jdiopajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/TB16oLOLVXXXXbWXXXXXXXXXXXX_!!0-item_pic.jpg_290x290xz.jpg");
        params.setTitle("jsds飞洒发我服务器ajdiopajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi3/TB1AhLyGFXXXXbgXpXXXXXXXXXX_!!0-item_pic.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploaded/i3/2855113287/TB2M91GdVOP.eBjSZFHXXXQnpXa_!!2855113287.jpg_290x290xz.jpg_.webp");
        params.setTitle("jsds福娃发顺丰艾丝凡无法我ajdiopajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploaded/i1/TB19YrpLXXXXXb0aXXXXXXXXXXX_!!0-item_pic.jpg_290x290xz.jpg_.webp");
        params.setTitle("jsdsajdiopajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskopkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadosko我发萨芬爱我pkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadosko我都发我阿帆萨芬pkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskof撒是否爱是pkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskf打撒发飞我opkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskd阿斯达大访问我打死opkcsmsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskopkc发送方撒发发是否啊smsmdc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        params = new ProductPreviewSeverParams();
        params.setUrl("https://img.alicdn.com/bao/uploadedi1/2135772166/TB2wxx7aXXXXXaTXpXXXXXXXXXX_!!2135772166.jpg_290x290xz.jpg");
        params.setTitle("jsdsajdiopajdpoadoskopkcsmsmdadadadadc");
        params.setPrice("45.0");
        params.setBrowser_volume("24");
        list.add(params);
        viewDelegate.setData(list);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this, R.id.ll_back_1, R.id.tv_product_classification, R.id.bt_complex,
                R.id.bt_new_product, R.id.ll_price, R.id.fab_up);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back_1:
                finish();
                break;
            case R.id.tv_product_classification:
                Intent intent = new Intent(this, ProductClassificationActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_complex:
                viewDelegate.setSelected(0);
                break;
            case R.id.bt_new_product:
                viewDelegate.setSelected(1);
                break;
            case R.id.ll_price:
                viewDelegate.setSelected(2);
                break;
            case R.id.fab_up:
                viewDelegate.clickFab();
                break;
        }
    }
}
