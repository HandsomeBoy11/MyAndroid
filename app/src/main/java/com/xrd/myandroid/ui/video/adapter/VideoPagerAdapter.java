package com.xrd.myandroid.ui.video.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2018/9/27.
 */

public class VideoPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fList = new ArrayList<>();//fragment的集合
    private List<String> tabList = new ArrayList<>();//tabname的集合

    public VideoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }

    public void setData(List<String> tabs, List<BaseFragment> fragments) {
        fList.clear();
        tabList.clear();
        fList.addAll(fragments);
        tabList.addAll(tabs);
        notifyDataSetChanged();
    }
}
