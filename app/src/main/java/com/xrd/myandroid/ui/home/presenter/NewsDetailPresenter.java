package com.xrd.myandroid.ui.home.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.xrd.myandroid.ui.girl.contract.GirlContract;
import com.xrd.myandroid.ui.home.Contract.NewsDetailContract;
import com.xrd.myandroid.ui.home.bean.NewsDetail;

/**
 * Created by user on 2018/9/25.
 */

public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        mView.showLoading("");
    }

    @Override
    public void lodeNewsDetailRequest(String postId) {
        mRxManage.add(mModel.lodeNewsDetailRequest(postId).subscribe(new RxSubscriber<NewsDetail>(mContext, false) {
            @Override
            protected void _onNext(NewsDetail newsDetail) {
                mView.stopLoading();
                mView.returnNewsDetail(newsDetail);
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
            }
        }));
    }
}
