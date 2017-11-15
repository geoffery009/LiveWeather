package zhutao.android.com.liveweather.model_each_time;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;

import zhutao.android.com.liveweather.Network;
import zhutao.android.com.liveweather.R;
import zhutao.android.com.liveweather.base.BaseApplication;
import zhutao.android.com.liveweather.base.BasePresenter;
import zhutao.android.com.liveweather.base.CityInfo;
import zhutao.android.com.liveweather.base.ICallback;
import zhutao.android.com.liveweather.util.DateUtil;
import zhutao.android.com.liveweather.util.Util;

/**
 * Created by Administrator on 2017/10/27.
 */

public class SixWeatherPresenter extends BasePresenter<SixWeatherView> {
    public static boolean is_F_degree = false;//华氏度
    private Gson gson;
    private boolean showProgress;

    public SixWeatherPresenter() {
        gson = new Gson();
    }

    public void getData(CityInfo info, boolean showProgress) {
        this.showProgress = showProgress;
        if (isViewAttached() && showProgress) {
            getView().showProgress();
        }
        getDBData(info);
    }

    private void getNetworkData(final CityInfo info) {
        //location key lang unit
        String city = info.getCity();
        try {
            city = java.net.URLEncoder.encode(city, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), e.toString());
            if (isViewAttached()) {
                getView().getThisContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().hideProgress();
                    }
                });
            }
            return;
        }

        String param = Network.SIX_WEATHER_API + city;
        SixWeatherModel model = new SixWeatherModel();
        model.params(param).execute(new ICallback<String>() {
            @Override
            public void onSuccess(final String str) {
                Logger.e(str);
                if (isViewAttached()) {
                    getView().getThisContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().hideProgress();
                            try {
                                final SixWeatherBean data = gson.fromJson(str, SixWeatherBean.class);
                                if (data != null && data.getStatus() == 200) {
                                    //db update
                                    saveData(info, str, DateUtil.getStringDateShort2());
                                    getView().showNetwotkData(data);
                                } else {
                                    getView().showEmpty();
                                    getView().showMsg(str);
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                getView().showEmpty();
                                getView().showMsg(str);
                            }
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
    }

    private void getDBData(final CityInfo info) {
        AsyncTask<String, Integer, SixWeather> task = new AsyncTask<String, Integer, SixWeather>() {

            @Override
            protected SixWeather doInBackground(String[] objects) {
                return BaseApplication.db.getSixWeatherDao().findByCity(info.getCity());
            }

            @Override
            protected void onPostExecute(SixWeather o) {
                super.onPostExecute(o);

                if (o == null || !showProgress) {
                    getNetworkData(info);
                } else {
                    final SixWeatherBean data = gson.fromJson(o.getDatas(), SixWeatherBean.class);
                    if (isViewAttached()) {
                        getView().getThisContext().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideProgress();
                                getView().showNetwotkData(data);
                            }
                        });
                    }
                }
            }
        };
        task.execute();
    }

    public void saveData(CityInfo info, String result, String updateDayT) {
        final SixWeather sixWeather = new SixWeather(info.getCity(), info.getProvince(), result, updateDayT);

        AsyncTask<Void, Integer, Void> task = new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                BaseApplication.db.getSixWeatherDao().insert(sixWeather);
                return null;
            }
        };
        task.execute();
    }

    public void saveLocalFile(String city, String province) {
        SharedPreferences p = getView().getThisContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("location_city", city);
        editor.putString("location_province", province);
        editor.commit();
    }

    public CityInfo getLocalFile() {
        SharedPreferences p = getView().getThisContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        CityInfo info = new CityInfo(p.getString("location_city", ""), p.getString("location_province", ""));
        return info;
    }

    public static String initTemp(String str) {
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

    public static String initWeek(String week) {
        try {
            return week.substring(week.indexOf("日") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return week;
        }
    }


    public static int icDrawable(String key) {
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

    public static float getWeather(float f) {
        if (is_F_degree) {
            return Util.getCtoF(f);
        } else {
            return f;
        }
    }
}
