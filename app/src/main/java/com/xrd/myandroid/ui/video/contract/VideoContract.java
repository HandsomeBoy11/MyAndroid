package com.xrd.myandroid.ui.video.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.video.bean.VideoChannelTable;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/27.
 */

public interface VideoContract {
    interface Model extends BaseModel{
        Observable<List<VideoChannelTable>> getVideoTabList();
    }
    interface View extends BaseView{
        void getTabList(List<VideoChannelTable> list);
    }
    abstract static class Presenter extends BasePresenter<View,Model>{
        public abstract void getVideoTabList();
    }
}
