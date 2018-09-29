package com.xrd.myandroid.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.home.bean.NewsSummary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018/9/19.
 */

public class HomeSubAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private final LayoutInflater inflater;
    private List<NewsSummary> mList=new ArrayList<>();

    public HomeSubAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_sub_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        NewsSummary newsSummary = mList.get(position);
        ImageLoaderUtils.displayRound(mContext,holder1.ivSubItem,newsSummary.getImgsrc());
        holder1.tvTitle.setText(newsSummary.getTitle());
        holder1.tvDes.setText(newsSummary.getDigest());
        holder1.tvTiem.setText(newsSummary.getPtime());
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallBack!=null){
                    mCallBack.onClick(view,holder1.ivSubItem,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<NewsSummary> newsSummaries) {
        mList.clear();
        mList.addAll(newsSummaries);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_sub_item)
        ImageView ivSubItem;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_tiem)
        TextView tvTiem;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private OnCallBack mCallBack;
    public void setOnItemClickListener(OnCallBack callBack){
        this.mCallBack=callBack;
    }
    public interface OnCallBack{
        void onClick(View view,View imageView,int position);
    }
}
