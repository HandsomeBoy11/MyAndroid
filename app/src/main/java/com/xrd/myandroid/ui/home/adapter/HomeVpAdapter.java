package com.xrd.myandroid.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jaydenxiao.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/9/19.
 */

public class HomeVpAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments=new ArrayList<>();
    private List<String> mTitleList=new ArrayList<>();

    public void setFragmentsAndTitle(List<BaseFragment> fragmentList,List<String> titles){
        fragments.clear();
        mTitleList.clear();
        fragments.addAll(fragmentList);
        mTitleList.addAll(titles);
        notifyDataSetChanged();

    }
    public HomeVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
