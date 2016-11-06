package com.soubu.goldensteward.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.module.RegisterRvItem;
import com.soubu.goldensteward.utils.ShowWidgetUtil;
import com.soubu.goldensteward.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakers on 16/10/27.
 */

public class RegisterSupplierRvAdapter extends BaseRecyclerViewAdapter<RegisterRvItem> {
    public static final int TYPE_ITEM_CAN_FILL = 0x00;  // 可填
    public static final int TYPE_ITEM_MUST_FILL = 0x11;  // 必填
    public static final int TYPE_ITEM_MUST_CHOOSE = 0x12;  // 必选
    public static final int TYPE_ITEM_CAN_CHOOSE = 0x03;  // 可选
    public static final int TYPE_ITEM_MULTILINE = 0x04;  // 必填
    public static final int TYPE_ITEM_LOCATE = 0x13;

    private String mArea;
    private String mAddress;

    private Activity mActivity;
    //需要对multiLine所输入的内容进行缓存
    private String mMultiLine;
    //必填项
    private List<RegisterRvItem> mMustList;


    public RegisterSupplierRvAdapter(Activity activity) {
        super();
        mActivity = activity;
        mMustList = new ArrayList<>();
//        mLostFocusListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_supplier_recyclerview, parent, false);
        View tvTitle = v.findViewById(R.id.tv_title);
        View etTitle = v.findViewById(R.id.et_title);
        View vRight = v.findViewById(R.id.ll_right);
        View vMust = v.findViewById(R.id.tv_must);
        View etMultiLine = v.findViewById(R.id.et_multiline_content);
        ImageView ivChoose = (ImageView) v.findViewById(R.id.iv_choose);
        View vChoose = v.findViewById(R.id.tv_content);
        if (viewType > 0x10) {
            vMust.setVisibility(View.VISIBLE);
        } else {
            vMust.setVisibility(View.GONE);
        }
        switch (viewType) {
            case TYPE_ITEM_CAN_FILL:
            case TYPE_ITEM_MUST_FILL:
                tvTitle.setVisibility(View.GONE);
                etTitle.setVisibility(View.VISIBLE);
                vRight.setVisibility(View.GONE);
                break;
            case TYPE_ITEM_LOCATE:
                ivChoose.setImageResource(R.drawable.login_location);
                ivChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mClickLocationViewListener != null){
                            mClickLocationViewListener.onClickLocationView();
                        }
                    }
                });
                vChoose.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                etTitle.setVisibility(View.VISIBLE);
                ((EditText) etTitle).setCursorVisible(false);
                vRight.setVisibility(View.VISIBLE);
                break;
            case TYPE_ITEM_CAN_CHOOSE:
            case TYPE_ITEM_MUST_CHOOSE:
                tvTitle.setVisibility(View.VISIBLE);
                etTitle.setVisibility(View.GONE);
                vRight.setVisibility(View.VISIBLE);
                break;
            case TYPE_ITEM_MULTILINE:
                tvTitle.setVisibility(View.VISIBLE);
                etTitle.setVisibility(View.GONE);
                vRight.setVisibility(View.GONE);
                etMultiLine.setVisibility(View.VISIBLE);
                break;
        }
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder holder1 = (ItemViewHolder) holder;
            int titleRes = mList.get(position).getTitleRes();
            int type = getItemViewType(position);
            String content = mList.get(position).getContent();
            if (type > 10) {
                if (!mMustList.contains(mList.get(position))) {
                    mMustList.add(mList.get(position));
                }
            }
            if (type == TYPE_ITEM_LOCATE) {
                if (!TextUtils.isEmpty(mArea) && TextUtils.isEmpty(content)) {
                    mList.get(position).setContent(mArea);
                    content = mArea;
                }
            }
            if (titleRes == R.string.detail_address) {
                if (!TextUtils.isEmpty(mAddress) && TextUtils.isEmpty(content)) {
                    mList.get(position).setContent(mAddress);
                    content = mAddress;
                }
            }
            holder1.tvTitle.setText(titleRes);

            if (type != TYPE_ITEM_MULTILINE) {
                if (TextUtils.isEmpty(content)) {
                    holder1.etTitle.setHint(titleRes);
                    holder1.etTitle.setText("");
                    holder1.tvContent.setText(R.string.please_choose);
                    holder1.tvContent.setTextColor(mActivity.getResources().getColor(R.color.line_color));
                } else {
                    holder1.etTitle.setText(content);
                    if (type == TYPE_ITEM_CAN_CHOOSE || type == TYPE_ITEM_MUST_CHOOSE) {
                        if (titleRes == R.string.main_products) {
                            holder1.tvContent.setText(content);
                        } else {
                            holder1.tvContent.setText(mActivity.getResources().getTextArray(mList.get(position).getArrayRes())[Integer.valueOf(content)]);
                        }
                    } else {
                        holder1.tvContent.setText(content);
                    }
                    holder1.tvContent.setTextColor(mActivity.getResources().getColor(R.color.title_black));
                }
            } else {
                if (!TextUtils.isEmpty(mMultiLine)) {
                    holder1.etMultiLineContent.setText(mMultiLine);
                }
                holder1.etMultiLineContent.setHint(content);
            }

            holder1.etTitle.setInputType(mList.get(holder.getLayoutPosition()).getEditType() | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            holder1.etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        onEditTextLostFocus(v, position);
                    }
                }
            });
            holder1.etTitle.setTag(mContentObserver);
            holder1.etMultiLineContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View editText, boolean hasFocus) {
                    if (!hasFocus) {
                        String temp = ((EditText) editText).getText().toString();
                        mMultiLine = temp;
                        WindowUtil.hideSoftInput(mActivity);
                    }
                }
            });
            holder1.etMultiLineContent.setTag(mContentObserver);
            if (holder.getLayoutPosition() == getItemCount() - 1) {
                holder1.vBottomLine.setVisibility(View.INVISIBLE);
            } else {
                holder1.vBottomLine.setVisibility(View.VISIBLE);
            }
        }
    }

    private void onEditTextLostFocus(View editText, int pos) {
        if (editText != null && editText instanceof EditText) {
            String temp = ((EditText) editText).getText().toString();
            if (!TextUtils.isEmpty(temp)) {
                OnContentObserver listener = (OnContentObserver) editText.getTag();
                if (listener != null) {
                    listener.onContentChange(pos, temp);
                }
//                mList.get(pos).setContent(temp);
            }
            WindowUtil.hideSoftInput(mActivity);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();

    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        EditText etTitle;
        TextView tvContent;
        EditText etMultiLineContent;
        View vBottomLine;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            etTitle = (EditText) itemView.findViewById(R.id.et_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            etMultiLineContent = (EditText) itemView.findViewById(R.id.et_multiline_content);
            vBottomLine = itemView.findViewById(R.id.v_bottom_line);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int viewType = getItemViewType();
            switch (viewType) {
                case TYPE_ITEM_CAN_FILL:
                case TYPE_ITEM_MUST_FILL:
                    WindowUtil.showSoftInput(v.getContext(), etTitle);
                    etTitle.setSelection(etTitle.getText().length());
                    break;
                case TYPE_ITEM_CAN_CHOOSE:
                case TYPE_ITEM_MUST_CHOOSE:
                    if (!doChooseSpec(mList.get(getLayoutPosition()).getTitleRes())) {
                        final RegisterRvItem item = mList.get(getLayoutPosition());
                        ShowWidgetUtil.showMultiItemDialog(mActivity, item.getTitleRes(), item.getArrayRes(), false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mContentObserver != null) {
                                    mContentObserver.onContentChange(getLayoutPosition(), which + "");
                                }
                                mList.get(getLayoutPosition()).setContent(which + "");
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                    }
                    break;
                case TYPE_ITEM_LOCATE:
                    v.requestFocus();
                    if (mChooseAreaOrProductListener != null) {
                        mChooseAreaOrProductListener.onClick(true);
                    }
                    break;
                case TYPE_ITEM_MULTILINE:
                    WindowUtil.showSoftInput(v.getContext(), etMultiLineContent);
                    etMultiLineContent.setSelection(etMultiLineContent.getText().length());
                    break;

            }

        }
    }

    //一些特别的选择方法
    private boolean doChooseSpec(int titleRes) {
        if (titleRes != R.string.main_products) {
            return false;
        } else {
            if (mChooseAreaOrProductListener != null) {
                mChooseAreaOrProductListener.onClick(false);
            }
//            Intent intent = new Intent(mActivity, ChooseMainProductsActivity.class);
//            mActivity.startActivity(intent);
            return true;
        }
    }

    OnChooseAreaOrProductClickListener mChooseAreaOrProductListener;

    public interface OnChooseAreaOrProductClickListener {
        void onClick(boolean chooseArea);
    }

    public void setOnChooseAreaOrProductClickListener(OnChooseAreaOrProductClickListener listener) {
        mChooseAreaOrProductListener = listener;
    }

    OnContentObserver mContentObserver;

    public interface OnContentObserver {
        void onContentChange(int pos, String content);
    }

    public void setOnContentObserver(OnContentObserver listener) {
        mContentObserver = listener;
    }

    public String getMultiLineContent() {
        return mMultiLine;
    }


    OnClickLocationViewListener mClickLocationViewListener;

    public interface OnClickLocationViewListener{
        void onClickLocationView();
    }

    public void setOnClickLocationViewListener(OnClickLocationViewListener listener){
        mClickLocationViewListener = listener;
    }


}
