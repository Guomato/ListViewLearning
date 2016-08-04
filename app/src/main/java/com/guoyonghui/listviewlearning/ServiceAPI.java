package com.guoyonghui.listviewlearning;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface ServiceAPI {

    @GET
    Observable<ResponseBody> download(@Url String imageUrl);

}
