package com.xrd.myandroid.ui.girl.contract;

import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.xrd.myandroid.ui.girl.bean.PhotoGirl;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;

import java.util.List;

import rx.Observable;

/**
 * Created by user on 2018/9/20.
 */

public interface GirlContract {

    interface Model extends BaseModel {
        //请求获取图片
        Observable <List<PhotoGirl>> getPhotosListData(int size, int page);
    }

    interface View extends BaseView {
        //返回获取的图片
        void returnPhotosListData(List<PhotoGirl> photoGirls);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取图片请求
        public abstract void getPhotosListDataRequest(int size, int page,boolean showDialog);
    }

}
