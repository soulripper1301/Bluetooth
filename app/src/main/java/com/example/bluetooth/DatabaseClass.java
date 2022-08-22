package com.example.bluetooth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {DataModel.class},version = 1)
public abstract class DatabaseClass extends RoomDatabase {

    public abstract DaoClass getDao();
    private static DatabaseClass instance;

    static DatabaseClass getDatabase(final Context context){
        if(instance==null){
            synchronized (DatabaseClass.class){
                instance= Room.databaseBuilder(context,DatabaseClass.class,"DRDO").allowMainThreadQueries().build();
            }
        }
        return instance;
    }

}
