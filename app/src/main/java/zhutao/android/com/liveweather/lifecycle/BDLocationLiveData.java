package zhutao.android.com.liveweather.lifecycle;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.baidu.location.BDLocation;

import zhutao.android.com.liveweather.broadcast.BaiduLocationReceiver;
import zhutao.android.com.liveweather.service.BaiduLocationService;

/**
 * Created by Administrator on 2017/11/10.
 */

public class BDLocationLiveData extends LiveData<BDLocation> {
    private Context c;
    private LocalBroadcastManager manager;

    private BroadcastReceiver callBack = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BDLocation data = intent.getParcelableExtra(BaiduLocationReceiver.DATA_EXTRA);
            if (data != null) {
                setValue(data);
            }
        }
    };

    public BDLocationLiveData(Context c) {
        //baidu location start
        c.startService(new Intent(c, BaiduLocationService.class));
        this.c = c;
    }

    @Override
    protected void onActive() {//Activity onstart
        super.onActive();

        manager = LocalBroadcastManager.getInstance(c);
        IntentFilter inflater = new IntentFilter();
        inflater.addAction(BaiduLocationReceiver.LOCATION_DATA);
        manager.registerReceiver(callBack, inflater);
    }

    @Override
    protected void onInactive() {//Activity ondestory
        super.onInactive();
    }
}
