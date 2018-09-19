package com.pyj.recycleviewutil.base.recycleView;

import com.pyj.recycleviewutil.base.bean.BaseBean;
import com.pyj.recycleviewutil.base.newrequest.CommonResponseCallback;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;

public abstract class BaseScrollRecycleService<T> {

    private Class serviceBeanClass;

    protected RecycleServiceListener recycleServiceListener;

    public BaseScrollRecycleService(Class serviceBeanClass) {
        this.serviceBeanClass = serviceBeanClass;
    }

    public void setRecycleServiceListener(RecycleServiceListener recycleServiceListener) {
        this.recycleServiceListener = recycleServiceListener;
    }

    public void query(HashMap<String, String> params) {
        Call<ResponseBody> call = makeCall(params);
        call.enqueue(new CommonResponseCallback(serviceBeanClass) {
            @Override
            public void onDone(BaseBean baseBean) {
                if (baseBean instanceof BaseRecycleBean) {
                    if (recycleServiceListener != null) {
                        recycleServiceListener.onSuccess((BaseRecycleBean) baseBean);
                    }
                }
            }

            @Override
            public void onFail() {
                if (recycleServiceListener != null) {
                    recycleServiceListener.onFail();
                }
            }

            @Override
            public void onAlways(BaseBean baseBean) {
                if (baseBean != null && baseBean instanceof BaseRecycleBean) {
                    if (recycleServiceListener != null) {
                        recycleServiceListener.onComplete((BaseRecycleBean) baseBean);
                    }
                }
            }
        });
    }

    public abstract Call<ResponseBody> makeCall(HashMap<String, String> params);

    public interface RecycleServiceListener<T> {
        void onSuccess(BaseRecycleBean<T> bean);

        void onFail();

        void onComplete(BaseRecycleBean<T> bean);
    }
}
