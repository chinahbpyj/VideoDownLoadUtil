package com.pyj.videodownload.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pyj.recycleviewutil.base.recycleView.RecycleViewAdapter;
import com.pyj.videodownload.R;
import com.pyj.videodownload.bean.VideoDownloadBean;
import com.pyj.videodownload.util.StringUtil;

import java.util.List;

public class VideoDownLoadListAdapter extends RecycleViewAdapter<VideoDownloadBean.Result,
        VideoDownLoadListAdapter.ViewHolder> {

    public VideoDownLoadListAdapter(List<VideoDownloadBean.Result> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_video_download;
    }

    @Override
    public ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void onBeforeCreateViewHolder(final ViewHolder holder) {
        holder.magnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    Object tag = holder.magnet.getTag();
                    if (tag == null) {
                        return;
                    }

                    int position = (int) tag;
                    VideoDownloadBean.Result result = dataSource.get(position);


                    mListener.OnViewClick(result.magnet);
                }
            }
        });
    }


    @Override
    public void onBeforeBindViewHolder(final VideoDownloadBean.Result item, ViewHolder holder, int position) {
        holder.videoName.setText(item.name);

        String clarity = item.resolution;
        if (StringUtil.isEmpty(clarity)) {
            holder.videoClarity.setVisibility(View.GONE);
        } else {
            holder.videoClarity.setVisibility(View.VISIBLE);
            holder.videoClarity.setText(item.resolution);
        }

        holder.videoSize.setText(item.formatSize);
        holder.videoDate.setText(item.count);

        holder.magnet.setTag(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView videoName;
        public TextView videoClarity;
        public TextView videoSize;
        public TextView videoDate;
        public TextView magnet;

        public ViewHolder(View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.videoName);
            videoClarity = itemView.findViewById(R.id.videoClarity);
            videoSize = itemView.findViewById(R.id.videoSize);
            videoDate = itemView.findViewById(R.id.videoDate);
            magnet = itemView.findViewById(R.id.magnet);
        }
    }

    private OnViewClickListener mListener;

    public interface OnViewClickListener {
        void OnViewClick(String copyText);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        mListener = listener;
    }
}
