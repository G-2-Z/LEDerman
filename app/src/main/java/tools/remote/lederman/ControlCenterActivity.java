package tools.remote.lederman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ControlCenterActivity extends AppCompatActivity implements OnColorChangedListener{

    public static final String SERVER = "tools.remotelederman.SERVER";
    public static final String SERVER_KEY = "last_server";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ControlCenterSectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private Handler handler;
    private DatagramSocket socket;
    private String serverIp;
    private SharedPreferences mPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_center);

        mPrefs = this.getPreferences(MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ControlCenterSectionsPagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        Intent intent = getIntent();
        serverIp = intent.getStringExtra(SERVER);

        if (serverIp != null) {
            TPM2ConnectionManager.getInstance().setAddressPort(serverIp,65506,this);
        } else {
            String lastServer = mPrefs.getString(SERVER_KEY, null);
            if (lastServer != null) {
                serverIp = lastServer;
                TPM2ConnectionManager.getInstance().setAddressPort(lastServer,65506,this);
            } else {
                TPM2ConnectionManager.getInstance().selectNewServer(this);
            }
        }


        if (serverIp != null) {
            Snackbar.make(toolbar, "Selected " + serverIp, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString(SERVER_KEY, serverIp);
        ed.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString(SERVER_KEY, serverIp);

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_control_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void colorChanged(int color) {
        TPM2ConnectionManager.getInstance().setColor(color);
    }


    public class ControlCenterSectionsPagerAdapter extends FragmentPagerAdapter {

        private ControlCenterActivity activity;

        public ControlCenterSectionsPagerAdapter(FragmentManager fm,ControlCenterActivity activity) {
            super(fm);
            this.activity = activity;
        }

        @Override
        public Fragment getItem(int position) {
            return ColorPickerFragment.newInstance(this.activity);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
