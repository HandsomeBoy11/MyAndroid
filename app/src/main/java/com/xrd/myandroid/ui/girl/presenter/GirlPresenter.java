package com.xrd.myandroid.ui.girl.presenter;

import com.jaydenxiao.common.baserx.RxSubscriber;
import com.xrd.myandroid.ui.girl.bean.PhotoGirl;
import com.xrd.myandroid.ui.girl.contract.GirlContract;

import java.util.List;

/**
 * Created by user on 2018/9/21.
 */

public class GirlPresenter extends GirlContract.Presenter {
    @Override
    public void getPhotosListDataRequest(int size, int page, final boolean showDialog) {
        mRxManage.add(mModel.getPhotosListData(size,page).subscribe(new RxSubscriber<List<PhotoGirl>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                if(showDialog)
                mView.showLoading("正在加载图片");
            }

            @Override
            protected void _onNext(List<PhotoGirl> photoGirls) {
                mView.returnPhotosListData(photoGirls);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
            }
        }));
    }
}
