package com.xrd.myandroid.ui.home.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonwidget.LoadingDialog;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.HomeContract;
import com.xrd.myandroid.ui.home.Contract.HomeItemContract;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.bean.NewsSummary;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by user on 2018/9/20.
 */

public class HomeItemPresenter extends HomeItemContract.Presenter {
    @Override
    public void getNewsListDataRequest(String type, String id, int startPage) {

        mRxManage.add(mModel.getNewsList(id, type, startPage).subscribe(new RxSubscriber<List<NewsSummary>>(mContext, false) {
            @Override
            protected void _onNext(List<NewsSummary> newsSummaries) {
                mView.returnNewsListData(newsSummaries);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                LogUtils.loge("发生错误" + message);
                mView.stopLoading();
                mView.netError();
            }
        }));
    }
}
