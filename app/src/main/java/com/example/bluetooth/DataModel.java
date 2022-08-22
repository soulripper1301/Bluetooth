package com.example.bluetooth;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DRDO")
public class DataModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int key;

    @NonNull
    float temp;

    @NonNull
    int time;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
