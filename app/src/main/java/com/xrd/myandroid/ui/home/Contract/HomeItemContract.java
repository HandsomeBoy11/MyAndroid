package com.xrd.myandroid.ui.home.Contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.bean.NewsSummary;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/20.
 */

public interface HomeItemContract {

     interface Model extends BaseModel {
        //请求获取新闻
        Observable<List<NewsSummary>> getNewsList(String newsId,String newsType,int page);

    }

     interface View extends BaseView {
        //返回获取的新闻
        void returnNewsListData(List<NewsSummary> newsSummaries);
        void netError();
    }

     abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(String type, final String id, int startPage);
    }

}
