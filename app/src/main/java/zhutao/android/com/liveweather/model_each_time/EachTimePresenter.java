package zhutao.android.com.liveweather.model_each_time;

import zhutao.android.com.liveweather.Network;
import zhutao.android.com.liveweather.base.BasePresenter;
import zhutao.android.com.liveweather.base.ICallback;

/**
 * Created by Administrator on 2017/10/27.
 */

public class EachTimePresenter extends BasePresenter<EachTimeView> {

    public void getData(String latLonStr) {
        if (isViewAttached()) {
            getView().showProgress();
        }

        //location key lang unit
        String param = Network.EACH_TIME_API + "location=" + latLonStr + "&key=" + Network.API_KEY + "&unit=m";
        EachTimeModel model = new EachTimeModel();
        model.params(param).execute(new ICallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (isViewAttached()) {
                    getView().getThisContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().hideProgress();
                            getView().showData(result);
                        }
                    });
                }
            }

            @Override
            public void onFailture(final String msg) {
                if (isViewAttached()) {
                    getView().getThisContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().hideProgress();
                            getView().showMsg(msg);
                        }
                    });
                }
            }
        });

    }
}
