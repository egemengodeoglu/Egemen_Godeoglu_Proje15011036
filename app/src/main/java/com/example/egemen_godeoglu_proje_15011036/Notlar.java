package com.example.egemen_godeoglu_proje_15011036;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;


@Entity(tableName = "notes")
@TypeConverters(Converters.class)
public class Notlar implements Serializable {
    @PrimaryKey
    @NonNull
    private String baslik;
    @ColumnInfo(name ="not_zaman")
    private Calendar zaman;
    @ColumnInfo(name ="not_hatirlatici")
    private Calendar hatirlatici;
    @ColumnInfo(name ="not_renk")
    private int renk;
    @ColumnInfo(name ="not_oncelik")
    private String oncelik;
    @ColumnInfo(name ="not_resim")
    private String resim;
    @ColumnInfo(name ="text")
    private String text;


    public String getBaslik(){
        return baslik;
    }
    public void setBaslik(String baslik){
        this.baslik=baslik;
    }

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text=text;
    }

    public Calendar getZaman(){
        return zaman;
    }
    public void setZaman(Calendar zaman){
        this.zaman=zaman;
    }

    public Calendar getHatirlatici(){
        return hatirlatici;
    }
    public void setHatirlatici(Calendar hatirlatici){
        this.hatirlatici=hatirlatici;
    }

    public int getRenk(){
        return renk;
    }
    public void setRenk(int renk){
        this.renk=renk;
    }

    public String getOncelik(){
        return oncelik;
    }
    public void setOncelik(String oncelik){ this.oncelik=oncelik; }

    public String getResim(){
        return resim;
    }
    public void setResim(String resim){
        this.resim=resim;
    }



}
