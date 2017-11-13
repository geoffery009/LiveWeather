package zhutao.android.com.liveweather.base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import zhutao.android.com.liveweather.model_each_time.SixWeather;
import zhutao.android.com.liveweather.model_each_time.SixWeatherDao;

/**
 * Created by Administrator on 2017/11/13.
 */

@Database(entities = {SixWeather.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SixWeatherDao getSixWeatherDao();
}
