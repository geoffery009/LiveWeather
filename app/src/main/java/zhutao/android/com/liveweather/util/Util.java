package zhutao.android.com.liveweather.util;

/**
 * Created by Administrator on 2017/11/7.
 */

public class Util {
    /**
     * 华氏度->摄氏度的换算
     *
     * @param c
     * @return
     */
    public static int getCtoF(float c) {
        return (int) (c * 1.8f + 32);
    }

    /**
     * 摄氏度->华氏度的换算
     *
     * @param f
     * @return
     */
    public static int getFtoC(float f) {
        return (int) ((f - 32) / 1.8f);
    }
}
