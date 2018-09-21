package com.xrd.myandroid.ui.home.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.HomeChannelContract;
import com.xrd.myandroid.ui.home.Contract.HomeContract;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/9/20.
 */

public class HomeChannelPresenter extends HomeChannelContract.Presenter {


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

    @Override
    public void lodeMoreChannelsRequest() {
        mRxManage.add(mModel.lodeMoreNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                LogUtils.loge(newsChannelTables.size()+"缓存后");
                mView.returnMoreNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onItemAddOrRemove(final ArrayList<NewsChannelTable> mineList, ArrayList<NewsChannelTable> moreList) {
        mRxManage.add(mModel.itemAndOrRemove(mineList,moreList).subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED,mineList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onItemSwpe(final ArrayList<NewsChannelTable> mineList) {
        mRxManage.add(mModel.itemSwpe(mineList).subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED,mineList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
