package com.pyj.videodownload.view;

import android.content.Context;
import android.util.AttributeSet;

import com.pyj.recycleviewutil.base.recycleView.BaseScrollRecyclerView;
import com.pyj.videodownload.bean.VideoDownloadBean;

public class VideoDownLoadRecyclerView extends BaseScrollRecyclerView<VideoDownloadBean.Result> {
    public VideoDownLoadRecyclerView(Context context) {
        super(context);
    }

    public VideoDownLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoDownLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
