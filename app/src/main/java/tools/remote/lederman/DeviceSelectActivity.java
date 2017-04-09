package tools.remote.lederman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.InetAddress;

public class DeviceSelectActivity extends AppCompatActivity {
    public static final String SERVER = "tools.remotelederman.SERVER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_select);

        new DeviceDiscoveryTask(this).execute();

    }
}
