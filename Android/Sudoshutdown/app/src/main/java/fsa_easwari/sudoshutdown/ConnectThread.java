package fsa_easwari.sudoshutdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectThread extends AsyncTask<Void, Void, Void> {
    private Socket socket = null;
    private Context context;
    private SharedPreferences preferences;
    private boolean sucess = false;
    private String message = "";

    public ConnectThread(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            final String HOST = preferences.getString(MainActivity.HOST_KEY, null);
            final int PORT = preferences.getInt(MainActivity.PORT_KEY, 0);
            socket = new Socket(HOST, PORT);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.print("SHUTDOWN");
            sucess = true;
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            message += e.getMessage();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (sucess) Toast.makeText(context, "Command sent Sucessfully", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, message != null ? message : "Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
