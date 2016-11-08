package com.soubu.goldensteward.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.server.IncomeOrExpensesServerParams;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.utils.RegularUtil;

import java.util.Date;

/**
 * Created by dingsigang on 16-10-20.
 */
public class IncomeOrExpensesRvAdapter extends BaseRecyclerViewAdapter<IncomeOrExpensesServerParams> {
    public static final int TYPE_INCOME = 0x00;
    public static final int TYPE_EXPENSES = 0x01;
    private int mType;
    private String[] mStates;
    private String[] mTypes;

    public IncomeOrExpensesRvAdapter(int type, Context context) {
        mType = type;
        mStates = context.getResources().getStringArray(R.array.order_state);
        mTypes = context.getResources().getStringArray(R.array.product_type);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income_or_expenses_recyclerview, parent, false);
        TextView tvSymbol = (TextView) v.findViewById(R.id.tv_symbol);
        if (mType == TYPE_EXPENSES) {
            tvSymbol.setText(R.string.minus);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            if (holder1.getLayoutPosition() == getItemCount() - 1) {
                holder1.vItemBottomLine.setVisibility(View.INVISIBLE);
            }
            IncomeOrExpensesServerParams item = mList.get(position);
            holder1.tvAmount.setText(item.getPrice());
            if(RegularUtil.isInteger(item.getType())){
                holder1.tvFor.setText(mTypes[Integer.valueOf(item.getType()) - 1] + "购买" + "-");
            } else {
                holder1.tvFor.setText(item.getType());
            }
            holder1.tvState.setText(mStates[Integer.valueOf(item.getStatus()) - 1]);
            if (!TextUtils.isEmpty(item.getName())) {
                holder1.tvDesc.setText(item.getName());
            } else {
                holder1.tvDesc.setText(item.getMessage());
            }
            holder1.tvTime.setText(item.getAdd_time() == null ? "" : ConvertUtil.dateToYYYY_MM_DD_HH_mm_ss(new Date(Long.valueOf(item.getAdd_time()) * 1000)));
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvAmount;
        public TextView tvState;
        public TextView tvFor;
        public TextView tvDesc;
        public TextView tvTime;
        public TextView tvCustomerService;
        public View vCustomerService;


        public View vItemBottomLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvFor = (TextView) itemView.findViewById(R.id.tv_for);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvCustomerService = (TextView) itemView.findViewById(R.id.tv_customer_service);
            vCustomerService = itemView.findViewById(R.id.ll_customer_service);
            vItemBottomLine = itemView.findViewById(R.id.v_bottom_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
