package zhutao.android.com.liveweather;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import zhutao.android.com.liveweather.base.BaseActivity;
import zhutao.android.com.liveweather.model_each_time.SixWeatherBean;
import zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter;
import zhutao.android.com.liveweather.model_each_time.SixWeatherView;

public class MainActivity extends BaseActivity implements SixWeatherView {
    private SixWeatherPresenter eachTimePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eachTimePresenter = new SixWeatherPresenter();
        eachTimePresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eachTimePresenter.detachView();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showData(String data) {
        Logger.e(data);
        Gson gson = new Gson();
        try {
            SixWeatherBean bean = gson.fromJson(data, SixWeatherBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        }

    }
}
