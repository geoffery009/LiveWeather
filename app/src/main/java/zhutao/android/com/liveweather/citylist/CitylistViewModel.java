package zhutao.android.com.liveweather.citylist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import zhutao.android.com.liveweather.base.BaseApplication;
import zhutao.android.com.liveweather.model_each_time.SixWeather;

/**
 * Created by Administrator on 2017/11/14.
 */

public class CitylistViewModel extends ViewModel {
    private MutableLiveData<List<SixWeather>> data;

    public MutableLiveData<List<SixWeather>> getDatas() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        getData();
        return data;
    }

    private void getData() {
        AsyncTask<Void, Integer, List<SixWeather>> task = new AsyncTask<Void, Integer, List<SixWeather>>() {
            @Override
            protected List<SixWeather> doInBackground(Void... voids) {
                return BaseApplication.db.getSixWeatherDao().findAll();
            }

            @Override
            protected void onPostExecute(List<SixWeather> sixWeathers) {
                super.onPostExecute(sixWeathers);
                data.setValue(sixWeathers);
            }
        };
        task.execute();
    }
}
