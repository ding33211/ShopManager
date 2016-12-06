package com.soubu.goldensteward.ui.report;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.base.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.support.bean.server.VisitFriendsServerParams;
import com.soubu.goldensteward.support.utils.ConvertUtil;
import com.soubu.goldensteward.support.utils.GlideUtils;

import java.util.Date;

/**
 * Created by lakers on 16/10/25.
 */

public class StoreVisitorContactFriendsRvAdapter extends BaseRecyclerViewAdapter<VisitFriendsServerParams> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_FOOTER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_footer_in_report, parent, false);
            return new FooterViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_visitor_contact_friends_recyclerview, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder holder1 = (ItemViewHolder)holder;
            VisitFriendsServerParams params = mList.get(position);
            GlideUtils.loadRoundedImage(holder1.ivAvatar.getContext(), holder1.ivAvatar, params.getPortrait(), R.drawable.common_header, R.drawable.common_header);
            holder1.tvName.setText(params.getName());
            holder1.tvTime.setText(ConvertUtil.dateToYYYY_MM_DD(new Date(Long.valueOf(params.getAdd_time()) * 1000)));
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView ivAvatar;
        TextView tvName;
        TextView tvTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    @Override
    public boolean isShowFooter() {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (isShowFooter()) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_ONLY;
    }
}
