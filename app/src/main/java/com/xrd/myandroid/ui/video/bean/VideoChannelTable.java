package com.xrd.myandroid.ui.video.bean;

/**
 * Created by user on 2018/9/27.
 */

public class VideoChannelTable {
    private String channelId;
    private String channelName;

    public VideoChannelTable(String channelId,String channelName){
        this.channelId=channelId;
        this.channelName=channelName;
    }
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "VideoChannelTable{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                '}';
    }
}
