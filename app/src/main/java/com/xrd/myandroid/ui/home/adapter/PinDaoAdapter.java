package com.xrd.myandroid.ui.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenxiao.common.commonutils.ACache;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.home.bean.NewsChannelTable;
import com.xrd.myandroid.ui.home.utils.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 2018/9/7.
 */

public class PinDaoAdapter extends RecyclerView.Adapter implements ItemDragHelperCallback.OnItemMoveListener{
    private List<NewsChannelTable> mList=new ArrayList<>();
    private Context mContext;
    private boolean isMore;
    private final LayoutInflater inflater;
    private ItemDragHelperCallback mItemDragHelperCallback;

    public PinDaoAdapter(Context mContext, boolean isMore) {
        this.mContext = mContext;
        this.isMore=isMore;
        inflater = LayoutInflater.from(mContext);
    }
    public void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback) {
        mItemDragHelperCallback = itemDragHelperCallback;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pindao_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        final NewsChannelTable pindaodataBean = mList.get(position);
        String s = pindaodataBean.getNewsChannelName();
        final boolean fixed = pindaodataBean.getNewsChannelFixed();
        TextView tvText = holder1.tvText;
        tvText.setText(s);
        if(fixed){
            tvText.setTextColor(Color.WHITE);
        }else{
            tvText.setTextColor(Color.parseColor("#333333"));
        }
        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    if(!fixed)
                    listener.onClick(pindaodataBean,position,isMore);
                }
            }
        });
        if(mItemDragHelperCallback!=null)
        holder1.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mItemDragHelperCallback.setLongPressEnabled(!fixed);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setdata(List<NewsChannelTable> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(isChannelFixed(fromPosition,toPosition)){
            return false;
        }
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        if(callBack!=null){
            callBack.onSwap(mList);
        }
        return true;
    }

    /**
     *
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private boolean isChannelFixed(int fromPosition, int toPosition) {
        return (mList.get(fromPosition).getNewsChannelFixed() ||
                mList.get(toPosition).getNewsChannelFixed())/*&&(fromPosition==0||toPosition==0)*/;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        void onClick(NewsChannelTable bean, int position, boolean isMore);
    }
    private OnSwapCallBack callBack;

    public void setSwapCallBack(OnSwapCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnSwapCallBack{
        void onSwap(List<NewsChannelTable> list);
    }
}
