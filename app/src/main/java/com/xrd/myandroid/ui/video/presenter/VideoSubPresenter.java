package com.xrd.myandroid.ui.video.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.xrd.myandroid.ui.video.bean.VideoData;
import com.xrd.myandroid.ui.video.contract.VideoSubContract;

import java.util.List;

/**
 * Created by user on 2018/9/27.
 */

public class VideoSubPresenter extends VideoSubContract.Presenter {


    @Override
    public void getVideoList(String id, int startPage, final boolean showDialog) {
        mRxManage.add(mModel.getVideoList(id, startPage).subscribe(new RxSubscriber<List<VideoData>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                if (showDialog)
                    mView.showLoading("");
            }

            @Override
            protected void _onNext(List<VideoData> videoData) {
                mView.returnVideoList(videoData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }
}
