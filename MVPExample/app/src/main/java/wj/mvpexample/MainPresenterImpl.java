package wj.mvpexample;

import android.app.Activity;

/**
 * Created by user on 2016-10-04.
 */

public class MainPresenterImpl implements MainPresenter {

    private Activity activity;
    private MainPresenter.View view;
    private MainModel mainModel;

    public MainPresenterImpl(Activity activity) {
        this.activity = activity;
        this.mainModel = new MainModel();
    }

    @Override
    public void setView(MainPresenter.View view) {
        this.view = view;
    }

    @Override
    public void onConfirm() {
        if (view != null) {
            view.setConfirmText(mainModel.getClickedText());
        }
    }


}