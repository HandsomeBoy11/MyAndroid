package com.xrd.myandroid.ui.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.video.adapter.VideoPagerAdapter;
import com.xrd.myandroid.ui.video.bean.VideoChannelTable;
import com.xrd.myandroid.ui.video.contract.VideoContract;
import com.xrd.myandroid.ui.video.fragment.VideoSubFragment;
import com.xrd.myandroid.ui.video.model.VideoModel;
import com.xrd.myandroid.ui.video.presenter.VideoPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by user on 2018/9/19.
 */

public class VideoFragment extends BaseFragment<VideoPresenter, VideoModel> implements VideoContract.View {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_video)
    ViewPager vpVideo;
    private VideoPagerAdapter pagerAdapter;
    private List<BaseFragment> fragments = new ArrayList<>();//fragment的集合
    private List<String> tabs = new ArrayList<>();//tabname的集合

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        pagerAdapter = new VideoPagerAdapter(getChildFragmentManager());
        vpVideo.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(vpVideo);
        mPresenter.getVideoTabList();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,30,30);
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
    public void getTabList(List<VideoChannelTable> list) {
        LogUtils.loge(list+"tab数量");
        fragments.clear();
        tabs.clear();
        for (int i = 0; i < list.size(); i++) {
            VideoSubFragment videoSubFragment = new VideoSubFragment();
            VideoChannelTable videoChannelTable = list.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("id", videoChannelTable.getChannelId());
            bundle.putString("name", videoChannelTable.getChannelName());
            videoSubFragment.setArguments(bundle);
            fragments.add(videoSubFragment);
            tabs.add(videoChannelTable.getChannelName());
        }
        vpVideo.setOffscreenPageLimit(fragments.size());
        pagerAdapter.setData(tabs, fragments);
    }
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
