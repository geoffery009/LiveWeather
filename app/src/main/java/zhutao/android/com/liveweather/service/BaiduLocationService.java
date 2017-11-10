package zhutao.android.com.liveweather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import zhutao.android.com.liveweather.BuildConfig;
import zhutao.android.com.liveweather.broadcast.BaiduLocationReceiver;

/**
 * Created by Administrator on 2017/11/7.
 */

public class BaiduLocationService extends Service {
    private LocationClient mLocationClient;
    private BDAbstractLocationListener myListener;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            Log.e(BaiduLocationService.this.getClass().getName(), "end service----> baidu location");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void init() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);

    }

    private void startLocation() {
        if (BuildConfig.DEBUG) {
            Log.e(BaiduLocationService.this.getClass().getName(), "start service----> baidu location");
        }

        mLocationClient.start();
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息

            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String cityCode = location.getCityCode();
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();


            if (TextUtils.isEmpty(city)) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(BaiduLocationReceiver.DATA_EXTRA, location);
            intent.setAction(BaiduLocationReceiver.LOCATION);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BaiduLocationService.this);
            localBroadcastManager.sendBroadcast(intent);

            if (BuildConfig.DEBUG) {
                Log.e(this.getClass().getName(), addr);
                Log.e(this.getClass().getName(), country + "," + province + "," + city + "," + cityCode + "," + district + "," + street);
                Log.e(this.getClass().getName(), latitude + "," + longitude);
            }
        }
    }
}
