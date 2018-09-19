package com.pyj.recycleviewutil.base.recycleView;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import lnyp.recyclerview.EndlessRecyclerOnScrollListener;
import lnyp.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import lnyp.recyclerview.RecyclerViewLoadingFooter;
import lnyp.recyclerview.RecyclerViewStateUtils;

public abstract class BaseScrollRecyclerView<T> extends RecyclerView {

    private Context ctx;

    private int page = 1;
    private int pageSize = 15;
    private int total = 0;
    private boolean isLastPage;

    private HashMap<String, String> params = new HashMap<>();
    private BaseScrollRecycleService service;
    private RecycleViewAdapter adapter;
    private BaseRecycleBean<T> serviceBean;

    private RecycleViewListener recycleViewListener;

    public BaseScrollRecyclerView(Context context) {
        super(context);
    }

    public BaseScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(Context context, RecycleViewAdapter adapter, BaseScrollRecycleService service) {
        ctx = context;
        this.service = service;
        this.adapter = adapter;

        HeaderAndFooterRecyclerViewAdapter hfAdaper = new HeaderAndFooterRecyclerViewAdapter(adapter);
        setAdapter(hfAdaper);
        setLayoutManager(new LinearLayoutManager(ctx));

        final BaseScrollRecyclerView self = this;

        addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);

                boolean mHasMore = !isLastPage();
                if (mHasMore) {
                    RecyclerViewStateUtils.setFooterViewState((Activity) ctx, self, true, RecyclerViewLoadingFooter.State.Loading, null);
                    refresh();
                } else {
                    RecyclerViewStateUtils.setFooterViewState((Activity) ctx, self, false, RecyclerViewLoadingFooter.State.TheEnd, null);
                }
            }
        });
    }

    public void refresh() {
        page += 1;
        query(null, false, true);
    }

    public void query() {
        page = 1;
        query(null);
    }

    public void query(HashMap<String, String> newParams) {
        page = 1;
        query(newParams, true, true);
    }

    public void query(HashMap<String, String> newParams, final boolean reset, boolean append) {
        mergeParams(newParams, append);
        final BaseScrollRecyclerView self = this;
        service.setRecycleServiceListener(new BaseScrollRecycleService.RecycleServiceListener<T>() {
            @Override
            public void onSuccess(BaseRecycleBean<T> bean) {
                try {

                    List<T> items = bean.getItems();

                    isLastPage = items.size() > 0 ? false : true;

                    if (reset) {
                        adapter.reset(items);
                    } else {
                        adapter.appendDataSource(items);
                    }
                    total = bean.getTotal();
                    serviceBean = bean;

                    if (items.size() == 0) {
                        if (recycleViewListener != null) {
                            recycleViewListener.onQueryNoResult();
                        }
                    } else {
                        if (recycleViewListener != null) {
                            recycleViewListener.onQuerySuccess();
                        }
                    }
                } catch (NullPointerException e) {

                }
            }

            @Override
            public void onFail() {
                if (recycleViewListener != null) {
                    recycleViewListener.onQueryFail();
                }
            }

            @Override
            public void onComplete(BaseRecycleBean<T> bean) {
                RecyclerViewStateUtils.setFooterViewState(self, RecyclerViewLoadingFooter.State.Normal);

                if (recycleViewListener != null) {
                    recycleViewListener.onQueryFinish();
                }
            }
        });

        if (recycleViewListener != null) {
            recycleViewListener.onBeforeQuery();
        }

        service.query(this.params);
    }

    /**
     * @param newParams
     * @param append    true表示追加条件 false表示重置原来的条件
     */
    private void mergeParams(HashMap<String, String> newParams, boolean append) {
        if (append) {
            if (newParams != null) {
                this.params.putAll(newParams);
            }
        } else {
            if (newParams != null) {
                this.params = newParams;
            } else {
                this.params = new HashMap<>();
            }
        }

        this.params.put("page", page + "");
        //this.params.put("pageSize", pageSize + "");
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public int getPageIndex() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public void setPageIndex(int pageIndex) {
        this.page = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public BaseRecycleBean<T> getServiceBean() {
        return serviceBean;
    }

    public void setServiceBean(BaseRecycleBean<T> serviceBean) {
        this.serviceBean = serviceBean;
    }

    public RecycleViewListener getRecycleViewListener() {
        return recycleViewListener;
    }

    public void setRecycleViewListener(RecycleViewListener recycleViewListener) {
        this.recycleViewListener = recycleViewListener;
    }

    public static class AbstractRecycleViewListener implements RecycleViewListener {

        @Override
        public void onBeforeQuery() {
        }

        @Override
        public void onQueryNoResult() {
        }

        @Override
        public void onQuerySuccess() {
        }

        @Override
        public void onQueryFinish() {
        }

        @Override
        public void onQueryFail() {
        }
    }

    public interface RecycleViewListener {
        void onBeforeQuery();

        void onQueryNoResult();

        void onQuerySuccess();

        void onQueryFinish();

        void onQueryFail();
    }
}
