package zhutao.android.com.liveweather.base;

/**
 * Created by Administrator on 2017/10/27.
 */

public class BasePresenter<T extends IModelView> {
    private T view;

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public boolean isViewAttached() {
        return this.view != null;
    }

    public T getView() {
        return view;
    }
}
