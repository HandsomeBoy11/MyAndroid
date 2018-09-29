package com.xrd.myandroid.ui.video.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.video.adapter.VideoSubAdapter;
import com.xrd.myandroid.ui.video.bean.VideoData;
import com.xrd.myandroid.ui.video.contract.VideoSubContract;
import com.xrd.myandroid.ui.video.model.VideoSubModel;
import com.xrd.myandroid.ui.video.presenter.VideoSubPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;

/**
 * Created by user on 2018/9/27.
 */

public class VideoSubFragment extends BaseFragment<VideoSubPresenter, VideoSubModel> implements VideoSubContract.View {
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private int startPage = 0;
    private String id;
    private boolean isRefresh;
    private List<VideoData> mList = new ArrayList<>();
    private VideoSubAdapter videoSubAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video_sub;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        refreshLayout.setEnableLoadMore(false);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvVideo.setLayoutManager(manager);
        videoSubAdapter = new VideoSubAdapter(mContext);
        rvVideo.setAdapter(videoSubAdapter);
        getTabInfo();//拿到tabid 请求网络
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                startPage++;
                mPresenter.getVideoList(id, startPage, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startPage = 0;
                mPresenter.getVideoList(id, startPage, false);
            }
        });
        rvVideo.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.listener() != null) {
                    JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.listener();
                    if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });
        tvNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLayout.autoRefresh();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public void getTabInfo() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            String name = bundle.getString("name");
            mPresenter.getVideoList(id, 0, true);
        }
    }

    @Override
    public void showLoading(String title) {
        showProgressDialog();
    }

    @Override
    public void stopLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        if (startPage > 0) {
            startPage--;
        }
        if(refreshLayout.isRefreshing()){
            refreshLayout.finishRefresh();
            ToastUitl.showShort(msg);
        }else if(refreshLayout.isLoading()){
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void returnVideoList(List<VideoData> list) {
        if (startPage == 0) {//刷新和初始获取数据
            mList.clear();
            mList.addAll(list);
            refreshLayout.finishRefresh();
        } else {//加载更多
            if (list.size() > 0) {
                mList.addAll(list);
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
        }
        if(mList.size()>0){
            tvNoData.setVisibility(View.GONE);
            videoSubAdapter.setData(mList);
            refreshLayout.setEnableLoadMore(true);
        }else{
            tvNoData.setVisibility(View.VISIBLE);
            refreshLayout.setEnableLoadMore(false);
        }

    }
}
