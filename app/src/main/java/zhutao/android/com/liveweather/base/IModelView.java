package zhutao.android.com.liveweather.base;

import android.app.Activity;

/**
 * Created by Administrator on 2017/10/27.
 */

public interface IModelView {
    void showMsg(String msg);

    void hideProgress();

    void showProgress();

    Activity getThisContext();
}
