package zhutao.android.com.liveweather.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import zhutao.android.com.liveweather.broadcast.BaiduLocationReceiver;
import zhutao.android.com.liveweather.service.BaiduLocationService;

/**
 * BDLocationLiveData replace Lifecycle
 * Created by Administrator on 2017/11/10.
 */

public class BDLocationLifecycle implements LifecycleObserver {
    private BroadcastReceiver callBack;//location data callback
    private Context c;
    private LocalBroadcastManager manager;

    public BDLocationLifecycle(Context context, Lifecycle lifecycle, BroadcastReceiver callBack) {
        //baidu location start
        context.startService(new Intent(context, BaiduLocationService.class));
        this.callBack = callBack;
        this.c = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {//Activity onCreate
        manager = LocalBroadcastManager.getInstance(c);
        IntentFilter inflater = new IntentFilter();
        inflater.addAction(BaiduLocationReceiver.LOCATION_DATA);
        manager.registerReceiver(callBack, inflater);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory() {//Activity onDestory
        manager.unregisterReceiver(callBack);
    }

}
