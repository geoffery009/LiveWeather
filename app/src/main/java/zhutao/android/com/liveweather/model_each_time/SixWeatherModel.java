package zhutao.android.com.liveweather.model_each_time;

import zhutao.android.com.liveweather.Network;
import zhutao.android.com.liveweather.base.BaseModel;
import zhutao.android.com.liveweather.base.ICallback;

/**
 * Created by Administrator on 2017/10/27.
 */

public class SixWeatherModel extends BaseModel<String> {
    @Override
    public void execute(final ICallback<String> callback) {
        //
        String url = mParams[0];
        //网络请求数据
        Network.getRequest(url, callback);
    }
}
