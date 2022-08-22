package com.example.bluetooth;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface DaoClass {

    @Insert
    void insertData(DataModel model);
}
