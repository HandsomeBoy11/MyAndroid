package com.xrd.myandroid.ui.home.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ACache;
import com.xrd.myandroid.R;
import com.xrd.myandroid.app.AppApplication;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.HomeChannelContract;
import com.xrd.myandroid.ui.home.adapter.PinDaoAdapter;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.model.HomeChannelModel;
import com.xrd.myandroid.ui.home.presenter.HomeChannelPresenter;
import com.xrd.myandroid.ui.home.utils.ItemDragHelperCallback;
import com.xrd.myandroid.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsChannelActivity extends BaseActivity<HomeChannelPresenter,HomeChannelModel>implements HomeChannelContract.View{

    @BindView(R.id.pindao_title)
    TitleBar pindaoTitle;
    @BindView(R.id.rv_mine)
    RecyclerView rvMine;
    @BindView(R.id.rv_more)
    RecyclerView rvMore;
    private PinDaoAdapter mineAdapter;
    private PinDaoAdapter moreAdapter;
    private ArrayList<NewsChannelTable> mineList=new ArrayList<>();
    private ArrayList<NewsChannelTable> moreList=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        pindaoTitle.setTitle("频道管理");
        pindaoTitle.setBack(true);
        final GridLayoutManager mineManager = new GridLayoutManager(this, 4);
        GridLayoutManager moreManager = new GridLayoutManager(this, 4);
        rvMine.setLayoutManager(mineManager);
        rvMore.setLayoutManager(moreManager);
        mineAdapter = new PinDaoAdapter(this, false);
        moreAdapter = new PinDaoAdapter(this, true);
        rvMine.setAdapter(mineAdapter);
        rvMore.setAdapter(moreAdapter);

        //长按拖拽回调以及绑定
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mineAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvMine);
        mineAdapter.setItemDragHelperCallback(itemDragHelperCallback);
        mineAdapter.setSwapCallBack(new PinDaoAdapter.OnSwapCallBack() {
            @Override
            public void onSwap(List<NewsChannelTable> list) {
                mPresenter.onItemSwpe(((ArrayList<NewsChannelTable>) list));
            }
        });

        //点击
        initEvent();

        mPresenter.lodeMineChannelsRequest();
        mPresenter.lodeMoreChannelsRequest();
    }

    private void initEvent() {
        //我的里点击条目
        mineAdapter.setListener(new PinDaoAdapter.OnClickListener() {
            @Override
            public void onClick(NewsChannelTable bean, int position, boolean isMore) {
                mineList.remove(position);
                moreList.add(bean);
                mineAdapter.setdata(mineList);
                moreAdapter.setdata(moreList);
                mPresenter.onItemAddOrRemove(mineList,moreList);
                /*ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,mineList);
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE,moreList);*/
            }
        });
        //更多里点击条目
        moreAdapter.setListener(new PinDaoAdapter.OnClickListener() {
            @Override
            public void onClick(NewsChannelTable bean, int position, boolean isMore) {
                moreList.remove(position);
                mineList.add(bean);
                mineAdapter.setdata(mineList);
                moreAdapter.setdata(moreList);
                mPresenter.onItemAddOrRemove(mineList,moreList);
                /*ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,mineList);
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE,moreList);*/
            }
        });
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
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {
        if(newsChannelsMine==null||newsChannelsMine.size()<=0) {
            return;
        }
        this.mineList.clear();
        mineList.addAll(newsChannelsMine);
        mineAdapter.setdata(newsChannelsMine);
    }

    @Override
    public void returnMoreNewsChannels(List<NewsChannelTable> newsChannelsMine) {
        if(newsChannelsMine==null||newsChannelsMine.size()<=0) {
            return;
        }
        moreList.clear();
        moreList.addAll(newsChannelsMine);
        moreAdapter.setdata(newsChannelsMine);
    }
}
