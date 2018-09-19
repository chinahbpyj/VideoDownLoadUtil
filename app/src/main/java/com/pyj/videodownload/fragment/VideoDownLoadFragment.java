package com.pyj.videodownload.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyj.recycleviewutil.base.newrequest.VideoDownloadFactory;
import com.pyj.recycleviewutil.base.recycleView.BaseScrollRecycleService;
import com.pyj.recycleviewutil.base.recycleView.BaseScrollRecyclerView;
import com.pyj.videodownload.R;
import com.pyj.videodownload.adapter.VideoDownLoadListAdapter;
import com.pyj.videodownload.bean.VideoDownloadBean;
import com.pyj.videodownload.util.AppUtils;
import com.pyj.videodownload.view.VideoDownLoadRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class VideoDownLoadFragment extends BaseFragment {
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoDownLoadRecyclerView videoDownLoadRecyclerView;
    private VideoDownLoadListAdapter adapter;

    public VideoDownLoadFragment() {

    }

    public static VideoDownLoadFragment newInstance() {
        VideoDownLoadFragment fragment = new VideoDownLoadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.video_download, container, false);

            initView();
        }

        return view;
    }

    public void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        videoDownLoadRecyclerView = view.findViewById(R.id.videoDownLoadRecyclerView);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        initVideoRecyclerView();

        load();
    }

    private void initVideoRecyclerView() {
        adapter = new VideoDownLoadListAdapter(new ArrayList<VideoDownloadBean.Result>());

        videoDownLoadRecyclerView.init(getActivity(), adapter, new BaseScrollRecycleService(VideoDownloadBean.class) {
            @Override
            public Call<ResponseBody> makeCall(HashMap params) {
                return VideoDownloadFactory.getInstance().generateService().videoListQuery(
                        params.get("source") == null ? "" : params.get("source") + "",
                        params.get("keyword") == null ? "" : params.get("keyword") + "",
                        params.get("page") + "");
            }
        });

        videoDownLoadRecyclerView.setRecycleViewListener(new BaseScrollRecyclerView.AbstractRecycleViewListener() {
            @Override
            public void onQueryNoResult() {
                videoDownLoadRecyclerView.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onQuerySuccess() {
                videoDownLoadRecyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onQueryFinish() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onQueryFail() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter.setOnViewClickListener(new VideoDownLoadListAdapter.OnViewClickListener() {
            @Override
            public void OnViewClick(String copyText) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(copyText));
                    intent.addCategory("android.intent.category.DEFAULT");
                    startActivity(intent);
                } catch (Exception e) {
                    AppUtils.showSnackBar(videoDownLoadRecyclerView, "没有安装", getResources().getColor(R.color.colorPrimary));
                }
            }
        });
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initData();
        }
    };

    @Override
    public void initData() {
        videoDownLoadRecyclerView.query(new HashMap<String, String>() {{
            put("source", "种子搜");
            put("keyword", "军旅");
        }});
    }
}
