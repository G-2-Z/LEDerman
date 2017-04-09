package tools.remote.lederman;

import android.app.Activity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Georg on 01.04.2017.
 */

public class DeviceHeartbeatTask extends ErrorHandlingAsyncTask<SocketAddress, Void, Boolean>  {

    private Activity activity;

    public DeviceHeartbeatTask(Activity caller) {
        super();
        this.activity = caller;
    }

    @Override
    protected void onResult(Boolean result) {
        if (!result) {
            TPM2ConnectionManager.getInstance().selectNewServer(this.activity);
        }
    }

    @Override
    protected void onException(Exception e) {
        TPM2ConnectionManager.getInstance().selectNewServer(this.activity);
    }

    @Override
    protected Boolean tryInBackground(SocketAddress... params) throws Exception {
        //DatagramSocket socket = params[0];
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);
        socket.connect(params[0]);
        byte[] buff = {(byte)0xc9, (byte)0xc0, (byte)0x00, (byte)0x01, (byte)0xff, (byte)0x36};
        //if (!socket.isClosed() && socket.isConnected()){
            DatagramPacket packet = new DatagramPacket(buff, buff.length);


            packet.setSocketAddress(params[0]);
            socket.send(packet);

            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        //}
        //return false;
    }
}
