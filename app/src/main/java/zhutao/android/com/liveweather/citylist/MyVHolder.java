package zhutao.android.com.liveweather.citylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/11/13.
 */

public class MyVHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public Context context;

    public MyVHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
    }
}
