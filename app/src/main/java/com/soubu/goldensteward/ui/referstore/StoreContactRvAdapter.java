package com.soubu.goldensteward.ui.referstore;

import android.content.Context;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.adapter.SingleAdapter;
import com.soubu.goldensteward.support.bean.server.StoreContactServerParams;
import com.soubu.goldensteward.support.widget.RecyclerViewFastScroller;

/**
 * Created by dingsigang on 16-12-12.
 */

public class StoreContactRvAdapter extends SingleAdapter<StoreContactServerParams> implements RecyclerViewFastScroller.BubbleTextGetter {
    public StoreContactRvAdapter(Context context, int layoutId) {
        super(context, R.layout.item_contact);
    }

    @Override
    protected void bindData(BaseViewHolder holder, StoreContactServerParams item, int position) {
//        TextView tag = holder.getView(R.id.tag);
//        View viewLvItemLine = holder.getView(R.id.view_lv_item_line);
//        ImageView avatar = holder.getView(R.id.iv_lv_item_head);
//        TextView nickname = holder.getView(R.id.nickname);
//        ImageView level = holder.getView(R.id.level);
//        ImageView role = holder.getView(R.id.role);
//        LinearLayout clickArea = holder.getView(R.id.click_area);
//        ImageView cerStatus = holder.getView(R.id.cer_status);
//        TextView avatarText = holder.getView(R.id.avatar_text);
//        int selection = person.getFirstPinYin().charAt(0);
//        // 通过首字母的assii值来判断是否显示字母
//        int positionForSelection = getPositionForSelection(selection);
//        if (position - 1 == positionForSelection) {// 相等说明需要显示字母
//            tag.setVisibility(View.VISIBLE);
//            tag.setText(person.getFirstPinYin());
//        } else {
//            tag.setVisibility(View.GONE);
//        }
//        viewLvItemLine.setVisibility(tag.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//
//        clickArea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onClick(person);
//            }
//        });
//
//        nickname.setText(person.getDisplayName());
//
//        if (TextUtils.isEmpty(person.getPortrait())) {
//            avatar.setVisibility(View.INVISIBLE);
//            avatarText.setVisibility(View.VISIBLE);
//            avatarText.setBackgroundResource(DrawableUtils.getAvaterColor(person.getDisplayName()));
//            avatarText.setText(person.getPortraitName());
//        } else {
//            avatar.setVisibility(View.VISIBLE);
//            avatarText.setVisibility(View.INVISIBLE);
//            GlideUtils.loadRoundedImage(context, avatar, Uri.parse(UrlUtils.getUrl(person.getPortrait())), R.drawable.register_ico_head, R.drawable.register_ico_head);
//        }
//
//
//        switch (person.getRole()) {
//            case 1:
//                role.setImageResource(R.drawable.common_ico_purchase);
//                break;
//            case 2:
//                role.setImageResource(R.drawable.common_ico_ml);
//                break;
//            default:
//                role.setVisibility(View.GONE);
//                break;
//        }
//
//        level.setVisibility(View.VISIBLE);
//        switch (person.getLevel()) {
//            case 0:
//                level.setImageResource(R.drawable.supply_general);
//                break;
//            case 1:
//                level.setImageResource(R.drawable.supply_copper);
//                break;
//            case 2:
//                level.setImageResource(R.drawable.supply_silver);
//                break;
//            case 3:
//                level.setImageResource(R.drawable.supply_gold);
//                break;
//            case 4:
//                level.setImageResource(R.drawable.supply_diamond);
//                break;
//            default:
//                level.setVisibility(View.GONE);
//                break;
//        }
//        cerStatus.setVisibility(View.VISIBLE);
//        switch (person.getCertificationType()) {
//            case 1:
//                cerStatus.setImageResource(R.drawable.common_icon_authperson);
//                break;
//            case 2:
//                cerStatus.setImageResource(R.drawable.common_icon_authcompy);
//                break;
//            default:
//                cerStatus.setVisibility(View.GONE);
//                break;
//        }

    }

    @Override
    public String getTextToShowInBubble(int pos) {
        return null;
    }
}
