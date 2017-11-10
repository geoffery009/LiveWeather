package zhutao.android.com.liveweather.model_each_time;

import zhutao.android.com.liveweather.base.IModelView;

/**
 * Created by Administrator on 2017/10/27.
 */

public abstract interface SixWeatherView extends IModelView {
    void showData(String eachTimeBean);

}
