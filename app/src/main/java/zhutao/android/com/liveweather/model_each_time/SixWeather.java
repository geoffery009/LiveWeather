package zhutao.android.com.liveweather.model_each_time;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/11/13.
 */

@Entity
public class SixWeather {
    @PrimaryKey
    @NonNull
    private String city;

    @ColumnInfo(name = "province")
    private String province;

    @ColumnInfo(name = "datas")
    private String datas;

    @ColumnInfo(name = "updateT")
    private String updateT;

    public SixWeather(@NonNull String city, String province, String datas, String updateT) {
        this.city = city;
        this.province = province;
        this.datas = datas;
        this.updateT = updateT;
    }

    public String getProvince() {
        return province;
    }

    public String getUpdateT() {
        return updateT;
    }

    public String getCity() {
        return city;
    }

    public String getDatas() {
        return datas;
    }
}
