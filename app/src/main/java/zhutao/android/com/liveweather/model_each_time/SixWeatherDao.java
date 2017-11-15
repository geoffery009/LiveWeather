package zhutao.android.com.liveweather.model_each_time;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Administrator on 2017/11/13.
 */

@Dao
public interface SixWeatherDao {

    @Query("SELECT * FROM sixweather WHERE city = :cityName")
    SixWeather findByCity(String cityName);

    @Insert(onConflict = REPLACE)
    void insert(SixWeather sixWeather);

    @Query("SELECT * FROM sixweather")
    List<SixWeather> findAll();
}
