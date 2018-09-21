package com.xrd.myandroid.ui.home.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.HomeContract;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by user on 2018/9/20.
 */

public class HomePresenter extends HomeContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.NEWS_CHANNEL_CHANGED, new Action1<List<NewsChannelTable>>() {
            @Override
            public void call(List<NewsChannelTable> newsChannelTables) {
                LogUtils.loge(newsChannelTables.size()+"rxbus后");
                mView.returnMineNewsChannels(newsChannelTables);
            }
        });
    }

    @Override
    public void lodeMineChannelsRequest() {
        mRxManage.add(mModel.lodeMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                LogUtils.loge(newsChannelTables.size()+"缓存后");
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));

    }
}
