package com.xrd.myandroid.ui.home.Contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/20.
 */

public interface HomeContract {

     interface Model extends BaseModel {
        Observable<List<NewsChannelTable>> lodeMineNewsChannels();

    }

     interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
    }

     abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeMineChannelsRequest();
    }

}
