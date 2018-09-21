package com.xrd.myandroid.ui.home.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.ACache;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.xrd.myandroid.api.Api;
import com.xrd.myandroid.api.ApiConstants;
import com.xrd.myandroid.api.HostType;
import com.xrd.myandroid.app.AppApplication;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.db.NewsChannelTableManager;
import com.xrd.myandroid.ui.home.Contract.HomeContract;
import com.xrd.myandroid.ui.home.Contract.HomeItemContract;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.bean.NewsSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by user on 2018/9/20.
 */

public class HomeItemModel implements HomeItemContract.Model {
    @Override
    public Observable<List<NewsSummary>> getNewsList(final String id, String type, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewsList(Api.getCacheControl(),type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            // 房产实际上针对地区的它的id与返回key不同
                            return Observable.from(map.get("北京"));
                        }
                        return Observable.from(map.get(id));
                    }
                })
                //转化时间
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = TimeUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()//去重
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<List<NewsSummary>>io_main());
    }

}
