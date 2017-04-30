package tools.remote.lederman;

import android.app.Activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * Created by Georg on 09.04.2017.
 */

public class SendKeyTask extends ErrorHandlingAsyncTask<Integer, Void, Void>  {

    private Activity activity;
    private SocketAddress socketAddress;

    public SendKeyTask(Activity caller, SocketAddress socketAddress) {
        super();
        this.socketAddress = socketAddress;
        this.activity = caller;
    }
    public SendKeyTask(SocketAddress socketAddress) {
        super();
        this.socketAddress = socketAddress;
        //this.activity = caller;
    }

    @Override
    protected void onResult(Void result) {

    }

    @Override
    protected void onException(Exception e) {
        //TPM2ConnectionManager.getInstance().selectNewServer(this.activity);
    }

    @Override
    protected Void tryInBackground(Integer... params) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(1000);
        socket.connect(this.socketAddress);
        byte key = (byte) 0x00;
        key = (byte) params[0].byteValue();
        byte[] buff = {(byte)0xc9, (byte)0xc0, (byte)0x00, (byte)0x02, (byte)250, key, (byte)0x36};
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        packet.setSocketAddress(this.socketAddress);
        socket.send(packet);
        socket.close();
        return null;
    }
}