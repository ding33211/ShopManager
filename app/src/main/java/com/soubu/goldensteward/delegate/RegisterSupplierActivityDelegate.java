package com.soubu.goldensteward.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.adapter.RegisterSupplierRvAdapter;
import com.soubu.goldensteward.base.mvp.view.AppDelegate;
import com.soubu.goldensteward.module.RegisterRvItem;
import com.soubu.goldensteward.module.server.UserServerParams;
import com.soubu.goldensteward.utils.RegularUtil;
import com.soubu.goldensteward.utils.ShowWidgetUtil;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static com.soubu.goldensteward.adapter.RegisterSupplierRvAdapter.TYPE_ITEM_MUST_CHOOSE;

/**
 * Created by lakers on 16/10/27.
 */

public class RegisterSupplierActivityDelegate extends AppDelegate {
    RecyclerView mRvContent;
    List<RegisterRvItem> mList1;
    List<RegisterRvItem> mList2;
    RegisterSupplierRvAdapter mAdapter;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_register_supplier;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mRvContent = get(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RegisterSupplierRvAdapter(getActivity());
        mRvContent.setAdapter(mAdapter);
    }

    public void setStep1Data(List<RegisterRvItem> list) {
        mList1 = list;
        mAdapter.setData(list);
        mAdapter.setOnContentObserver(new RegisterSupplierRvAdapter.OnContentObserver() {
            @Override
            public void onContentChange(int pos, String content) {
                mList1.get(pos).setContent(content);
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public void setStep2Data(List<RegisterRvItem> list) {
        mList2 = list;
    }

    public void clickNextStep() {
        ((TextView) get(R.id.tv_label)).setText(R.string.company_info);
        ((Button) get(R.id.btn_next_step)).setText(R.string.finish_register);
        mAdapter.setData(mList2);
        mAdapter.setOnContentObserver(new RegisterSupplierRvAdapter.OnContentObserver() {
            @Override
            public void onContentChange(int pos, String content) {
                mList2.get(pos).setContent(content);
                Log.e("zzzzzzz", "content   :   " + content);
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public void onClickBackOnSecondStep() {
        ((TextView) get(R.id.tv_label)).setText(R.string.account_info);
        ((Button) get(R.id.btn_next_step)).setText(R.string.next_step);
        mAdapter.setData(mList1);
        mAdapter.setOnContentObserver(new RegisterSupplierRvAdapter.OnContentObserver() {
            @Override
            public void onContentChange(int pos, String content) {
                mList1.get(pos).setContent(content);
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public boolean ifStep2Init() {
        return mList2 != null;
    }

    public void refreshArea(String area) {
        mList2.get(4).setContent(area);
        mAdapter.notifyItemChanged(4);
    }

    public void refreshAddress(String address) {
        mList2.get(5).setContent(address);
        mAdapter.notifyItemChanged(5);
    }

    public void refreshMainProducts(String products) {
        mList2.get(2).setContent(products);
        mAdapter.notifyItemChanged(2);
    }

    public void setOnChooseAreaListener(RegisterSupplierRvAdapter.OnChooseAreaOrProductClickListener listener) {
        mAdapter.setOnChooseAreaOrProductClickListener(listener);
    }


    @Override
    public boolean ifNeedEventBus() {
        return true;
    }


    public boolean checkComplete(UserServerParams params, int stepWhich) {
        for (RegisterRvItem param : stepWhich == 1 ? mList1 : mList2) {
            String content = param.getContent();
            int titleRes = param.getTitleRes();
            int type = param.getType();
            Log.e("xxxxxxx", this.getActivity().getString(titleRes) + "   :  " + content);
            if (type > 0x10 && TextUtils.isEmpty(content)) {
                if (type == TYPE_ITEM_MUST_CHOOSE) {
                    ShowWidgetUtil.showShort(getActivity().getString(R.string.please_choose_s, this.getActivity().getString(titleRes)));
                } else {
                    ShowWidgetUtil.showShort(getActivity().getString(R.string.something_can_not_empty, this.getActivity().getString(titleRes)));
                }
                return false;
            }
            switch (titleRes) {
                case R.string.store_name:
                    params.setName(content);
                    break;
                case R.string.contact_name:
                    params.setContact_name(content);
                    break;
                case R.string.position_in:
                    params.setJob(Integer.valueOf(content) + 1 + "");
                    break;
                case R.string.email:
                    if (RegularUtil.isEmail(content)) {
                        params.setMail(content);
                    } else {
                        if (!TextUtils.isEmpty(content)) {
                            ShowWidgetUtil.showShort(R.string.wrong_email);
                            return false;
                        }
                    }
                    break;
                case R.string.phone:
                    params.setFixed_telephone(content);
                    break;
                case R.string.company_name:
                    params.setCompany(content);
                    break;
                case R.string.main_industry:
                    params.setMain_industry(Integer.valueOf(content) + 1 + "");
                    break;
                case R.string.management_model:
                    params.setOperation_mode(Integer.valueOf(content) + 1 + "");
                    break;
                case R.string.detail_address:
                    params.setAddress(content);
                    break;
                case R.string.annual_turnover:
                    params.setTurnover(Integer.valueOf(content) + 1 + "");
                    break;
                case R.string.employees_num:
                    params.setCompany_size(Integer.valueOf(content) + 1 + "");
                    break;
                case R.string.company_profile:
                    if (TextUtils.isEmpty(mAdapter.getMultiLineContent())) {
                        ShowWidgetUtil.showShort(getActivity().getString(R.string.something_can_not_empty, this.getActivity().getString(titleRes)));
                        return false;
                    }
                    params.setCompany_profile(mAdapter.getMultiLineContent());
                    break;
            }
        }
        return true;
    }


    public void setOnClickLocationViewListener(RegisterSupplierRvAdapter.OnClickLocationViewListener listener) {
        mAdapter.setOnClickLocationViewListener(listener);
    }

}
