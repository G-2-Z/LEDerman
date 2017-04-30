package tools.remote.lederman;

import android.app.Activity;
import android.content.Intent;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by Georg on 02.04.2017.
 */

public class TPM2ConnectionManager {

    private static TPM2ConnectionManager instance;
    private DatagramSocket socket;
    private SocketAddress address;

    public static TPM2ConnectionManager getInstance(){
        if (instance == null){
            instance = new TPM2ConnectionManager();
        }
        return instance;
    }

    private TPM2ConnectionManager(){
        super();
    }

    //public void createSocket(Activity caller, String serverIp){
     ////}
    //protected void setSocket(Activity caller, DatagramSocket socket){
      //  this.socket = socket;
       // issueHeatbeat(caller);
    //}
    public void setAddressPort(String address, int port,Activity caller){
        this.address = new InetSocketAddress(address,port);
        issueHeatbeat(caller);
    }
    protected void issueHeatbeat(Activity caller){
        new DeviceHeartbeatTask(caller).execute(this.address);
    }
    protected void selectNewServer(Activity caller){
        Intent startIntent = new Intent(caller, DeviceSelectActivity.class);
        caller.startActivity(startIntent);
    }
    public void setColor(int color){
        new SetColorTask(this.address).execute(color);
    }
    public void setMode(int mode){
        new SetModeTask(this.address).execute(mode);
    }
    public void sendKey(int key){
        new SendKeyTask(this.address).execute(key);
    }
}
