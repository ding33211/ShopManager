package com.soubu.goldensteward.ui.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.OperationReportRvItem;

/**
 * Created by dingsigang on 16-10-21.
 */
public class OperationReportRvAdapter extends BaseRecyclerViewAdapter<OperationReportRvItem> {
    private final int CLICKABLE = 0x00;
    private final int UN_CLICKABLE = 0x01;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operation_report_recyclerview, parent, false);
        View vChoose = v.findViewById(R.id.iv_choose);
        switch (viewType) {
            case UN_CLICKABLE:
                vChoose.setVisibility(View.GONE);
                break;
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            holder1.tvLabel.setText(mList.get(position).getLabel());
            holder1.gvContainer.setAdapter(mList.get(position).getAdapter());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).isClickable()) {
            return CLICKABLE;
        } else {
            return UN_CLICKABLE;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvLabel;
        GridView gvContainer;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
            gvContainer = (GridView) itemView.findViewById(R.id.gv_container);
            itemView.findViewById(R.id.ll_label).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getItemViewType() == CLICKABLE && mListener != null) {
                mListener.onClick(getLayoutPosition());
            }

        }
    }


}
