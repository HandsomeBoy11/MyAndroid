package com.xrd.myandroid.ui.home.model;

import com.jaydenxiao.common.baserx.RxSchedulers;
import com.xrd.myandroid.api.Api;
import com.xrd.myandroid.api.ApiConstants;
import com.xrd.myandroid.api.ApiService;
import com.xrd.myandroid.api.HostType;
import com.xrd.myandroid.ui.home.Contract.NewsDetailContract;
import com.xrd.myandroid.ui.home.bean.NewsDetail;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by user on 2018/9/25.
 */

public class NewsDetailModel implements NewsDetailContract.Model {


    @Override
    public Observable<NewsDetail> lodeNewsDetailRequest(final String postId) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO)
                .getNewDetail(Api.getCacheControl(),postId)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> newsDetailMap) {
                        NewsDetail newsDetail = newsDetailMap.get(postId);
                        changeNewsDetail(newsDetail);
                        return newsDetail;
                    }
                })
                .compose(RxSchedulers.<NewsDetail>io_main());
    }
    private void changeNewsDetail(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        if (isChange(imgSrcs)) {
            String newsBody = newsDetail.getBody();
            newsBody = changeNewsBody(imgSrcs, newsBody);
            newsDetail.setBody(newsBody);
        }
    }

    private boolean isChange(List<NewsDetail.ImgBean> imgSrcs) {
        return imgSrcs != null && imgSrcs.size() >= 2;
    }

    private String changeNewsBody(List<NewsDetail.ImgBean> imgSrcs, String newsBody) {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);

        }
        return newsBody;
    }
}
