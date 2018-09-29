package com.xrd.myandroid.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xrd.myandroid.R;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.HomeItemContract;
import com.xrd.myandroid.ui.home.activity.NewsDetailActivity;
import com.xrd.myandroid.ui.home.adapter.HomeSubAdapter;
import com.xrd.myandroid.ui.home.bean.NewsSummary;
import com.xrd.myandroid.ui.home.model.HomeItemModel;
import com.xrd.myandroid.ui.home.presenter.HomeItemPresenter;
import com.xrd.myandroid.weight.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by user on 2018/9/19.
 */

public class HomeItemFragment extends BaseFragment<HomeItemPresenter, HomeItemModel> implements HomeItemContract.View {
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    Unbinder unbinder;
    private SpaceItemDecoration itemDecoration;
    private HomeSubAdapter homeSubAdapter;
    private int position;
    private String newsType;
    private int startPage = 0;
    private String newsId;
    private List<NewsSummary> mList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home_item;
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
        rvHome.setLayoutManager(new LinearLayoutManager(mContext));
        homeSubAdapter = new HomeSubAdapter(mContext);
        rvHome.setAdapter(homeSubAdapter);
        if (itemDecoration != null) {
            rvHome.removeItemDecoration(itemDecoration);
        } else {
            itemDecoration = new SpaceItemDecoration(mContext,8,13,8,13,
                    1,R.color.alpha_25_black);
        }
        rvHome.addItemDecoration(itemDecoration);
        homeSubAdapter.setOnItemClickListener(new HomeSubAdapter.OnCallBack() {
            @Override
            public void onClick(View view, View imageView,int position) {
                NewsSummary newsSummary = mList.get(position);
                NewsDetailActivity.startAct(mContext,imageView,newsSummary.getPostid(),newsSummary.getImgsrc());

            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            newsId = bundle.getString(AppConstant.NEWS_ID);
            newsType = bundle.getString(AppConstant.NEWS_TYPE);
            position = bundle.getInt(AppConstant.CHANNEL_POSITION);
            mPresenter.getNewsListDataRequest(newsType, newsId, startPage);//请求数据列表
        }
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                startPage += 20;
                mPresenter.getNewsListDataRequest(newsType, newsId, startPage);//请求数据列表
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                startPage = 0;
                mPresenter.getNewsListDataRequest(newsType, newsId, startPage);//请求数据列表
            }
        });
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        } else if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void showErrorTip(String msg) {
        stopProgressDialog();
        startPage-=20;
    }

    /**
     * @param newsSummaries
     */
    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries) {
        if (startPage == 0) {//刷新或者第一次加载
            mList.clear();
            mList.addAll(newsSummaries);
            refreshLayout.finishRefresh();
        } else {//加载更多
            if (newsSummaries.size() == 0) {
                refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                mList.addAll(newsSummaries);
                refreshLayout.finishLoadMore();
            }
        }
        if (mList.size() > 0) {
            tvNoData.setVisibility(View.GONE);
            homeSubAdapter.setData(mList);
            refreshLayout.setEnableLoadMore(true);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            refreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void netError() {
        if(mList==null||mList.size()<=0){
            tvNoData.setText("发生错误，点击重试");
            tvNoData.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.tv_no_data)
    public void onViewClicked() {
        refreshLayout.autoRefresh();
    }
}
