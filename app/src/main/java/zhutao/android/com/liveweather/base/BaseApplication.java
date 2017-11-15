package zhutao.android.com.liveweather.base;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/11/8.
 */

public class BaseApplication extends Application {
    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "db5f783893", false);
        getDb(this);
    }

    private AppDatabase getDb(Context context) {
        if (db == null) {
            //create db
            db = Room.databaseBuilder(context, AppDatabase.class, "db").build();
        }
        return db;
    }
}
