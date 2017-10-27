package zhutao.android.com.liveweather;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import zhutao.android.com.liveweather.base.BaseActivity;
import zhutao.android.com.liveweather.model_each_time.EachTimeBean;
import zhutao.android.com.liveweather.model_each_time.EachTimePresenter;
import zhutao.android.com.liveweather.model_each_time.EachTimeView;

public class MainActivity extends BaseActivity implements EachTimeView {
    private EachTimePresenter eachTimePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eachTimePresenter = new EachTimePresenter();
        eachTimePresenter.attachView(this);
        eachTimePresenter.getData("120.343,36.088");
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
            EachTimeBean bean = gson.fromJson(data, EachTimeBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        }

    }
}
