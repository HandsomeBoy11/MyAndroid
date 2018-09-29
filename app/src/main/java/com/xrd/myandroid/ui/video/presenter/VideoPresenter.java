package com.xrd.myandroid.ui.video.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.xrd.myandroid.ui.video.bean.VideoChannelTable;
import com.xrd.myandroid.ui.video.contract.VideoContract;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/27.
 */

public class VideoPresenter extends VideoContract.Presenter {
    @Override
    public void getVideoTabList() {
         mRxManage.add(mModel.getVideoTabList().subscribe(new RxSubscriber<List<VideoChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<VideoChannelTable> videoChannelTables) {
                mView.getTabList(videoChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
