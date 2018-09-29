package com.xrd.myandroid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaydenxiao.common.base.BaseActivity;
import com.xrd.myandroid.R;
import com.xrd.myandroid.app.AppApplication;
import com.xrd.myandroid.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_jump)
    TextView tvJump;
    private Handler mHandler;
    private int allTime=3;
    private MyRunnable myRunnable;
    private String advaceImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mHandler = new Handler();
        advaceImg = "img";
        doTimeer();
    }

    private void doTimeer() {
        if(myRunnable==null){
            myRunnable=new MyRunnable();
        }
        mHandler.post(myRunnable);
    }
    private void stopTimeer(){
        if(myRunnable!=null){
            mHandler.removeCallbacks(myRunnable);
            myRunnable=null;
            if(!TextUtils.isEmpty(advaceImg)){
//                goAdavace();
                goMain();
            }else{
                goMain();
            }
        }

    }

    /**
     * 进入到主页
     */
    private void goMain() {
        startActivity(MainActivity.class);
        finish();
    }

    /**
     * 进入到广告页
     */
    private void goAdavace() {
        startActivity(AdavaceActivity.class);
        finish();
    }

    @OnClick(R.id.tv_jump)
    public void onViewClicked() {
        stopTimeer();
    }
     class MyRunnable implements Runnable{

        @Override
        public void run() {
            mHandler.postDelayed(this,1000);
            tvJump.setText(allTime+"s");
            allTime--;
            if(allTime<0){
                stopTimeer();
            }

        }
    }
    private long lastTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if(currentTime-lastTime>2000){
                Toast.makeText(mContext, "再次点击退出应用", Toast.LENGTH_SHORT).show();
            }else{
                finish();
            }
            lastTime=currentTime;
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }


}
