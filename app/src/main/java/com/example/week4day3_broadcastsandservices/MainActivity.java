package com.example.week4day3_broadcastsandservices;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // a list of intent filters that handle various system actions. When a system setting
    // has been changed (like toggling airplane mode, wifi, etc), it will correspond to the appropriate
    // filter which will then call the corresponding Broadcast Receiver
    IntentFilter airplaneFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
    IntentFilter wifiFilter = new IntentFilter("android.net.wifi.STATE_CHANGE");
    IntentFilter bluetoothFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    IntentFilter headsetFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
    IntentFilter nfcFilter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RECEIVER FOR AIRPLANE MODE
        BroadcastReceiver airplaneModeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Settings.System.getInt(context.getContentResolver(),
                        Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
                    Toast.makeText(context, "Airplane Mode On", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(context, "Airplane Mode Off", Toast.LENGTH_LONG).show();
            }
        };

        // RECEIVER FOR WIFI
        BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Settings.System.getInt(context.getContentResolver(),
                        Settings.Global.WIFI_ON, 0) != 0) {
                    Toast.makeText(context, "Wifi On", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Wifi Off", Toast.LENGTH_LONG).show();
                }
            }
        };

        // RECEIVER FOR BLUETOOTH
        BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Settings.System.getInt(context.getContentResolver(),
                        Settings.Global.BLUETOOTH_ON, 0) != 0) {
                    Toast.makeText(context, "Bluetooth on", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Bluetooth off", Toast.LENGTH_LONG).show();
                }
            }
        };

        // RECEIVER TO HANDLE HEADSETS
        BroadcastReceiver headsetReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                    int state = intent.getIntExtra("state", -1);
                    switch (state) {
                        case 0:
                            Toast.makeText(context, "Headphones unplugged", Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(context, "Headphones plugged", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        };

        // RECEIVER FOR NFC
        BroadcastReceiver nfcReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(intent.getAction())) {
                    if(intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, 0)
                            == NfcAdapter.STATE_ON) {
                        Toast.makeText(context, "NFC ON", Toast.LENGTH_LONG).show();
                    }
                    if(intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, 1)
                            == NfcAdapter.STATE_OFF) {
                        Toast.makeText(context, "NFC OFF", Toast.LENGTH_LONG).show();
                    } // end if
                }
            }
        };

        // register each receiver
        getApplicationContext().registerReceiver(airplaneModeReceiver, airplaneFilter);
        getApplicationContext().registerReceiver(wifiReceiver, wifiFilter);
        getApplicationContext().registerReceiver(bluetoothReceiver, bluetoothFilter);
        getApplicationContext().registerReceiver(headsetReceiver, headsetFilter);
        getApplicationContext().registerReceiver(nfcReceiver, nfcFilter);
    }
}

