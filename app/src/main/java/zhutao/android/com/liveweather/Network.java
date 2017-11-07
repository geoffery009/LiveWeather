package zhutao.android.com.liveweather;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import zhutao.android.com.liveweather.base.ICallback;

/**
 * Created by Administrator on 2017/10/27.
 */

public class Network {
    public static String SIX_WEATHER_API = "http://www.sojson.com/open/api/weather/json.shtml?city=";

    //Get请求
    public static void getRequest(final String url, final ICallback<String> callback) {
        Log.e("tag", "url---" + url);
        ScheduledExecutorService requestService = Executors.newSingleThreadScheduledExecutor();
        requestService.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body().string());
                    } else {
                        callback.onFailture(response.body().string());
                    }
//                    System.out.println("Get---Result: " + response.isSuccessful());
//                    System.out.println("Server: " + response.header("Server"));
//                    System.out.println("ResponseBody: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
