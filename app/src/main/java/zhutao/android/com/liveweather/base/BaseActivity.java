package zhutao.android.com.liveweather.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements IModelView {
    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Activity getThisContext() {
        return this;
    }
}
