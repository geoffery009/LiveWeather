package zhutao.android.com.liveweather.citylist;

/**
 * Created by Administrator on 2017/11/13.
 */

public class CityWeatherBean {
    private String city;
    private boolean flag;
    private String weather;
    private String updateT;//20170909
    private String province;
    private String temperature;//

    public String getUpdateT() {
        return updateT;
    }

    public void setUpdateT(String updateT) {
        this.updateT = updateT;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
