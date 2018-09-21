package com.xrd.myandroid.ui.main;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.base.BaseFragment;
import com.xrd.myandroid.R;
import com.xrd.myandroid.weight.TabBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tab_bar)
    TabBar tabBar;
    private List<BaseFragment> fragments;
    private BaseFragment currentFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initFragment();
        changeFragment(0);
        tabBar.setOnItemChangedListener(new TabBar.OnItemChangedListener() {
            @Override
            public void onItemChecked(int position) {
                changeFragment(position);
            }
        });
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new GirlFragment());
        fragments.add(new VideoFragment());
        fragments.add(new CareFragment());
    }

    /**
     * 切换fragment
     * @param position
     */
    public void changeFragment(int position){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BaseFragment fragment = fragments.get(position);
        if(fragment.isAdded()){
            if(currentFragment==null){
                transaction.show(fragment);
            }else{
                transaction.hide(currentFragment).show(fragment);
            }
        }else{
            if(currentFragment==null){
                transaction.add(R.id.fl_container,fragment);
            }else{
                transaction.hide(currentFragment).add(R.id.fl_container,fragment);
            }
        }
        transaction.commit();
        currentFragment = fragment;
    }
}
