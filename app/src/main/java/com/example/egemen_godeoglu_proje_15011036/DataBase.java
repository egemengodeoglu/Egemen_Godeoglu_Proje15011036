package com.example.egemen_godeoglu_proje_15011036;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Notlar.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract MyDao myDao();
}
