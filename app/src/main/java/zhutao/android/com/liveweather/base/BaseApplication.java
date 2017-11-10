package zhutao.android.com.liveweather.base;

import android.app.Application;
import android.content.Intent;

import zhutao.android.com.liveweather.service.BaiduLocationService;

/**
 * Created by Administrator on 2017/11/8.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //baidu location start
        startService(new Intent(this, BaiduLocationService.class));
    }
}
