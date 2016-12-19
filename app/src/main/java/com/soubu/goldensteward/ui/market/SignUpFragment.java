package com.soubu.goldensteward.ui.market;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.bean.server.ProductInSignUpActivityServerParams;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.helper.ViewConverter;
import com.soubu.goldensteward.support.helper.ViewType;
import com.soubu.goldensteward.support.utils.GlideUtils;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;
import com.soubu.goldensteward.support.web.mvp.BaseMvpFragment;
import com.soubu.goldensteward.support.widget.pullrefresh.RefreshHelper;
import com.soubu.goldensteward.support.widget.pullrefresh.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

import static android.app.Activity.RESULT_OK;
import static com.soubu.goldensteward.R.string.total;

/**
 * Created by dingsigang on 16-12-6.
 */

public class SignUpFragment extends BaseMvpFragment<SignUpPresenter> implements SignUpView, RefreshHelper.RefreshInterface<ProductInSignUpActivityServerParams> {

    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.view_refresh)
    RefreshLayout viewRefresh;

    public RefreshHelper refreshHelper;
    private List<String> mCheckedProductId;
    private String mAccountId;
    private int mActivityId;
    private View errorView;

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_signup;
    }

    @Override
    protected SignUpPresenter createPresenter() {
        mCheckedProductId = new ArrayList<>();
        mAccountId = getActivity().getIntent().getStringExtra(IntentKey.EXTRA_ACCOUNT_ID);
        mActivityId = getActivity().getIntent().getIntExtra(IntentKey.EXTRA_ACTIVITY_ID, -1);
        return new SignUpPresenter(mAccountId);
    }

    @Override
    public Observable<List<ProductInSignUpActivityServerParams>> getData(int curPage) {
        return presenter.getData(curPage);
    }

    @Override
    public void bindData(BaseViewHolder holder, ProductInSignUpActivityServerParams item, int position) {
        View vBottom1 = holder.getView(R.id.ll_bottom);
        vBottom1.setVisibility(View.GONE);
        CheckBox cbChoose = holder.getView(R.id.cb_choose);
        ImageView ivProductImg = holder.getView(R.id.iv_product);
        TextView tvProductName = holder.getView(R.id.tv_name);
        TextView tvSamplePrice = holder.getView(R.id.tv_sample_card_price);
        TextView tvBigGoodsPrice = holder.getView(R.id.tv_big_goods_price);
        TextView tvSampleUnit = holder.getView(R.id.tv_sample_unit);
        TextView tvBigGoodsUnit = holder.getView(R.id.tv_big_goods_unit);
        GlideUtils.loadRoundedImage(ivProductImg.getContext(), ivProductImg, item.getCover(), R.drawable.common_product_placeholder, R.drawable.common_product_placeholder);
        tvProductName.setText(item.getName());
        tvSamplePrice.setText(item.getCut_price());
        tvBigGoodsPrice.setText(item.getPrice());
        tvSampleUnit.setText(item.getCut_units());
        tvBigGoodsUnit.setText(item.getUnit());
        if (mCheckedProductId.contains(item.getPro_id())) {
            cbChoose.setChecked(true);
        } else {
            cbChoose.setChecked(false);
        }
    }

    @Override
    public void bindListener(BaseViewHolder holder, ProductInSignUpActivityServerParams item, int position) {

    }

    @Override
    public void onItemClick(BaseViewHolder holder, ProductInSignUpActivityServerParams item, int position) {
        CheckBox cbChoose = holder.getView(R.id.cb_choose);
        if (cbChoose.isChecked()) {
            cbChoose.setChecked(false);
        } else {
            cbChoose.setChecked(true);
        }
        String id = item.getPro_id();
        if (!mCheckedProductId.contains(id)) {
            mCheckedProductId.add(id);
        } else {
            mCheckedProductId.remove(id);
        }

        tvTotal.setText(total + "");

        refreshTotalText(mCheckedProductId.size());
    }

    public void refreshTotalText(int total) {
        tvTotal.setText(total + "");
    }

    @OnClick(R.id.tv_commit)
    public void onClick() {
        presenter.commit(mAccountId, mActivityId, mCheckedProductId);
    }

    public void onCommitSuccess(int num) {
        ShowWidgetUtil.showShort("提交成功");
        View successView = ViewConverter.replaceView(rootView, R.layout.view_signup_success);

        TextView tvTitle = (TextView) getActivity().findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(R.string.sign_up_success);

        TextView tvSignUpCount = (TextView) getActivity().findViewById(R.id.tv_sign_up_count);
        tvSignUpCount.setText(String.valueOf(num));

        Button btReturn = (Button) successView.findViewById(R.id.bt_return);
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            }
        });
    }

    @Override
    public void initWidget() {
        refreshHelper = new RefreshHelper<ProductInSignUpActivityServerParams>(viewRefresh, this, R.layout.item_product_access_product_on_sale_recyclerview);
        refreshHelper.loadData();
        refreshHelper.setDataInterface(new RefreshHelper.DataInterface() {
            @Override
            public void onData(ViewType type) {
                LogUtil.print("type=" + type);
                getActivity().findViewById(R.id.fr_content).setVisibility(View.VISIBLE);
                if (type != ViewType.CONTENT) {
                    errorView = ViewConverter.setErrorView(rootView, type);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        refreshHelper.detachView();
        super.onDestroyView();
    }
}
