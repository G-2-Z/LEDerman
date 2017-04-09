package tools.remote.lederman;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Georg on 02.04.2017.
 */

public class CreateSocketTask extends AsyncTask<String,Void,DatagramSocket> {

    private Activity activity;


    public CreateSocketTask(Activity activity){
        super();
        this.activity = activity;
    }
    @Override
    protected void onPostExecute(DatagramSocket socket){
        //TPM2ConnectionManager.getInstance().setSocket(activity, socket);
    }

    @Override
    protected DatagramSocket doInBackground(String... params) {
        try {
            DatagramSocket socket = new DatagramSocket();

            try {
                InetAddress address = InetAddress.getByName(params[0]);
                socket.connect(address,65506);

            } catch (UnknownHostException e) {
                e.printStackTrace();
                return null;
            }
            //socket.setSoTimeout(1000);
            return socket;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //Log.d("CreateSocketTask","why");
        //return null;
    }
}
