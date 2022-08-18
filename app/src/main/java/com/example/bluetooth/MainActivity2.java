package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {


    public static BluetoothSocket btsc=null;

    Runnable runnable ;
    Handler handler =new Handler();
    TextView Reading;
    int timeDelay=1000;//time for reading delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        hook();
        process();
    }

    private void hook() {
        Reading=(TextView) findViewById(R.id.Reading);
    }

    private void process() {
        final InputStream[] inputStream = {null};
        try {
            for(int i=0;i!=1;){
                BluetoothSocket finalBtsc = btsc;
                handler.postDelayed(runnable = new Runnable() {
                    public void run() {
                        handler.postDelayed(runnable, timeDelay);

                        try {
                            inputStream[0] = finalBtsc.getInputStream();
                            inputStream[0].skip(inputStream[0].available());
                            byte b=(byte) inputStream[0].read();
                            Reading.setText(String.valueOf(b));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, timeDelay);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}