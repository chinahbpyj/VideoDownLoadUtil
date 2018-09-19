package com.pyj.recycleviewutil.base.newrequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoDownloadService {

    @GET("search")
    Call<ResponseBody> videoListQuery(@Query("source") String source,
                                      @Query("keyword") String keyword,
                                      @Query("page") String page);
}
