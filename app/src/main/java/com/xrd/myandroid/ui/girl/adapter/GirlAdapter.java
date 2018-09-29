package com.xrd.myandroid.ui.girl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.girl.bean.PhotoGirl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018/9/19.
 */

public class GirlAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private final LayoutInflater inflater;
    private List<PhotoGirl> girlList=new ArrayList<>();

    public GirlAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.girl_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        PhotoGirl photoGirl = girlList.get(position);
        if(photoGirl!=null){
            ImageLoaderUtils.display(mContext,holder1.girlItem,photoGirl.getUrl());
        }
        //照片条目点击
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallBack!=null){
                    mCallBack.onClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return girlList.size();
    }

    public void setData(List<PhotoGirl> mList) {
        girlList.clear();
        girlList.addAll(mList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.girl_item)
        ImageView girlItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private OnCallBack mCallBack;
    public void setOnItemClickListen(OnCallBack callback){
        this.mCallBack=callback;
    }
    public interface OnCallBack{
        void onClick(View view,int position);
    }
}
