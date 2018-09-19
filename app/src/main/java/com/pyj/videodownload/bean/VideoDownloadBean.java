package com.pyj.videodownload.bean;

import com.pyj.recycleviewutil.base.bean.BaseBean;
import com.pyj.recycleviewutil.base.recycleView.BaseRecycleBean;

import java.util.List;

public class VideoDownloadBean extends BaseBean implements BaseRecycleBean {
    @Override
    public int getPageIndex() {
        return currentPage;
    }

    @Override
    public int getPageSize() {
        return 15;
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public List<Result> getItems() {
        return results;
    }

    public int currentPage;
    public String currentSourceSite;
    public List<Result> results;

    public class Result {
        public String magnet;
        public String name;
        public String formatSize;
        public String size;
        public String count;
        public String detailUrl;
        public String resolution;
    }
}
