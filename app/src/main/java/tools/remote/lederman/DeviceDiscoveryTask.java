package tools.remote.lederman;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Georg on 01.04.2017.
 */

public class DeviceDiscoveryTask extends ErrorHandlingAsyncTask<Void, Void, String> {

    public static final String SERVER = "tools.remotelederman.SERVER";
    private Activity activity;
    private static final byte[] message = new byte[] { (byte)0x15, 0x1e, (byte)0xd2 };

    public DeviceDiscoveryTask(Activity activity){
        super();
        this.activity = activity;
    }

    @Override
    protected void onResult(String result) {
        Intent intent = new Intent(activity, ControlCenterActivity.class);
        intent.putExtra(SERVER, result);
        activity.startActivity(intent);
    }

    @Override
    protected void onException(Exception e) {

    }

    @Override
    protected String tryInBackground(Void... params) throws IOException {
        InetAddress group = null;
        group = InetAddress.getByName("230.255.20.7");
        MulticastSocket s = null;
        s = new MulticastSocket(6789);
        s.setLoopbackMode(true);
        s.joinGroup(group);

        DatagramPacket hi = new DatagramPacket(message, message.length, group, 6789);

        s.send(hi);

        // get their responses!
        byte[] buf = new byte[1000];
        final DatagramPacket recv = new DatagramPacket(buf, buf.length);

        boolean found = false;

        while(!found) {
            s.receive(recv);
                if(recv.getLength() == 3 && recv.getData()[0] == (byte) 0x1a && recv.getData()[1] == (byte) 0x1e && recv.getData()[2] == (byte) 0xd1){
                    found=true;
                }
        }
        // OK, I'm done talking - leave the group...
        s.leaveGroup(group);

        s.close();
        return recv.getAddress().getHostAddress();
    }

}
