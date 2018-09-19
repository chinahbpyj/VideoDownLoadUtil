package com.pyj.recycleviewutil.base.newrequest;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.pyj.recycleviewutil.base.bean.BaseBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CommonResponseCallback
        implements Callback<ResponseBody> {

    private Class beanClass;

    public CommonResponseCallback() {
    }

    public CommonResponseCallback(Class beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        BaseBean bean = new BaseBean();
        try {
            bean = (BaseBean) beanClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (response.body() != null) {

            String json = "";
            try {
                json = response.body().string();
                Gson gson = new Gson();
                bean = (BaseBean) gson.fromJson(json, beanClass);

                onDone(bean);

            } catch (Exception e) {
                e.printStackTrace();
                onFail();
            }
        } else {
            //http error
            onFail();
        }

        //http complete
        onAlways(bean);
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (!call.isCanceled()) {
            t.printStackTrace();
            toast("网络请求失败");
            onAlways(null);
        }
    }

    public void onDone(BaseBean baseBean) {
    }

    public void onFail() {
        toast("服务异常");
    }

    public void onAlways(BaseBean baseBean) {
    }

    public void toast(String msg) {
        //AppUtils.showToast(msg);
    }

    public void showMsg(BaseBean baseBean) {
        showMsg(baseBean, baseBean.msg);
    }

    public void showMsg(String toastMsg) {
        if (!TextUtils.isEmpty(toastMsg)) {
            toast(toastMsg);
        }
    }

    public void showMsg(BaseBean baseBean, String defMsg) {
        if (baseBean != null) {
            String toastMsg = baseBean.msg;
            if (TextUtils.isEmpty(baseBean.msg) && !TextUtils.isEmpty(defMsg)) {
                toastMsg = defMsg;
            }
            if (!TextUtils.isEmpty(toastMsg)) {
                toast(toastMsg);
            }
        }
    }
}
