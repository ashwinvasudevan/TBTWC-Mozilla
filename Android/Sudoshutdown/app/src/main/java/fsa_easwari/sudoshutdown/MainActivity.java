package fsa_easwari.sudoshutdown;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public static final String HOST_KEY = "fsa_easwari.sudoshutdown.HOST";
    public static final String PORT_KEY = "fsa_easwari.sudoshutdown.PORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Retreving previous Host/Port setting
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String host = preferences.getString(HOST_KEY, "");
        final int port = preferences.getInt(PORT_KEY, -1);

        final EditText hostField = (EditText) findViewById(R.id.etHost);
        final EditText portField = (EditText) findViewById(R.id.etPort);
        if (host != null && port != -1) {
            hostField.setText(host);
            portField.setText("" + port);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tHost = "", tPort = "";
                tHost = hostField.getText().toString();
                tPort = portField.getText().toString();
                if (tHost.isEmpty() || tPort.isEmpty())
                    Snackbar.make(view, "Configure Host IP & Port Properly", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    new ConnectThread(MainActivity.this).execute();

                    //updating previous Host/Port setting
                    if (!host.equals(tHost)) preferences.edit().putString(HOST_KEY, tHost).commit();
                    if (port != Integer.parseInt(tPort))
                        preferences.edit().putInt(PORT_KEY, Integer.parseInt(tPort)).commit();
                }
            }
        });
    }
}
