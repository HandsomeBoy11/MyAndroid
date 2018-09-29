package com.xrd.myandroid.ui.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xrd.myandroid.R;
import com.xrd.myandroid.ui.video.bean.VideoData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by user on 2018/9/27.
 */

public class VideoSubAdapter extends Adapter {
    private Context mContext;
    private final LayoutInflater inflater;
    private List<VideoData> mVideoDataList = new ArrayList<>();
    private String url="http://flv2.bn.netease.com/videolib3/1609/06/UnuGW1312/HD/UnuGW1312-mobile.mp4";

    public VideoSubAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.video_sub_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        VideoData videoData = mVideoDataList.get(position);
        String mp4_url = videoData.getMp4_url();
        int playersize = videoData.getPlayersize();
        JCVideoPlayerStandard jcVideoPlayerStandard=holder1.videoplayer;
//                videoData.getMp4_url()
        boolean setUp = jcVideoPlayerStandard.setUp(
                url, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                TextUtils.isEmpty(videoData.getDescription())?videoData.getTitle()+"":videoData.getDescription());
        if (setUp) {
            Glide.with(mContext).load(videoData.getCover())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                    .crossFade().into(jcVideoPlayerStandard.thumbImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mVideoDataList.size();
    }

    public void setData(List<VideoData> mList) {
        mVideoDataList.clear();
        mVideoDataList.addAll(mList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.videoplayer)
        JCVideoPlayerStandard videoplayer;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_times)
        TextView tvTimes;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
