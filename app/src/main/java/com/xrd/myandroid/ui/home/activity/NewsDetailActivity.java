package com.xrd.myandroid.ui.home.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baserx.RxSchedulers;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.xrd.myandroid.R;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.NewsDetailContract;
import com.xrd.myandroid.ui.home.bean.NewsDetail;
import com.xrd.myandroid.ui.home.model.NewsDetailModel;
import com.xrd.myandroid.ui.home.presenter.NewsDetailPresenter;
import com.xrd.myandroid.weight.URLImageGetter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter, NewsDetailModel> implements NewsDetailContract.View {


    @BindView(R.id.news_detail_photo_iv)
    ImageView newsDetailPhotoIv;
    @BindView(R.id.mask_view)
    View maskView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.news_detail_from_tv)
    TextView newsDetailFromTv;
    @BindView(R.id.news_detail_body_tv)
    TextView newsDetailBodyTv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String mShareLink;
    private String mNewsTitle;
    private URLImageGetter mUrlImageGetter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        SetTranslanteBar();
        Intent intent = getIntent();
        String postId = intent.getStringExtra(AppConstant.NEWS_POST_ID);
        String imgsrc = intent.getStringExtra(AppConstant.NEWS_IMG_RES);

        mPresenter.lodeNewsDetailRequest(postId);//请求新闻详情

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
    }

    public static void startAct(Context mContext, View view, String postid, String imgsrc) {
        Intent intent = new Intent(mContext,NewsDetailActivity.class);
        intent.putExtra(AppConstant.NEWS_POST_ID, postid);
        intent.putExtra(AppConstant.NEWS_IMG_RES, imgsrc);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext, view, AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            mContext.startActivity(intent, options.toBundle());
        } else {

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void returnNewsDetail(NewsDetail newsDetail) {
        mShareLink = newsDetail.getShareLink();
        mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = TimeUtil.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();
        String NewsImgSrc = getImgSrcs(newsDetail);

        setToolBarLayout(mNewsTitle);
        //mNewsDetailTitleTv.setText(newsTitle);
        newsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);
        setNewsDetailBodyTv(newsDetail, newsBody);
    }

    /**
     * 设置详情数据内容
     *
     * @param newsDetail
     * @param newsBody
     */
    private void setNewsDetailBodyTv(final NewsDetail newsDetail, final String newsBody) {
        mRxManager.add(Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<Long>io_main())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setBody(newsDetail,newsBody);
                    }
                }));
    }

    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
//              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            mUrlImageGetter = new URLImageGetter(newsDetailBodyTv, newsBody, imgTotal);
            newsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            newsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }

    private boolean isShowBody(String newsBody, int imgTotal) {
        return imgTotal >= 2 && newsBody != null;
    }

    /**
     * 获得返回图片集合的第一张图片
     *
     * @param newsDetail
     * @return
     */
    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgs = newsDetail.getImg();
        String imgSrc = "";
        if (imgs != null && imgs.size() > 0) {
            imgSrc = imgs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(AppConstant.NEWS_IMG_RES);
        }
        return imgSrc;
    }

    private void setNewsDetailPhotoIv(String newsImgSrc) {
        ImageLoaderUtils.display(mContext, newsDetailPhotoIv, newsImgSrc);
    }

    private void setToolBarLayout(String mNewsTitle) {
        toolbar.setTitle(mNewsTitle);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }
}
