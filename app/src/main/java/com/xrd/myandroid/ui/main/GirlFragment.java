package com.xrd.myandroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.DisplayUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.girl.activity.PhotoDetailActivity;
import com.xrd.myandroid.ui.girl.adapter.GirlAdapter;
import com.xrd.myandroid.ui.girl.bean.PhotoGirl;
import com.xrd.myandroid.ui.girl.contract.GirlContract;
import com.xrd.myandroid.ui.girl.model.GirlModel;
import com.xrd.myandroid.ui.girl.presenter.GirlPresenter;
import com.xrd.myandroid.weight.SpaceItemDecoration;
import com.xrd.myandroid.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by user on 2018/9/19.
 */

public class GirlFragment extends BaseFragment<GirlPresenter,GirlModel> implements GirlContract.View {
    @BindView(R.id.title_girl)
    TitleBar titleGirl;
    @BindView(R.id.rv_girl)
    RecyclerView rvGirl;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private GirlAdapter girlAdapter;
    private List<PhotoGirl> mList=new ArrayList<>();
    private static final int size=20;
    private int page=1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_girl;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        titleGirl.setBack(false);
        titleGirl.setTitle("靓妹子");
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL);
        rvGirl.setLayoutManager(gridLayoutManager);
        girlAdapter = new GirlAdapter(mContext);
        rvGirl.setAdapter(girlAdapter);
        //照片条目的点击事件
        girlAdapter.setOnItemClickListen(new GirlAdapter.OnCallBack() {
            @Override
            public void onClick(View view, int position) {
                String url = mList.get(position).getUrl();
                /*Intent intent = new Intent(mContext, PhotoDetailActivity.class);
                intent.putExtra("url",mList.get(position).getUrl());
                startActivity(intent);*/
                PhotoDetailActivity.startAct(mContext,view,url);
            }
        });
        //添加分割线
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(mContext,6,2,6,2,0,R.color.transparent);
        rvGirl.addItemDecoration(itemDecoration);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.getPhotosListDataRequest(size,page,false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                mPresenter.getPhotosListDataRequest(size,page,false);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getPhotosListDataRequest(size,page,true);
    }

    @Override
    public void showLoading(String title) {
        showProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void returnPhotosListData(List<PhotoGirl> photoGirls) {
        if(page==1){
            refreshLayout.finishRefresh();
            mList.clear();
            mList.addAll(photoGirls);
        }else{
            if(photoGirls==null||photoGirls.size()<=0){
                refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }else{
                refreshLayout.finishLoadMore();
                mList.addAll(photoGirls);
            }
        }
        if(mList.size()>0){
            girlAdapter.setData(mList);
        }
    }
}
