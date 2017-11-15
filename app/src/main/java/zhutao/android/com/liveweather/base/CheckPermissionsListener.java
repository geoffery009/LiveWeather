package zhutao.android.com.liveweather.base;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */

public interface CheckPermissionsListener {
    void onGranted();
    void onDenied(List<String> permissions);
}