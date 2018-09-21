package com.xrd.myandroid.ui.home.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.ACache;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.xrd.myandroid.app.AppApplication;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.db.NewsChannelTableManager;
import com.xrd.myandroid.ui.home.Contract.HomeChannelContract;
import com.xrd.myandroid.ui.home.Contract.HomeContract;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by user on 2018/9/20.
 */

public class HomeChannelModel implements HomeChannelContract.Model {
    @Override
    public Observable<List<NewsChannelTable>> lodeMineNewsChannels() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MINE);
                if(newsChannelTableList==null){
                    newsChannelTableList= (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                    LogUtils.loge(newsChannelTableList.size()+"换存前");
                    ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,newsChannelTableList);
                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<List<NewsChannelTable>> lodeMoreNewsChannels() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MORE);
                if(newsChannelTableList==null){
                    newsChannelTableList= (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsMine();
                    LogUtils.loge(newsChannelTableList.size()+"换存前");
                    ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE,newsChannelTableList);
                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<List<NewsChannelTable>> itemAndOrRemove(final ArrayList<NewsChannelTable> mineList, final ArrayList<NewsChannelTable> moreList) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,mineList);
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE,moreList);
                subscriber.onNext(mineList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<List<NewsChannelTable>> itemSwpe(final ArrayList<NewsChannelTable> mineList) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                subscriber.onNext(mineList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }
}
