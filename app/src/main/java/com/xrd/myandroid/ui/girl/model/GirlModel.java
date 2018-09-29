package com.xrd.myandroid.ui.girl.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.xrd.myandroid.api.Api;
import com.xrd.myandroid.api.HostType;
import com.xrd.myandroid.ui.girl.bean.GirlData;
import com.xrd.myandroid.ui.girl.bean.PhotoGirl;
import com.xrd.myandroid.ui.girl.contract.GirlContract;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by user on 2018/9/21.
 */

public class GirlModel implements GirlContract.Model {
    @Override
    public Observable<List<PhotoGirl>> getPhotosListData(int size, int page) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(Api.getCacheControl(), size, page)
                .map(new Func1<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                }).compose(RxSchedulers.<List<PhotoGirl>>io_main());
    }
}
