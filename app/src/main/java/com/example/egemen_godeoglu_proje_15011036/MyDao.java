package com.example.egemen_godeoglu_proje_15011036;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    public void insertNot(Notlar not);

    @Query("select * from notes")
    public List<Notlar> getNotlar();

    @Delete
    public void deleteNot(Notlar not);

    @Update
    public void updateNot(Notlar not);
}
