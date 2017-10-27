package zhutao.android.com.liveweather.base;

/**
 * Created by Administrator on 2017/10/27.
 */

public interface ICallback<T> {
    void onSuccess(T result);

    void onFailture(String msg);
}
