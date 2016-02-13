package com.cclight.acraslacknotifylib;

import android.app.Application;
import android.util.Log;

import org.acra.ACRA;
import org.acra.ReportField;

import java.io.IOException;
import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * @author claire
 * Register ACRA service with Slack
 */
public class SlackClient {

    private static SlackApi sSlackApi;
    public static String sSlackChannel;


    public static void init(Application app) {

        //IMPORTANT for ACRA
        ACRA.init(app);


        HttpLoggingInterceptor.Logger log = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(SlackClient.class.getCanonicalName(), message);
            }
        };

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log);
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SlackWebHooksUrl.URL_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        sSlackApi = retrofit.create(SlackApi.class);

    }

    public static void sendSlackMessage(String appName, EnumMap<ReportField, String> reportMap) {

        StringBuilder message = new StringBuilder();
        message.append(appName + " (V");
        message.append(reportMap.get(ReportField.APP_VERSION_NAME) + ") of ");
        message.append(reportMap.get(ReportField.BRAND) + " ");
        message.append(reportMap.get(ReportField.PHONE_MODEL) + " (V");
        message.append(reportMap.get(ReportField.ANDROID_VERSION) + ")");

        String stackTrace = reportMap.get(ReportField.STACK_TRACE);

        SlackWrapper.Builder wrapper = new SlackWrapper.Builder()
                .setUserName("ACRA")
                .setText(message.toString())
                .addAttachment("", "#f66718", "Error log", stackTrace.substring(0, (stackTrace.length() > 600) ? 600 : stackTrace.length()))
                .build();

        //Sending crash log
        retrofit2.Call<ResponseBody> response = sSlackApi.sendSlackWrapper(wrapper);

        try {
            response.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
