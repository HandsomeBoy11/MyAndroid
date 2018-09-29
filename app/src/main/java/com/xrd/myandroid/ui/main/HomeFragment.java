package com.xrd.myandroid.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.xrd.myandroid.R;
import com.xrd.myandroid.app.AppConstant;
import com.xrd.myandroid.ui.home.Contract.HomeContract;
import com.xrd.myandroid.ui.home.activity.NewsChannelActivity;
import com.xrd.myandroid.ui.home.adapter.HomeVpAdapter;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.fragment.HomeItemFragment;
import com.xrd.myandroid.ui.home.model.HomeModel;
import com.xrd.myandroid.ui.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by user on 2018/9/19.
 */

public class HomeFragment extends BaseFragment<HomePresenter,HomeModel> implements HomeContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    Unbinder unbinder;
    private List<BaseFragment> fragments;
    private List<String> titles;
    private HomeVpAdapter homeVpAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        initFragment();
        homeVpAdapter = new HomeVpAdapter(getChildFragmentManager());
        vpHome.setAdapter(homeVpAdapter);
        tabLayout.setupWithViewPager(vpHome);
        mPresenter.lodeMineChannelsRequest();


    }

    /**
     * 创建数据集合
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
//        ToastUitl.showShort("添加频道");
        startActivity(NewsChannelActivity.class);
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
        if(newsChannelsMine==null&&newsChannelsMine.size()<=0){
            return ;
        }
        fragments.clear();
        titles.clear();
        for (int i = 0; i < newsChannelsMine.size(); i++) {
            NewsChannelTable newsChannel=newsChannelsMine.get(i);
            HomeItemFragment homeItemFragment = new HomeItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.NEWS_ID, newsChannel.getNewsChannelId());
            bundle.putString(AppConstant.NEWS_TYPE, newsChannel.getNewsChannelType());
            bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
            homeItemFragment.setArguments(bundle);
            fragments.add(homeItemFragment);
            NewsChannelTable newsChannelTable = newsChannelsMine.get(i);
            String newsChannelName = newsChannelTable.getNewsChannelName();
            titles.add(newsChannelName);
        }
        vpHome.setOffscreenPageLimit(fragments.size());
        homeVpAdapter.setFragmentsAndTitle(fragments,titles);
        vpHome.setCurrentItem(0);
    }
}
