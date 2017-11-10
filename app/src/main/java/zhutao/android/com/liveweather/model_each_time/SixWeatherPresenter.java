package zhutao.android.com.liveweather.model_each_time;

import android.util.Log;

import java.io.UnsupportedEncodingException;

import zhutao.android.com.liveweather.Network;
import zhutao.android.com.liveweather.R;
import zhutao.android.com.liveweather.base.BasePresenter;
import zhutao.android.com.liveweather.base.ICallback;
import zhutao.android.com.liveweather.util.Util;

/**
 * Created by Administrator on 2017/10/27.
 */

public class SixWeatherPresenter extends BasePresenter<SixWeatherView> {
    public static boolean is_F_degree = false;//华氏度

    public void getData(String city, boolean showProgress) {
        if (isViewAttached() && showProgress) {
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
                                getView().showNetwotkData(result);
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
