package zhutao.android.com.liveweather.citylist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import zhutao.android.com.liveweather.R;
import zhutao.android.com.liveweather.model_each_time.SixWeather;
import zhutao.android.com.liveweather.model_each_time.SixWeatherBean;
import zhutao.android.com.liveweather.util.Util;

public class CityListActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    private List<CityWeatherBean> data = new ArrayList<>();
    public static boolean isFlag;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        RelativeLayout toolbar = (RelativeLayout) findViewById(R.id.toolbar);

        toolbar.findViewById(R.id.img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity();
            }
        });
        toolbar.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityListActivity.this.finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(data, this, this);
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        CitylistViewModel viewModel = ViewModelProviders.of(this).get(CitylistViewModel.class);
        viewModel.getDatas().observe(this, new Observer<List<SixWeather>>() {
            @Override
            public void onChanged(@Nullable List<SixWeather> sixWeathers) {
                if (sixWeathers != null) {
                    mGson = new Gson();
                    for (SixWeather weather : sixWeathers) {
                        SixWeatherBean weatherBean = mGson.fromJson(weather.getDatas(), SixWeatherBean.class);

                        CityWeatherBean bean = new CityWeatherBean();
                        bean.setUpdateT(weather.getUpdateT());
                        bean.setProvince(weather.getProvince());
                        bean.setCity(weather.getCity());
                        bean.setFlag(isFlag);
                        float wenduF = Float.parseFloat(weatherBean.getData().getWendu());
                        bean.setTemperature(isFlag ? (Util.getCtoF(wenduF) + "") : ((int) wenduF + ""));
                        bean.setWeather(weatherBean.getData().getForecast().get(0).getType());
                        data.add(bean);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCity() {
        startActivityForResult(new Intent(this, CityPickerActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
            backWithData(city);
        }
    }

    private void backWithData(String city) {
        Intent data = new Intent();
        data.putExtra(CityPickerActivity.KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(int position) {
        backWithData(data.get(position).getCity());
    }
}
