package neighbor.com.test123;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {

    private Button emergencyButton;
    private static int emergencyCode=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        final String[] option = new String[]{"버스사고", "버스고장", "도로사고", "긴급상황발생"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, option);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Emergency!!!!!");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dia, final int position) { // TODO Auto-generated method stub } }); final AlertDialog dialog = builder.create(); button = (Button) findViewById(R.id.buttonMeenu); button.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { dialog.show(); } });
                emergencyCode = position;

                android.app.AlertDialog.Builder alt_bld = new android.app.AlertDialog.Builder(NextActivity.this);
                alt_bld.setMessage("신고하겠습니까?").setCancelable(
                        false).setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), emergencyCode+"", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                android.app.AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("Change");
                // Icon for AlertDialog
                alert.show();
            }
        });
        final AlertDialog dialog = builder.create();
        emergencyButton = (Button) findViewById(R.id.emergencyBtn);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

    }
}
