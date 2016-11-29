package wj.mvpexample;

/**
 * Created by user on 2016-10-04.
 */

public interface MainPresenter {

    void setView(MainPresenter.View view);

    void onConfirm();

    interface View {
        void setConfirmText(String text);
    }

}
