package zhutao.android.com.liveweather.model_each_time;

import android.util.Log;

import java.io.UnsupportedEncodingException;

import zhutao.android.com.liveweather.Network;
import zhutao.android.com.liveweather.base.BasePresenter;
import zhutao.android.com.liveweather.base.ICallback;

/**
 * Created by Administrator on 2017/10/27.
 */

public class SixWeatherPresenter extends BasePresenter<SixWeatherView> {

    public void getData(String city) {
        if (isViewAttached()) {
            getView().showProgress();
        }

        //location key lang unit
        try {
            city = java.net.URLEncoder.encode(city, "utf-8");
            String param = Network.SIX_WEATHER_API + city;
            SixWeatherModel model = new SixWeatherModel();
            model.params(param).execute(new ICallback<String>() {
                @Override
                public void onSuccess(final String result) {
                    if (isViewAttached()) {
                        getView().getThisContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideProgress();
                                getView().showData(result);
                            }
                        });
                    }
                }

                @Override
                public void onFailture(final String msg) {
                    if (isViewAttached()) {
                        getView().getThisContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideProgress();
                                getView().showMsg(msg);
                            }
                        });
                    }
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), e.toString());
        }
    }
}
