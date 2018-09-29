package com.xrd.myandroid.ui.home.Contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.bean.NewsDetail;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/20.
 */

public interface NewsDetailContract {

     interface Model extends BaseModel {
        Observable<NewsDetail> lodeNewsDetailRequest(String postId);

    }

     interface View extends BaseView {
        void returnNewsDetail(NewsDetail newsDetail);
    }

     abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeNewsDetailRequest(String postId);
    }

}
