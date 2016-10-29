package com.soubu.goldensteward.widget.flowlayout;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.TagInFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakers on 16/10/28.
 */

public class FlowLayoutController {
    FlowLayout mFlLayout;
    OnEventCallBackListener mOnEventCallBackListener;
    OnClickAddItemListener mOnClickAddItemListener;
    ArrayList<String> mListSelected;
    ArrayList<String> mListAdded;


    public FlowLayoutController(FlowLayout flowLayout) {
        mFlLayout = flowLayout;
        mListSelected = new ArrayList<>();
        mListAdded = new ArrayList<>();
    }


    public void setOnEventCallBack(OnEventCallBackListener eventCallBack) {
        mOnEventCallBackListener = eventCallBack;
    }

    public void setOnClickAddItemListener(OnClickAddItemListener listener) {
        mOnClickAddItemListener = listener;
    }


    public ArrayList<String> getAllSelected() {
        return mListSelected;
    }

    public ArrayList<String> getAllAdded() {
        return mListAdded;
    }


    public void addPhoneItem(final String phone, boolean canDel) {
        final View phoneItem = LayoutInflater.from(mFlLayout.getContext()).inflate(R.layout.item_flowlayout, null);
        ((TextView) phoneItem.findViewById(R.id.tv_phone)).setText(phone);
        View vDelete = phoneItem.findViewById(R.id.iv_delete);
        if (canDel) {
            vDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlLayout.removeView(phoneItem);
                    mListAdded.remove(phone);
                    if (mOnEventCallBackListener != null) {
                        mOnEventCallBackListener.onDelete(phone);
                    }
                }
            });
        } else {
            vDelete.setVisibility(View.GONE);
        }
        mFlLayout.addView(phoneItem);
        mListAdded.add(phone);
        if (mOnEventCallBackListener != null) {
            mOnEventCallBackListener.onAdd(phone);
        }
    }


    public void addTagItem(final TagInFlowLayout tag) {
        final View tagItem = LayoutInflater.from(mFlLayout.getContext()).inflate(R.layout.item_flowlayout, mFlLayout, false);
        final TextView tvContent = (TextView) tagItem.findViewById(R.id.tv_phone);
        tvContent.setText(tag.getContent());
        View vDelete = tagItem.findViewById(R.id.iv_delete);
        if (tag.isSelected()) {
            tvContent.setBackgroundResource(R.drawable.bg_orange_corners);
            tvContent.setTextColor(mFlLayout.getResources().getColor(android.R.color.white));
        } else {
            tvContent.setBackgroundResource(R.drawable.bg_grey_corners);
            tvContent.setTextColor(mFlLayout.getResources().getColor(R.color.subtitle_grey));
        }
        tagItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag.isSelected()){
                    tvContent.setBackgroundResource(R.drawable.bg_grey_corners);
                    tvContent.setTextColor(mFlLayout.getResources().getColor(R.color.subtitle_grey));
                    if(mOnEventCallBackListener != null){
                        mOnEventCallBackListener.onUnSelected(tag);
                    }
                    tag.setSelected(false);
                } else {
                    tvContent.setBackgroundResource(R.drawable.bg_orange_corners);
                    tvContent.setTextColor(mFlLayout.getResources().getColor(android.R.color.white));
                    if(mOnEventCallBackListener != null){
                        mOnEventCallBackListener.onSelected(tag);
                    }
                    tag.setSelected(true);
                }
            }
        });
        if (tag.isCanDel()) {
            vDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlLayout.removeView(tagItem);
                }
            });
        } else {
            vDelete.setVisibility(View.GONE);
        }
        mFlLayout.addView(tagItem);

    }


    public void addTags(List<TagInFlowLayout> list) {
        if (list == null) {
            return;
        } else {
            for (TagInFlowLayout tag : list) {
                addTagItem(tag);
            }
            addAddItem();
        }
    }

    public void addAddItem() {
        AutoCompleteTextView addItem = (AutoCompleteTextView) LayoutInflater.from(mFlLayout.getContext()).inflate(R.layout.item_add_flowlayout, mFlLayout, false);
        addItem.setInputType(InputType.TYPE_NULL);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickAddItemListener != null) {
                    mOnClickAddItemListener.onClickAdd();
                }
            }
        });
        mFlLayout.addView(addItem);
    }


    public interface OnEventCallBackListener<T> {

        void onAdd(T content);

        void onDelete(T content);

        void onSelected(T content);

        void onUnSelected(T content);

    }


    public interface OnClickAddItemListener {
        void onClickAdd();
    }

}
