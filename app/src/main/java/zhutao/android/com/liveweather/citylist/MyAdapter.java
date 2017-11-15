package zhutao.android.com.liveweather.citylist;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import zhutao.android.com.liveweather.R;
import zhutao.android.com.liveweather.databinding.ItemCityListBinding;
import zhutao.android.com.liveweather.model_each_time.SixWeatherPresenter;

/**
 * Created by Administrator on 2017/11/13.
 */

public class MyAdapter extends RecyclerView.Adapter<MyVHolder> {
    private List<CityWeatherBean> data;
    private Activity activity;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(List<CityWeatherBean> data, Activity activity, OnItemClickListener clickListener) {
        this.data = data;
        this.activity = activity;
        this.onItemClickListener = clickListener;
    }

    @Override
    public MyVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCityListBinding mainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_city_list, null, false);
        return new MyVHolder(mainBinding.getRoot(), parent.getContext());
    }

    @Override
    public void onBindViewHolder(MyVHolder holder, final int position) {
        ItemCityListBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
        viewDataBinding.setData(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(position);
            }
        });
        ImageView imageView = viewDataBinding.getRoot().findViewById(R.id.img_weather);
        imageView.setImageDrawable(holder.context.getResources().getDrawable(SixWeatherPresenter.icDrawable(data.get(position).getWeather())));
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    interface OnItemClickListener {
        public void onClick(int position);
    }
}
