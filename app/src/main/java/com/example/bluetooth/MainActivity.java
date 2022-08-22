package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static final UUID dUUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String device="00:22:04:00:28:D0";//ardunio device address like  : 00:21:13:02:B6:5B
    Button btn_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hook();
//        process(); //  comment out for automation
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  //         process();//comment out to enable manual connection
                startActivity(new Intent(MainActivity.this,MainActivity2.class)); //comment out for going directly to go to 2nd page
            }
        });
    }

    private void process() {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        BluetoothDevice arduino=btAdapter.getRemoteDevice(device);
        Toast.makeText(this,"Connected device" +arduino.getName(), Toast.LENGTH_SHORT).show();
        try {
            do {
                MainActivity2.btsc = arduino.createRfcommSocketToServiceRecord(dUUID);
                MainActivity2.btsc.connect();
            }while(!MainActivity2.btsc.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(MainActivity.this,MainActivity2.class));
    }


    private void hook() {
        btn_1=(Button) findViewById(R.id.btn_1);
    }
}