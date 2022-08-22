package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.usage.UsageEvents;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity implements GestureDetector.OnGestureListener{


    public static BluetoothSocket btsc=null;

    Runnable runnable ;
    Handler handler =new Handler();
    TextView Reading;
    int timeDelay=1000;//time for reading delay
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        hook();
        updateAndDelete();
//        process();
    }

    private void hook() {
        Reading=(TextView) findViewById(R.id.Reading);
        gestureDetector=new GestureDetector(this,this);
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
                            updateAndDelete();
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

    public void updateAndDelete(){
        try {
            Call<List<ApiModel>> call = RetrofitClient.getService().getApiData();
            call.enqueue(new Callback<List<ApiModel>>() {
                @Override
                public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {
                    List<ApiModel> model = response.body();
                    for (int i = 0; i < model.size(); i++) {
                        DataModel dataModel=new DataModel();
                        dataModel.temp=model.get(i).getTemp();
                        dataModel.time=model.get(i).getTime();
                        try {
                            DatabaseClass.getDatabase(MainActivity2.this).getDao().insertData(dataModel);
                        }catch (Exception e){
                            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ApiModel>> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        float x1=0,y1=0,x2,y2;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1= event.getX();
                y1= event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2= event.getX();
                y2= event.getY();
                float floatX=x2-x1;
                float floatY=y2-y1;
                if(Math.abs(floatX)< 0.1f){
                    Toast.makeText(this, "Left Swipe", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity2.this,GraphActivity.class));
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}