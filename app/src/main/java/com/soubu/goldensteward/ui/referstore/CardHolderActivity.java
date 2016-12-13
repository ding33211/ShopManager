package com.soubu.goldensteward.ui.referstore;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.CardHolderServerParams;
import com.soubu.goldensteward.support.mvp.presenter.ActivityPresenter;
import com.soubu.goldensteward.support.utils.DrawableUtils;

/**
 * Created by dingsigang on 16-12-12.
 */

public class CardHolderActivity extends ActivityPresenter<CardHolderActivityDelegate> {
    @Override
    protected Class<CardHolderActivityDelegate> getDelegateClass() {
        return CardHolderActivityDelegate.class;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        viewDelegate.setTitle(R.string.card_holder);
    }

    @Override
    protected void initData() {
        super.initData();
        SingleAdapter adapter = new SingleAdapter<CardHolderServerParams>(this, R.layout.item_card_holder) {
            @Override
            protected void bindData(BaseViewHolder holder, CardHolderServerParams item, int position) {
                TextView avatar = holder.getView(R.id.avatar);
                TextView name = holder.getView(R.id.name);
                TextView companyName = holder.getView(R.id.company_name);
                TextView status = holder.getView(R.id.status);
                LinearLayout tags = holder.getView(R.id.tags);
                name.setText(item.getName());
                avatar.setText(item.getName().substring(0, 2));
                avatar.setBackgroundResource(DrawableUtils.getAvatarColor(item.getName()));
                companyName.setText(item.getCompany());
                status.setVisibility(View.INVISIBLE);
                switch (item.getContact_status()) {
                    case 0:
                        status.setText("未邀请");
                        status.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        status.setText("已邀请");
                        status.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        status.setText("已申请");
                        status.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        status.setText("已添加");
                        status.setVisibility(View.VISIBLE);
                        break;
                }
                tags.removeAllViews();
                Context context = tags.getContext();
                for (int i = 0; i < item.getTag().size(); i++) {
                    TextView text = (TextView) LayoutInflater.from(context).inflate(R.layout.tag_text, null);
                    switch (item.getTag().get(i).getType()) {
                        case 1:
                            text.setTextColor(context.getResources().getColor(R.color.tag_1));
                            text.setBackgroundResource(R.drawable.tag_1);
                            break;
                        case 2:
                            text.setTextColor(context.getResources().getColor(R.color.tag_2));
                            text.setBackgroundResource(R.drawable.tag_2);
                            break;
                        case 3:
                            text.setTextColor(context.getResources().getColor(R.color.tag_3));
                            text.setBackgroundResource(R.drawable.tag_3);
                            break;
                    }

                    text.setText(item.getTag().get(i).getName());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()), 0);
                    text.setLayoutParams(layoutParams);
                    tags.addView(text);
                }
                View vBottomLine = holder.getView(R.id.v_bottom_line);
                if (position == getItemCount() - 1) {
                    vBottomLine.setVisibility(View.GONE);
                } else {
                    vBottomLine.setVisibility(View.VISIBLE);
                }
            }
        };
        viewDelegate.setCardHolderAdapter(adapter);
        viewDelegate.setData(null);
    }
}
