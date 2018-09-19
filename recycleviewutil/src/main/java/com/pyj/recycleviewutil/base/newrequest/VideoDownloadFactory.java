package com.pyj.recycleviewutil.base.newrequest;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class VideoDownloadFactory {
    //http://bt.xiandan.in/api/search?source=种子搜&keyword=钢铁侠&page=1
    private VideoDownloadService service;

    private VideoDownloadFactory() {
        service = createService(
                VideoDownloadService.class,
                "http://bt.xiandan.in/api/");
    }

    private static class SingletonHolder {
        private static final VideoDownloadFactory INSTANCE = new VideoDownloadFactory();
    }

    public static VideoDownloadFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient getOkHttpClient() {
        long DEFAULT_TIMEOUT = 3000;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //包含header、body数据
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder
                /*.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        String api_url = request.url().encodedPath().substring(1);

                        Request req = request.newBuilder()
                                .addHeader("OS-NAME", "android")
                                .addHeader("APP-VERSION", AppUtils.getAppVersionName())
                                .addHeader("Accept", Constants.API_ACCEPT)
                                .addHeader("Authorization", authorization)
                                .addHeader("sign", AppUtils.getSign(api_url))
                                .build();

                        return chain.proceed(req);
                    }

                })*/
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor());
        //.proxy(Proxy.NO_PROXY)

        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return httpClientBuilder.build();
    }

    public VideoDownloadService generateService() {
        if (service == null) {
            service = createService(
                    VideoDownloadService.class,
                    "http://bt.xiandan.in/api/");
        }

        return service;
    }
}