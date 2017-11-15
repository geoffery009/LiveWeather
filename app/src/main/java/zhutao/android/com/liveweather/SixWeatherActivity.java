package zhutao.android.com.liveweather;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.List;

import zhutao.android.com.liveweather.base.BaseActivity;
import zhutao.android.com.liveweather.base.CheckPermissionsListener;
import zhutao.android.com.liveweather.base.CityInfo;
import zhutao.android.com.liveweather.citylist.CityListActivity;
import zhutao.android.com.liveweather.lifecycle.BDLocationLiveData;
import zhutao.android.com.liveweather.model_each_time.SixWeatherBean;
import zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter;
import zhutao.android.com.liveweather.model_each_time.SixWeatherView;
import zhutao.android.com.liveweather.util.DateUtil;
import zhutao.android.com.liveweather.util.IntentUtil;
import zhutao.android.com.liveweather.util.Util;

import static zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter.getWeather;
import static zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter.icDrawable;
import static zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter.initTemp;
import static zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter.initWeek;
import static zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter.is_F_degree;

public class SixWeatherActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, SixWeatherView, SwipeRefreshLayout.OnRefreshListener, CheckPermissionsListener {

    private SixWeatherPresenter eachTimePresenter;
    private String selectProvince = "";
    private String selectCity = "";
    private String cityDistrict = "";
    private SixWeatherBean data;//show data
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;

    protected final String[] neededPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_home_hanbaobao));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //refresh
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        swipeRefreshLayout.setOnRefreshListener(this);

        eachTimePresenter = new SixWeatherPresenter();
        eachTimePresenter.attachView(this);


        requestPermissions(this, neededPermissions, this);
    }

    @Override
    public void onGranted() {
        initLiveData();
    }

    @Override
    public void onDenied(List<String> permissions) {
        Toast.makeText(this, "权限被禁用，请到设置里打开", Toast.LENGTH_SHORT).show();
    }

    private void initLiveData() {
        if (Util.isGPSOPen(this)) {
            BDLocationLiveData liveData = new BDLocationLiveData(this);
            liveData.observe(this, new android.arch.lifecycle.Observer<BDLocation>() {
                @Override
                public void onChanged(@Nullable BDLocation bdLocation) {
                    MenuItem locationItem = navigationView.getMenu().findItem(R.id.nav_location);
                    locationItem.setTitle("当前: " + bdLocation.getCity() + bdLocation.getDistrict());

                    String city = bdLocation.getCity().replace("市", "");
                    String province = bdLocation.getProvince().replace("省", "");

                    eachTimePresenter.saveLocalFile(city, province);
                    getLocWeatherData(city, province);
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("打开GPS获取精准位置");
            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    IntentUtil.openGPS(SixWeatherActivity.this);
                }
            });
            builder.setNegativeButton("取消", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLiveData();
            } else {
                this.finish();
            }
        }
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

        if (id == R.id.nav_location) {
            CityInfo info = eachTimePresenter.getLocalFile();
            if (!TextUtils.isEmpty(info.getCity()) && !info.getCity().equals(selectCity)) {
                getLocWeatherData(info.getCity(), info.getProvince());
            }
        } else if (id == R.id.nav_list) {
            CityListActivity.isFlag = is_F_degree;
            startActivityForResult(new Intent(this, CityListActivity.class), 1);
        } else if (id == R.id.nav_share) {
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
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        findViewById(R.id.loading_layout).setVisibility(View.GONE);
        findViewById(R.id.empty_layout).setVisibility(View.GONE);
        findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        findViewById(R.id.loading_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.empty_layout).setVisibility(View.GONE);
        findViewById(R.id.content_layout).setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        findViewById(R.id.loading_layout).setVisibility(View.GONE);
        findViewById(R.id.empty_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.content_layout).setVisibility(View.GONE);
    }

    @Override
    public void showNetwotkData(SixWeatherBean bean) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        data = bean;
        showWeatherData();
    }

    //获取位置天气
    private void getLocWeatherData(String city, String privince) {
        selectCity = city;
        selectProvince = privince;
        is_F_degree = false;
        getData(true);
    }


    private void getData(boolean showProgress) {
        eachTimePresenter.getData(new CityInfo(selectCity, selectProvince), showProgress);
    }

    private void showWeatherData() {
        ((TextView) findViewById(R.id.text_city)).setText(selectCity);
        ((TextView) findViewById(R.id.text_province)).setText(selectProvince);

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
                showWeatherData();
            }
        });


        ((TextView) findViewById(R.id.text_day)).setText(DateUtil.getStringWeek() + " , " + DateUtil.getStringDateShort() + " , " + data.getData().getForecast().get(0).getType());

        ((TextView) findViewById(R.id.text_tody_weather)).setText((int) getWeather(Float.parseFloat(data.getData().getWendu())) + "°");
        ((TextView) findViewById(R.id.text_today_max)).setText(initTemp(data.getData().getForecast().get(0).getHigh()));
        ((TextView) findViewById(R.id.text_today_min)).setText(initTemp(data.getData().getForecast().get(0).getLow()));
        ((ImageView) findViewById(R.id.text_tody_weather_img)).setImageDrawable(getResources().getDrawable(icDrawable(data.getData().getForecast().get(0).getType())));

        ((TextView) findViewById(R.id.text_pm_two_five)).setText(data.getData().getPmTwoFive() + "");

        final View pm25ClickView = findViewById(R.id.pm25Layout);
        pm25ClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(pm25ClickView, "空气质量：" + data.getData().getQuality() + "\n" + data.getData().getGanmao(), Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                snackbar.show();
                TextView msg = snackbar.getView().findViewById(R.id.snackbar_text);
                msg.setMaxLines(4);
            }
        });

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

    @Override
    public void onRefresh() {
        getData(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
            getLocWeatherData(city, "江苏");
        }
    }


}
