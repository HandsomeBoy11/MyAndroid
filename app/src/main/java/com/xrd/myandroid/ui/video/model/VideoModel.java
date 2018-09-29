package com.xrd.myandroid.ui.video.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.xrd.myandroid.ui.video.DataManger.VideosChannelTableManager;
import com.xrd.myandroid.ui.video.bean.VideoChannelTable;
import com.xrd.myandroid.ui.video.contract.VideoContract;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by user on 2018/9/27.
 */

public class VideoModel implements VideoContract.Model{
    @Override
    public Observable<List<VideoChannelTable>> getVideoTabList() {

        return Observable.create(new Observable.OnSubscribe<List<VideoChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<VideoChannelTable>> subscriber) {
                List<VideoChannelTable> videoChannelTables = VideosChannelTableManager.loadVideosChannelsMine();
                subscriber.onNext(videoChannelTables);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<VideoChannelTable>>io_main());
    }
}
