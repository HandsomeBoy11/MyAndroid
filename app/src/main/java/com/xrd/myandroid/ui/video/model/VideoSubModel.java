package com.xrd.myandroid.ui.video.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.xrd.myandroid.api.Api;
import com.xrd.myandroid.api.HostType;
import com.xrd.myandroid.ui.video.bean.VideoData;
import com.xrd.myandroid.ui.video.contract.VideoSubContract;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by user on 2018/9/27.
 */

public class VideoSubModel implements VideoSubContract.Model {
    @Override
    public Observable<List<VideoData>> getVideoList(final String id, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getVideoList(Api.getCacheControl(),id,startPage)
                .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {

                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> map) {
                        return Observable.from(map.get(id));
                    }
                })
                .map(new Func1<VideoData, VideoData>() {
                    @Override
                    public VideoData call(VideoData videoData) {
                        String s = TimeUtil.formatDate(videoData.getPtime());
                        videoData.setPtime(s);
                        return videoData;
                    }
                })
                .distinct()
                .toSortedList(new Func2<VideoData, VideoData, Integer>() {
                    @Override
                    public Integer call(VideoData videoData, VideoData videoData2) {
                        return videoData2.getPtime().compareTo(videoData.getPtime());
                    }
                })
                .compose(RxSchedulers.<List<VideoData>>io_main());
    }
}
