package zhutao.android.com.liveweather.citylist;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import zhutao.android.com.liveweather.R;
import zhutao.android.com.liveweather.databinding.ItemCityListBinding;

/**
 * Created by Administrator on 2017/11/13.
 */

public class MyAdapter extends RecyclerView.Adapter<MyVHolder> {
    private List<CityWeatherBean> data;
    private Activity activity;

    public MyAdapter(List<CityWeatherBean> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public MyVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCityListBinding mainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_city_list, null, false);
        return new MyVHolder(mainBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(MyVHolder holder, int position) {
        ItemCityListBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
        viewDataBinding.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
