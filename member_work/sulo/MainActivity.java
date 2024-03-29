package com.whileloop.walkmode_p1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private int c;
    private Button btnRefresh;
    private TextView txtData;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private static String address = "20:15:12:04:25:83";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private Thread workerThread;

    //IBINDER
    boolean mBound = false;


    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    //////////////////


    //////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //msgbox("Hellow!");
        c = 1;

        init();


    }

   void init() {
        txtData = (TextView) findViewById(R.id.txtData);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btnRefresh = (Button) findViewById(R.id.btnRefresh);

        if (btAdapter == null) {
            msgbox("You don't fucking have bluetooth broh!");
        } else {
            if (btAdapter.isEnabled()) {
                msgbox("Bluetooth  Activated!");

            } else {
                msgbox("Bluetooth  is deactivated!");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }

        }
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    findBT();
                    openBT();
                } catch (IOException ex) {
                    msgbox(ex.getMessage() + 3);
                }
                msgbox("Testing " + c);
                c++;
            }
        });
    }

    void findBT() {

        try {
            Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getAddress().equals(address)) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            txtData.setText("Bluetooth device found." + mmDevice.getName());

        }catch(Exception e){
            msgbox(e.getMessage()+2);
        }
    }

    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            //msgbox("PASS");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
            //msgbox("PASS");
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            txtData.setText("Bluetooth Opened");

        } catch (Exception e) {
            msgbox(e.getMessage()+100000);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Enable the fucking bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    void msgbox(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }
    int getdata(String ss){
        String[] str = ss.trim().split(";");
        int[] int_val = new int[str.length];
        for(int i=0;i<str.length;i++){
            int_val[i] = Integer.parseInt(str[i]);
        }
        return int_val[1];
    }


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/*
 * after opening a connection to bluetooth printer device,
 * we have to listen and check if a data were sent to be printed.
 */
void beginListenForData() {
    try {
        final Handler handler = new Handler();

        // this is the ASCII code for a newline character
        final byte delimiter = 10;

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        workerThread = new Thread(new Runnable() {
            public void run() {

                while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                    try {

                        int bytesAvailable = mmInputStream.available();

                        if (bytesAvailable > 0) {

                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);

                            for (int i = 0; i < bytesAvailable; i++) {

                                byte b = packetBytes[i];
                                if (b == delimiter) {

                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(
                                            readBuffer, 0,
                                            encodedBytes, 0,
                                            encodedBytes.length
                                    );

                                    // specify US-ASCII encoding
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    // tell the user data were sent to bluetooth printer device
                                    handler.post(new Runnable() {
                                        public void run() {
                                            int x;
                                            //x = getdata(data);
                                            txtData.setText(data);
                                            //skPosition.setProgress(x);
                                        }
                                    });

                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }

                    } catch (IOException ex) {
                        stopWorker = true;
                    }

                }
            }
        });

        workerThread.start();

    } catch (Exception e) {
        msgbox(e.getMessage()+4);
    }
}
}