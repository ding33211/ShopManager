package com.soubu.goldensteward.ui.wallet;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.constant.IntentKey;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.bean.server.IncomeOrExpensesServerParams;
import com.soubu.goldensteward.support.utils.ConvertUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by dingsigang on 16-10-20.
 */
public class IncomeOrExpensesFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {
    public static final int TYPE_INCOME = 0x00;
    public static final int TYPE_EXPENSES = 0x01;


    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        int type = getArguments().getInt(IntentKey.EXTRA_TYPE);
        SingleAdapter adapter = new SingleAdapter<IncomeOrExpensesServerParams>(getActivity(), R.layout.item_income_or_expenses_recyclerview) {
            @Override
            protected void bindData(BaseViewHolder holder, IncomeOrExpensesServerParams item, int position) {
                TextView tvSymbol = holder.getView(R.id.tv_symbol);
                tvSymbol.setText(R.string.plus);
                TextView tvAmount = holder.getView(R.id.tv_amount);
                TextView tvState = holder.getView(R.id.tv_state);
                TextView tvFor = holder.getView(R.id.tv_for);
                TextView tvDesc = holder.getView(R.id.tv_desc);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvCustomerService = holder.getView(R.id.tv_customer_service);
                View vCustomerService = holder.getView(R.id.ll_customer_service);
                View vItemBottomLine = holder.getView(R.id.v_bottom_line);
                vItemBottomLine.setVisibility(View.VISIBLE);
                if (position == getItemCount() - 1) {
                    vItemBottomLine.setVisibility(View.INVISIBLE);
                }
                tvState.setText(item.getStatus());
                tvTime.setText(item.getAdd_time() == null ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm_ss(new Date(Long.valueOf(item.getAdd_time()) * 1000)));
                if (type == TYPE_EXPENSES) {
                    tvSymbol.setText(R.string.minus);
                    tvAmount.setText(item.getPrice());
                    tvFor.setText(item.getType());
                } else {
                    tvAmount.setText(item.getReal_get());
                    if (!TextUtils.isEmpty(item.getOrder_type())) {
                        tvFor.setText(item.getOrder_type() + "购买");
                        if (!TextUtils.isEmpty(item.getMessage())) {
                            tvDesc.setText("-" + item.getMessage());
                        }
                    } else {
                        if (!TextUtils.isEmpty(item.getMessage())) {
                            tvDesc.setText(item.getMessage());
                        }
                    }
                }
            }
        };
        viewDelegate.setAdapter(adapter);
    }

    public void setData(List<IncomeOrExpensesServerParams> list){
        viewDelegate.setData(list);
    }
}
