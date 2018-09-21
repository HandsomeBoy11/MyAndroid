package com.xrd.myandroid.weight;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xrd.myandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hh on 2016/5/17.
 */
public class TabBar extends LinearLayout {


    @BindView(R.id.iv_tab1)
    ImageView ivTab1;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.ll_tab1)
    LinearLayout llTab1;
    @BindView(R.id.iv_tab2)
    ImageView ivTab2;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.ll_tab2)
    LinearLayout llTab2;
    @BindView(R.id.iv_tab3)
    ImageView ivTab3;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.ll_tab3)
    LinearLayout llTab3;
    @BindView(R.id.iv_tab4)
    ImageView ivTab4;
    @BindView(R.id.tv_tab4)
    TextView tvTab4;
    @BindView(R.id.ll_tab4)
    LinearLayout llTab4;
    private Context ct;
    private OnItemChangedListener onItemChangedListener;
    private int mCurrentItem = -1;

    public TabBar(Context context) {
        this(context, null, -1);
    }

    public TabBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ct = context;
        init();
    }

    private void init() {
        View view = View.inflate(ct, R.layout.m_bottom, this);
        ButterKnife.bind(this, view);
        ivTab1.setImageResource(R.mipmap.ic_home_selected);
        tvTab1.setTextColor(getResources().getColor(R.color.main_color));
        setBackgroundColor(ContextCompat.getColor(ct, R.color.white));
    }

   @OnClick({R.id.ll_tab1, R.id.ll_tab2, R.id.ll_tab3, R.id.ll_tab4})
   public void onViewClicked(View view) {
       int clickPosition = -1;
       switch (view.getId()) {
           case R.id.ll_tab1:
               clickPosition = 0;
               break;
           case R.id.ll_tab2:
               clickPosition = 1;
               break;
           case R.id.ll_tab3:
               clickPosition = 2;
               break;
           case R.id.ll_tab4:
               clickPosition = 3;
               break;
       }
       setItemChecked(clickPosition);
   }

    public void setItemChecked(int position) {

        if (mCurrentItem == position) {
            return;
        }

        //如果监听了此方法,且return true , 则消费掉
        if (onItemChangedListener != null) {
            onItemChangedListener.onItemChecked(position);
        }

        //LogUtil.log("切换到:" + position);
        mCurrentItem = position;

        checkTab(position);

    }

    private void checkTab(int position) {
        //处理一些颜色的变化
        switch (position) {
            case 0:
                resetColors();
                ivTab1.setImageResource(R.mipmap.ic_home_selected);
                tvTab1.setTextColor(ContextCompat.getColor(ct, R.color.main_color));
                break;
            case 1:
                resetColors();
                ivTab2.setImageResource(R.mipmap.ic_girl_selected);
                tvTab2.setTextColor(ContextCompat.getColor(ct, R.color.main_color));
                break;
            case 2:
                resetColors();
                ivTab3.setImageResource(R.mipmap.ic_video_selected);
                tvTab3.setTextColor(ContextCompat.getColor(ct, R.color.main_color));
                break;
            case 3:
                resetColors();
                ivTab4.setImageResource(R.mipmap.ic_care_selected);
                tvTab4.setTextColor(ContextCompat.getColor(ct, R.color.main_color));
                break;
        }
    }

    private void resetColors() {
        ivTab1.setImageResource(R.mipmap.ic_home_normal);
        ivTab2.setImageResource(R.mipmap.ic_girl_normal);
        ivTab3.setImageResource(R.mipmap.ic_video_normal);
        ivTab4.setImageResource(R.mipmap.ic_care_normal);

        tvTab1.setTextColor(Color.parseColor("#666666"));
        tvTab2.setTextColor(Color.parseColor("#666666"));
        tvTab3.setTextColor(Color.parseColor("#666666"));
        tvTab4.setTextColor(Color.parseColor("#666666"));

    }

    public void setOnItemChangedListener(OnItemChangedListener l) {
        this.onItemChangedListener = l;
    }

    public interface OnItemChangedListener {
        void onItemChecked(int position);
    }
}
