package com.soubu.goldensteward.ui.market;

import com.soubu.goldensteward.support.base.BaseApplication;
import com.soubu.goldensteward.support.bean.server.ProductInSignUpActivityServerParams;
import com.soubu.goldensteward.support.bean.server.SignUpServerParams;
import com.soubu.goldensteward.support.web.core.BaseResponse;
import com.soubu.goldensteward.support.web.core.BaseSubscriber;
import com.soubu.goldensteward.support.web.mvp.BasePresenter;

import java.util.List;

import rx.Observable;

/**
 * 作者：余天然 on 2016/12/14 下午7:54
 */
public class SignUpPresenter extends BasePresenter<SignUpView> {

    private String id;

    public SignUpPresenter(String id) {
        this.id = id;
    }

    public Observable<List<ProductInSignUpActivityServerParams>> getData(int curPage) {
        return BaseApplication.getWebModel()
                .getProductListInActivity(new ProductListRequest(curPage, id))
                .getObservable()
                .map(res -> res.getResult().getData());
    }

    public void commit(String mAccountId, int mActivityId, List<String> mCheckedProductId) {
        SignUpServerParams params = new SignUpServerParams();
        params.setUid(mAccountId);
        params.setActive_id(mActivityId + "");
        params.setProduct_list(mCheckedProductId);

        BaseApplication.getWebModel()
                .signUp(params)
                .sendTo(new BaseSubscriber<BaseResponse>(getView()) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().onCommitSuccess();
                    }
                });
    }
}
