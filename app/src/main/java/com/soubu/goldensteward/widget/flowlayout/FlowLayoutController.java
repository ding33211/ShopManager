package com.soubu.goldensteward.widget.flowlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.soubu.goldensteward.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakers on 16/10/28.
 */

public class FlowLayoutController {
    FlowLayout mFlLayout;
    OnEventCallBackListener mOnEventCallBackListener;
    ArrayList<String> mListSelected;
    ArrayList<String> mListAdded;


    public FlowLayoutController(FlowLayout flowLayout){
        mFlLayout = flowLayout;
        mListSelected = new ArrayList<>();
        mListAdded = new ArrayList<>();
    }


    public void setOnEventCallBack(OnEventCallBackListener eventCallBack){
        mOnEventCallBackListener = eventCallBack;
    }


    public ArrayList<String> getAllSelected() {
        return mListSelected;
    }

    public ArrayList<String> getAllAdded(){
        return mListAdded;
    }


    public void addPhoneItem(final String phone, boolean canDel){
        final View phoneItem = LayoutInflater.from(mFlLayout.getContext()).inflate(R.layout.item_flowlayout_phone, null);
        ((TextView) phoneItem.findViewById(R.id.tv_phone)).setText(phone);
        View vDelete = phoneItem.findViewById(R.id.iv_delete);
        if(canDel){
            vDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlLayout.removeView(phoneItem);
                    mListAdded.remove(phone);
                    mOnEventCallBackListener.onDelete(phone);
                }
            });
        } else {
            vDelete.setVisibility(View.GONE);
        }
        mFlLayout.addView(phoneItem);
        mListAdded.add(phone);
        if(mOnEventCallBackListener != null){
            mOnEventCallBackListener.onAdd(phone);
        }
    }


    public interface OnEventCallBackListener{

        void onAdd(String content);

        void onDelete(String content);

        void onSelected(String content);

        void onUnSelected(String content);

    }

}
