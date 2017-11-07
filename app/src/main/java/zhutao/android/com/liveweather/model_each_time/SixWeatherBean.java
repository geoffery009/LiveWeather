package zhutao.android.com.liveweather.model_each_time;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 * error message：{"status":"param invalid"}
 */

public class SixWeatherBean {

    /**
     * date : 20171030
     * message : Success !
     * status : 200
     * city : 北京
     * count : 1
     * data : {"shidu":"45%","pm25":18,"pm10":48,"quality":"优","wendu":"5","ganmao":"各类人群可自由活动","yesterday":{"date":"29日星期日","sunrise":"06:38","high":"高温 12.0℃","low":"低温 1.0℃","sunset":"17:17","aqi":26,"fx":"北风","fl":"3-4级","type":"晴","notice":"晴空万里，去沐浴阳光吧"},"forecast":[{"date":"30日星期一","sunrise":"06:39","high":"高温 14.0℃","low":"低温 1.0℃","sunset":"17:16","aqi":63,"fx":"西北风","fl":"<3级","type":"晴","notice":"晴空万里，去沐浴阳光吧"},{"date":"31日星期二","sunrise":"06:40","high":"高温 15.0℃","low":"低温 2.0℃","sunset":"17:14","aqi":119,"fx":"南风","fl":"<3级","type":"晴","notice":"晴空万里，去沐浴阳光吧"},{"date":"01日星期三","sunrise":"06:42","high":"高温 18.0℃","low":"低温 4.0℃","sunset":"17:13","aqi":136,"fx":"西南风","fl":"<3级","type":"晴","notice":"lovely sunshine，尽情享受阳光的温暖吧"},{"date":"02日星期四","sunrise":"06:43","high":"高温 17.0℃","low":"低温 5.0℃","sunset":"17:12","aqi":34,"fx":"北风","fl":"3-4级","type":"多云","notice":"悠悠的云里有淡淡的诗"},{"date":"03日星期五","sunrise":"06:44","high":"高温 14.0℃","low":"低温 3.0℃","sunset":"17:11","aqi":99,"fx":"西北风","fl":"<3级","type":"晴","notice":"天气干燥，请适当增加室内湿度"}]}
     */

    private String date;
    private String message;
    private int status;
    private String city;
    private int count;
    private DataBean data;
    private long refreshMillsTime;

    public long getRefreshMillsTime() {
        return refreshMillsTime;
    }

    public void setRefreshMillsTime(long refreshMillsTime) {
        this.refreshMillsTime = refreshMillsTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shidu : 45%
         * pm25 : 18
         * pm10 : 48
         * quality : 优
         * wendu : 5
         * ganmao : 各类人群可自由活动
         * yesterday : {"date":"29日星期日","sunrise":"06:38","high":"高温 12.0℃","low":"低温 1.0℃","sunset":"17:17","aqi":26,"fx":"北风","fl":"3-4级","type":"晴","notice":"晴空万里，去沐浴阳光吧"}
         * forecast : [{"date":"30日星期一","sunrise":"06:39","high":"高温 14.0℃","low":"低温 1.0℃","sunset":"17:16","aqi":63,"fx":"西北风","fl":"<3级","type":"晴","notice":"晴空万里，去沐浴阳光吧"},{"date":"31日星期二","sunrise":"06:40","high":"高温 15.0℃","low":"低温 2.0℃","sunset":"17:14","aqi":119,"fx":"南风","fl":"<3级","type":"晴","notice":"晴空万里，去沐浴阳光吧"},{"date":"01日星期三","sunrise":"06:42","high":"高温 18.0℃","low":"低温 4.0℃","sunset":"17:13","aqi":136,"fx":"西南风","fl":"<3级","type":"晴","notice":"lovely sunshine，尽情享受阳光的温暖吧"},{"date":"02日星期四","sunrise":"06:43","high":"高温 17.0℃","low":"低温 5.0℃","sunset":"17:12","aqi":34,"fx":"北风","fl":"3-4级","type":"多云","notice":"悠悠的云里有淡淡的诗"},{"date":"03日星期五","sunrise":"06:44","high":"高温 14.0℃","low":"低温 3.0℃","sunset":"17:11","aqi":99,"fx":"西北风","fl":"<3级","type":"晴","notice":"天气干燥，请适当增加室内湿度"}]
         */

        private String shidu;
        private int pm25;
        private int pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public int getPmTwoFive() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 29日星期日
             * sunrise : 06:38
             * high : 高温 12.0℃
             * low : 低温 1.0℃
             * sunset : 17:17
             * aqi : 26
             * fx : 北风
             * fl : 3-4级
             * type : 晴
             * notice : 晴空万里，去沐浴阳光吧
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 30日星期一
             * sunrise : 06:39
             * high : 高温 14.0℃
             * low : 低温 1.0℃
             * sunset : 17:16
             * aqi : 63
             * fx : 西北风
             * fl : <3级
             * type : 晴
             * notice : 晴空万里，去沐浴阳光吧
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
