package com.soubu.goldensteward.ui.information;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.adapter.BaseViewHolder;
import com.soubu.goldensteward.support.bean.InformationRvItem;
import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.greendao.User;
import com.soubu.goldensteward.support.greendao.UserDao;
import com.soubu.goldensteward.support.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.support.utils.RegularUtil;
import com.soubu.goldensteward.support.utils.SPUtil;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingsigang on 16-10-19.
 */
public class PersonalInformationFragment extends FragmentPresenter<RecyclerViewFragmentDelegate> {

    private List<InformationRvItem> mList;
    private UserDao mUserDao;
    private User mUser;
    private UserServerParams mParams;
    private boolean mHaveChanged = false;

    @Override
    protected Class<RecyclerViewFragmentDelegate> getDelegateClass() {
        return RecyclerViewFragmentDelegate.class;
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mParams = new UserServerParams();
        InformationSingleRvAdapter adapter = new InformationSingleRvAdapter(getActivity()) {
            @Override
            public void onItemClick(BaseViewHolder holder, InformationRvItem item, int position) {
                if (item.getItemType() == InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE) {
                    ShowWidgetUtil.showCustomInputDialog(getActivity(), item.getTitleRes(), item.getContent(), new ShowWidgetUtil.OnClickCustomInputConfirm() {
                        @Override
                        public void onConfirm(String content) {
                            saveInfo(content, position);
                        }
                    });
                }
            }
        };
        viewDelegate.setAdapter(adapter);
        mUserDao = DBHelper.getInstance(getActivity()).getUserDao();
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        List<User> list = mUserDao.queryBuilder().where(UserDao.Properties.Phone.eq(phone)).list();
        if (list.size() > 0) {
            initRecyclerData(list.get(0));
        }
    }


    private void saveInfo(String content, int position) {
        if (!mHaveChanged) {
            mHaveChanged = true;
        }
        switch (position) {
            case 0:
                mUser.setName(content);
                mParams.setName(content);
                break;
            case 4:
                if (!RegularUtil.isEmail(content)) {
                    ShowWidgetUtil.showShort(R.string.wrong_email);
                    return;
                }
                mUser.setMail(content);
                mParams.setMail(content);
                break;
            case 5:
                mUser.setFixed_telephone(content);
                mParams.setFixed_telephone(content);
                break;

        }
        mList.get(position).setContent(content);
        viewDelegate.notifyItemChanged(position);

    }

    protected void initRecyclerData(User user) {
        super.initData();
        mUser = user;
        mList = new ArrayList<>();
        InformationRvItem item = new InformationRvItem();
        item.setTitleRes(R.string.store_name);
        item.setContent(user.getName());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.contact);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent(user.getContact_name());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.position);
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setArrayRes(R.array.job);
        item.setContent(user.getJob());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.mobile);
        item.setContent(user.getPhone());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.email);
        item.setContent(user.getMail());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.phone);
        item.setContent(user.getFixed_telephone());
        item.setItemType(InformationSingleRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        viewDelegate.setData(mList);
    }

    public UserServerParams getParams() {
        return mParams;
    }

    public boolean isChanged() {
        return mHaveChanged;
    }

    public void updateToDb() {
        mUserDao.update(mUser);
    }
}
