package com.xrd.myandroid.ui.video.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.video.bean.VideoData;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/27.
 */

public interface VideoSubContract {
    interface Model extends BaseModel{
        Observable<List<VideoData>> getVideoList(String id, int startPage);
    }
    interface View extends BaseView{
        void returnVideoList(List<VideoData> list);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getVideoList(String id,int startPage,boolean showDialog);
    }
}
