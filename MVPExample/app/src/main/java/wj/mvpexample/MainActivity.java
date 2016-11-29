package wj.mvpexample;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements MainPresenter.View{
    private MainPresenter mainPresenter;

    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenterImpl(MainActivity.this);
        mainPresenter.setView(this);

        confirmButton = (Button) findViewById(R.id.button);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mainPresenter.onConfirm();
            }
        });
    }

    @Override
    public void setConfirmText(String text) {
        confirmButton.setText(text);
    }
}
