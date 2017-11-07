package zhutao.android.com.liveweather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import zhutao.android.com.liveweather.base.BaseActivity;
import zhutao.android.com.liveweather.model_each_time.SixWeatherBean;
import zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter;
import zhutao.android.com.liveweather.model_each_time.SixWeatherView;
import zhutao.android.com.liveweather.util.DateUtil;
import zhutao.android.com.liveweather.util.Util;

public class SixWeatherActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, SixWeatherView, SwipeRefreshLayout.OnRefreshListener {

    private SixWeatherPresenter eachTimePresenter;
    private String province = "江苏";
    private String city = "南京";
    private String cityDistrict;
    private boolean is_F_degree = false;//华氏度
    private SixWeatherBean data;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_home_hanbaobao));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //refresh
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE);
        swipeRefreshLayout.setOnRefreshListener(this);


        eachTimePresenter = new SixWeatherPresenter();
        eachTimePresenter.attachView(this);
        getData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void showData(String str) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        Logger.e(str);
        Gson gson = new Gson();
        try {
            data = gson.fromJson(str, SixWeatherBean.class);
            if (data != null) {
                initData();
            } else {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }

    }

    private void getData() {
        eachTimePresenter.getData(city);
        initTime();
    }

    private void initTime() {
        ((TextView) findViewById(R.id.text_city)).setText(city);
        ((TextView) findViewById(R.id.text_province)).setText(province);
    }

    private void initData() {
        final TextView text_weather_type = findViewById(R.id.text_weather_type);
        String str;
        if (is_F_degree) {
            str = "℃ | <font color='#2d3982'>℉</font>";
        } else {
            str = "<font color='#2d3982'>℃</font> | ℉";
        }
        text_weather_type.setText(Html.fromHtml(str));

        text_weather_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_F_degree = !is_F_degree;
                //todo refresh
                initData();
            }
        });


        ((TextView) findViewById(R.id.text_day)).setText(DateUtil.getStringWeek() + " , " + DateUtil.getStringDateShort() + " , " + data.getData().getForecast().get(0).getType());

        ((TextView) findViewById(R.id.text_tody_weather)).setText((int) getWeather(Float.parseFloat(data.getData().getWendu())) + "°");
        ((TextView) findViewById(R.id.text_today_max)).setText(initTemp(data.getData().getForecast().get(0).getHigh()));
        ((TextView) findViewById(R.id.text_today_min)).setText(initTemp(data.getData().getForecast().get(0).getLow()));
        ((ImageView) findViewById(R.id.text_tody_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getForecast().get(0).getType())));

        ((TextView) findViewById(R.id.text_pm_two_five)).setText(data.getData().getPmTwoFive() + "(" + data.getData().getQuality() + ")");
        ((TextView) findViewById(R.id.text_wind_des)).setText(data.getData().getForecast().get(0).getFx());
        ((TextView) findViewById(R.id.text_wind)).setText(data.getData().getForecast().get(0).getFl());
        ((TextView) findViewById(R.id.text_shidu)).setText(data.getData().getShidu());

        ((TextView) findViewById(R.id.text_week_1)).setText(initWeek(data.getData().getYesterday().getDate()));
        ((TextView) findViewById(R.id.text_week_1_weather)).setText(initTemp(data.getData().getYesterday().getLow()) + "/" + initTemp(data.getData().getYesterday().getHigh()));
        ((ImageView) findViewById(R.id.text_week_1_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getYesterday().getType())));

        ((TextView) findViewById(R.id.text_week_2)).setText(initWeek(data.getData().getForecast().get(1).getDate()));
        ((TextView) findViewById(R.id.text_week_2_weather)).setText(initTemp(data.getData().getForecast().get(1).getLow()) + "/" + initTemp(data.getData().getForecast().get(1).getHigh()));
        ((ImageView) findViewById(R.id.text_week_2_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getForecast().get(1).getType())));

        ((TextView) findViewById(R.id.text_week_3)).setText(initWeek(data.getData().getForecast().get(2).getDate()));
        ((TextView) findViewById(R.id.text_week_3_weather)).setText(initTemp(data.getData().getForecast().get(2).getLow()) + "/" + initTemp(data.getData().getForecast().get(2).getHigh()));
        ((ImageView) findViewById(R.id.text_week_3_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getForecast().get(2).getType())));

        ((TextView) findViewById(R.id.text_week_4)).setText(initWeek(data.getData().getForecast().get(3).getDate()));
        ((TextView) findViewById(R.id.text_week_4_weather)).setText(initTemp(data.getData().getForecast().get(3).getLow()) + "/" + initTemp(data.getData().getForecast().get(3).getHigh()));
        ((ImageView) findViewById(R.id.text_week_4_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getForecast().get(3).getType())));

        ((TextView) findViewById(R.id.text_week_5)).setText(initWeek(data.getData().getForecast().get(4).getDate()));
        ((TextView) findViewById(R.id.text_week_5_weather)).setText(initTemp(data.getData().getForecast().get(4).getLow()) + "/" + initTemp(data.getData().getForecast().get(4).getHigh()));
        ((ImageView) findViewById(R.id.text_week_5_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getForecast().get(4).getType())));
    }

    private String initTemp(String str) {
        try {
            String temp = "";
            if (str.contains("高温")) {
                temp = str.split("高温 ")[1].replace("℃", "");
            } else if (str.contains("低温")) {
                temp = str.split("低温 ")[1].replace("℃", "");
            }
            return (int) getWeather(Float.parseFloat(temp)) + "°";
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        return str;
    }

    private String initWeek(String week) {
        try {
            return week.split("日")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return week;
        }
    }

    private int icDrawable(String key) {
        if (key.contains("雪")) {
            if (key.contains("暴雪")) {
                return R.drawable.ic_snow_5;
            } else if (key.contains("大雪")) {
                return R.drawable.ic_snow_4;
            } else if (key.contains("中雪")) {
                return R.drawable.ic_snow_3;
            } else if (key.contains("小雪")) {
                return R.drawable.ic_snow_2;
            } else if (key.contains("雨夹雪")) {
                return R.drawable.ic_snow_1;
            } else {
                return R.drawable.ic_rainy_2;
            }
        } else if (key.contains("雨")) {
            if (key.contains("雷阵雨")) {
                return R.drawable.ic_rainy_5;
            } else if (key.contains("暴雨")) {
                return R.drawable.ic_rainy_4;
            } else if (key.contains("大雨")) {
                return R.drawable.ic_rainy_3;
            } else if (key.contains("中雨")) {
                return R.drawable.ic_rainy_2;
            } else if (key.contains("小雨")) {
                return R.drawable.ic_rainy_1;
            } else {
                return R.drawable.ic_rainy_1;
            }

        } else if (key.contains("阴")) {
            return R.drawable.ic_cloudy_1;

        } else if (key.contains("云")) {
            return R.drawable.ic_cloudy_2;

        } else if (key.contains("风")) {
            if (key.contains("台风")) {
                return R.drawable.ic_windy_2;
            } else {
                return R.drawable.ic_windy_1;
            }

        } else if (key.contains("沙")) {
            if (key.contains("沙尘暴")) {
                return R.drawable.ic_dity_2;
            } else {
                return R.drawable.ic_dity_1;
            }
        } else if (key.contains("冰雹")) {
            return R.drawable.ic_ice;

        } else if (key.contains("雾")) {
            if (key.contains("雾霾")) {
                return R.drawable.ic_fog_2;
            } else {
                return R.drawable.ic_fog_1;
            }
        } else if (key.contains("晴")) {
            return R.drawable.ic_sunny;
        } else
            return R.drawable.ic_sunny;
    }

    private float getWeather(float f) {
        if (is_F_degree) {
            return Util.getCtoF(f);
        } else {
            return f;
        }
    }

    @Override
    public void onRefresh() {
        is_F_degree = false;
        getData();
    }
}
