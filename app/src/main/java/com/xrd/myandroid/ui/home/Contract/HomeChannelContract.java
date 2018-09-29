package com.xrd.myandroid.ui.home.Contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/20.
 */

public interface HomeChannelContract {

     interface Model extends BaseModel {
        Observable<List<NewsChannelTable>> lodeMineNewsChannels();
        Observable<List<NewsChannelTable>> lodeMoreNewsChannels();
        Observable<List<NewsChannelTable>> itemAndOrRemove(ArrayList<NewsChannelTable> mineList,ArrayList<NewsChannelTable> moreList);
        Observable<List<NewsChannelTable>> itemSwpe(ArrayList<NewsChannelTable> mineList);
    }

     interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
        void returnMoreNewsChannels(List<NewsChannelTable> newsChannelsMine);
    }

     abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeMineChannelsRequest();
        public abstract void lodeMoreChannelsRequest();
        public abstract void onItemAddOrRemove(ArrayList<NewsChannelTable> mineList, ArrayList<NewsChannelTable> moreList);
        public abstract void onItemSwpe(ArrayList<NewsChannelTable> mineList);
    }

}
