package zhutao.android.com.liveweather.base;

/**
 * Created by Administrator on 2017/11/14.
 */

public class CityInfo {
    private String city;
    private String province;

    public CityInfo(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
