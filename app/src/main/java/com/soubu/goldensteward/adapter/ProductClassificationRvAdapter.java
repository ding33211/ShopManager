package com.soubu.goldensteward.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.server.ProductClassificationServerParams;

import java.util.Arrays;

/**
 * Created by dingsigang on 16-11-25.
 */

public class ProductClassificationRvAdapter extends BaseRecyclerViewAdapter<ProductClassificationServerParams> {
    private static final int HAVE_SUB = 0x00;
    private static final int HAVE_NO_SUB = 0x01;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_classification_recyclerview, parent, false);
        View gvSub = v.findViewById(R.id.gv_container);
        View tvViewAll = v.findViewById(R.id.tv_view_all);
        View vChoose = v.findViewById(R.id.iv_choose);
        if (viewType == HAVE_SUB) {
            gvSub.setVisibility(View.VISIBLE);
            tvViewAll.setVisibility(View.VISIBLE);
            vChoose.setVisibility(View.GONE);
        } else {
            gvSub.setVisibility(View.GONE);
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            ProductClassificationServerParams params = mList.get(position);
            holder1.tvTitle.setText(params.getMain());
            if (params.getSpecProducts() != null) {
                String[] titles = params.getSpecProducts();
                ProductClassificationGridViewAdapter adapter = new ProductClassificationGridViewAdapter(holder1.tvTitle.getContext(), Arrays.asList(titles));
                holder1.gvSubGroup.setAdapter(adapter);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getSpecProducts() == null) {
            return HAVE_NO_SUB;
        } else {
            return HAVE_SUB;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        public GridView gvSubGroup;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_main_category);
            gvSubGroup = (GridView) itemView.findViewById(R.id.gv_container);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onClick(getLayoutPosition());
            }
        }
    }
}
