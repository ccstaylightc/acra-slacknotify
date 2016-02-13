package com.cclight.acraslacknotifylib;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author claire
 */
public interface SlackApi {

    @Headers({"Content-Type: application/json"})
    @POST(SlackWebHooksUrl.URL_CHANNEL)
    Call<ResponseBody> sendSlackWrapper(@Body SlackWrapper.Builder wrapper);

    @Headers({"Content-Type: application/json"})
    @POST(SlackWebHooksUrl.URL_CHANNEL)
    Call<ResponseBody> sendMessage(@Body String message);

}
