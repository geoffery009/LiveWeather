package zhutao.android.com.liveweather.util;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by Administrator on 2017/11/14.
 */

public class IntentUtil {
    public static void openGPS(Activity context) {

        // 转到手机设置界面，用户设置GPS
        Intent intent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);

        context.startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
    }
}
