package com.soubu.goldensteward.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.CustomersModule;
import com.soubu.goldensteward.utils.PinyinComparator;
import com.soubu.goldensteward.widget.RecyclerViewFastScroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dingsigang on 16-8-25.
 */
public class MyCustomersRvAdapter extends BaseRecyclerViewAdapter<CustomersModule> implements RecyclerViewFastScroller.BubbleTextGetter {
    private final int TYPE_TOP = 0x00;
    private final int TYPE_MID = 0x01;
    private final int TYPE_BOT = 0x02;
    private final int TYPE_ONLY = 0x03;

    public MyCustomersRvAdapter(Context context) {
        mList = new ArrayList<>();
//        DBHelper helper = DBHelper.getInstance(context);
        CustomersModule customersModule = new CustomersModule();
        customersModule.setName("张老四");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("极大");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("管控");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("发哦");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("给你哦啊");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("我就");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("迫切渴望");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("via");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("风貌");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("围绕你");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("蘑菇");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("我没钱");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("蘑菇");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("的才能杀");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("偶尔玩");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("都为");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("傲娇");
        mList.add(customersModule);
        customersModule = new CustomersModule();
        customersModule.setName("二期");
        mList.add(customersModule);

//        mParams.addAll(helper.getStaffDao().loadAll());
        Collections.sort(mList, new PinyinComparator());
    }

    @Override
    public int getItemViewType(int position) {
        String thisLetter = mList.get(position).getLetter();
        String lastLetter = null;
        String nextLetter = null;
        if (position != 0) {
            lastLetter = mList.get(position - 1).getLetter();
        }
        if (position != getItemCount() - 1) {
            nextLetter = mList.get(position + 1).getLetter();
        }
        if (TextUtils.equals(thisLetter, lastLetter)) {
            if (TextUtils.equals(thisLetter, nextLetter)) {
                return TYPE_MID;
            } else {
                return TYPE_BOT;
            }
        } else {
            if (TextUtils.equals(thisLetter, nextLetter)) {
                return TYPE_TOP;
            } else {
                return TYPE_ONLY;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_customer_recycler, parent, false);
        View vLine = view.findViewById(R.id.v_bottom_line);
        View vLetter = view.findViewById(R.id.ll_letter);
        switch (viewType) {
            case TYPE_ONLY:
                vLine.setVisibility(View.GONE);
                break;
            case TYPE_MID:
                vLetter.setVisibility(View.GONE);
                break;
            case TYPE_BOT:
                vLetter.setVisibility(View.GONE);
                vLine.setVisibility(View.GONE);
                break;
            case TYPE_TOP:
                break;
        }
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            holder1.tvName.setText(mList.get(position).getName());
//            holder1.tvDealCount.setText(mParams.get(position).getDepartment());
//            holder1.tvPosition.setText(mParams.get(position).getPosition());
            holder1.tvLetter.setText(mList.get(position).getLetter());
        }

    }


    @Override
    public String getTextToShowInBubble(final int pos) {
        return mList.get(pos).getLetter();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvDealCount;
        TextView tvLastDeal;
        ImageView ivAvatar;
        TextView tvLetter;

        private ItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDealCount = (TextView) itemView.findViewById(R.id.tv_traded_order_count);
            tvLastDeal = (TextView) itemView.findViewById(R.id.tv_last_deal_time);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvLetter = (TextView) itemView.findViewById(R.id.tv_letter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClick(getLayoutPosition());
            }
        }
    }

}
