package zhutao.android.com.telephone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取手机号码
        tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        startActivity(new Intent(this, Main2Activity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        String deviceid = tm.getDeviceId();//获取智能设备唯一编号
        String te1 = tm.getLine1Number();//获取本机号码
        String imei = tm.getSimSerialNumber();//获得SIM卡的序号
        String imsi = tm.getSubscriberId();//得到用户Id
        String phone = tm.getLine1Number();


        Log.e(this.getClass().getName(), "phone:" + phone);
        Log.e(this.getClass().getName(), "sim:" + imei);
        Log.e(this.getClass().getName(), "a:" + deviceid);
        Log.e(this.getClass().getName(), "a count:" + tm.getPhoneCount());
        if (tm.getPhoneCount() > 0) {
            for (int slot = 0; slot < tm.getPhoneCount(); slot++) {
                String a = tm.getDeviceId(slot);
                Log.e(this.getClass().getName(), "a:" + slot + "--" + a);
            }
        }

        TextView textView = findViewById(R.id.text);
        textView.setText("imsi:" + deviceid + ",sim:" + imei);
    }
}
