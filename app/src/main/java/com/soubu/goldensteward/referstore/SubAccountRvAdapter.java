package com.soubu.goldensteward.referstore;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.base.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.module.server.SubAccountServerParams;
import com.soubu.goldensteward.utils.GlideUtils;
import com.soubu.goldensteward.utils.PinyinComparator;
import com.soubu.goldensteward.widget.RecyclerViewFastScroller;

import java.util.Collections;
import java.util.List;

/**
 * Created by lakers on 16/10/31.
 */

public class SubAccountRvAdapter extends BaseRecyclerViewAdapter<SubAccountServerParams> implements RecyclerViewFastScroller.BubbleTextGetter {
    private final int TYPE_TOP = 0x00;
    private final int TYPE_MID = 0x01;
    private final int TYPE_BOT = 0x02;
    private final int TYPE_ONLY = 0x03;


//    List<SubAccountModule> mCopyList;  //用于筛选

    public SubAccountRvAdapter() {
//        mList = new ArrayList<>();
////        DBHelper helper = DBHelper.getInstance(context);
//        SubAccountModule SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("张老四");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("极大");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("管控");
//        SubAccountModule.setType(com.soubu.goldensteward.module.SubAccountModule.TYPE_REVIEW);
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("发哦");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("给你哦啊");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("我就");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("迫切渴望");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("via");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("风貌");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("围绕你");
//        mList.add(SubAccountModule);
//        SubAccountModule.setType(com.soubu.goldensteward.module.SubAccountModule.TYPE_REVIEW);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("蘑菇");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("我没钱");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("蘑菇");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("的才能杀");
//        SubAccountModule.setType(com.soubu.goldensteward.module.SubAccountModule.TYPE_REVIEW);
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("偶尔玩");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("都为");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("傲娇");
//        mList.add(SubAccountModule);
//        SubAccountModule = new SubAccountModule();
//        SubAccountModule.setName("二期");
//        SubAccountModule.setType(com.soubu.goldensteward.module.SubAccountModule.TYPE_REVIEW);
//        mList.add(SubAccountModule);
//        mCopyList = new ArrayList<>();
//        mCopyList.addAll(mList);
//        mParams.addAll(helper.getStaffDao().loadAll());
        Collections.sort(mList, new PinyinComparator());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_account_recyclerview, parent, false);
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
            SubAccountServerParams param = mList.get(position);
            holder1.tvName.setText(param.getName());
            holder1.tvLetter.setText(param.getLetter());
//            String type = mList.get(position).getType();
//            if (TextUtils.isEmpty(type)) {
//                holder1.tvState.setVisibility(View.VISIBLE);
//                holder1.tvName.setTextColor(holder1.tvLetter.getResources().getColor(R.color.line_color));
//            } else {
            holder1.tvName.setTextColor(holder1.tvLetter.getResources().getColor(R.color.title_black));
            GlideUtils.loadRoundedImage(holder1.ivAvatar.getContext(), holder1.ivAvatar, param.getPortrait(), R.drawable.common_header, R.drawable.common_header);
//            holder1.tvState.setVisibility(View.GONE);
//            }
        }
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
    public String getTextToShowInBubble(int pos) {
        return mList.get(pos).getLetter();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                //初始化过滤结果对象
//                FilterResults results = new FilterResults();
//                //假如搜索为空的时候，将复制的数据添加到原始数据，用于继续过滤操作
//                if (results.values == null) {
//                    mList.clear();
//                    mList.addAll(mCopyList);
//                }
//                //关键字为空的时候，搜索结果为复制的结果
//                if (constraint == null || constraint.length() == 0) {
//                    results.values = mCopyList;
//                    results.count = mCopyList.size();
//                } else {
//                    String prefixString = constraint.toString();
//                    final int count = mList.size();
//                    //用于存放暂时的过滤结果
//                    final ArrayList<SubAccountModule> newValues = new ArrayList<SubAccountModule>();
//                    for (int i = 0; i < count; i++) {
//                        final SubAccountModule value = mList.get(i);
//                        String username = value.getName();
//                        // First match against the whole ,non-splitted value，假如含有关键字的时候，添加
//                        if (username.contains(prefixString)) {
//                            newValues.add(value);
//                        } else {
//                            //过来空字符开头
//                            final String[] words = username.split(" ");
//                            final int wordCount = words.length;
//
//                            // Start at index 0, in case valueText starts with space(s)
//                            for (int k = 0; k < wordCount; k++) {
//                                if (words[k].contains(prefixString)) {
//                                    newValues.add(value);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                    results.values = newValues;
//                    results.count = newValues.size();
//                }
//                return results;//过滤结果
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                mList.clear();//清除原始数据
//                mList.addAll((List<SubAccountModule>) results.values);//将过滤结果添加到这个对象
//                if (results.count > 0) {
//                    notifyDataSetChanged();//有关键字的时候刷新数据
//                } else {
//                    //关键字不为零但是过滤结果为空刷新数据
//                    if (constraint.length() != 0) {
//                        notifyDataSetChanged();
//                        return;
//                    }
//                    //加载复制的数据，即为最初的数据
//                    setData(mCopyList);
//                }
//                Collections.sort(mList, new PinyinComparator());
//            }
//        };
//    }


    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        //        TextView tvState;
        TextView tvLetter;
        ImageView ivAvatar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
//            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvLetter = (TextView) itemView.findViewById(R.id.tv_letter);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClick(getLayoutPosition());
            }
        }
    }

    @Override
    public void setData(List<SubAccountServerParams> list) {
        super.setData(list);
    }


}
