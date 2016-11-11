package com.soubu.goldensteward.view.fragment;

import com.soubu.goldensteward.GoldenStewardApplication;
import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.BaseRecyclerViewAdapter;
import com.soubu.goldensteward.adapter.InformationRvAdapter;
import com.soubu.goldensteward.base.greendao.DBHelper;
import com.soubu.goldensteward.base.greendao.User;
import com.soubu.goldensteward.base.greendao.UserDao;
import com.soubu.goldensteward.base.mvp.presenter.FragmentPresenter;
import com.soubu.goldensteward.delegate.RecyclerViewFragmentDelegate;
import com.soubu.goldensteward.module.InformationRvItem;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.utils.RegularUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

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
        viewDelegate.setRvItemOnClickListener(new BaseRecyclerViewAdapter.OnRvItemClickListener() {
            @Override
            public void onClick(final int position) {
                if (mList.get(position).getItemType() == InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE) {
                    ShowWidgetUtil.showCustomInputDialog(getActivity(), mList.get(position).getTitleRes(), mList.get(position).getContent(), new ShowWidgetUtil.OnClickCustomInputConfirm() {
                        @Override
                        public void onConfirm(String content) {
                            saveInfo(content, position);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mParams = new UserServerParams();
        viewDelegate.setAdapter(new InformationRvAdapter());
        mUserDao = DBHelper.getInstance(getActivity()).getUserDao();
        List<User> list = mUserDao.queryBuilder().where(UserDao.Properties.Phone.eq(GoldenStewardApplication.getContext().getPhone())).list();
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
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.contact);
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent(user.getContact_name());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.position);
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        item.setContent(user.getJob());
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.mobile);
        item.setContent(user.getPhone());
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_NOT_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.email);
        item.setContent(user.getMail());
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
        mList.add(item);
        item = new InformationRvItem();
        item.setTitleRes(R.string.phone);
        item.setContent(user.getFixed_telephone());
        item.setItemType(InformationRvAdapter.TYPE_ITEM_CAN_CHOOSE);
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
