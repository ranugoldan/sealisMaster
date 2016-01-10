package com.kelompok4.sealis.sealis;

import com.kelompok4.sealis.sealis.Info;

import java.io.Serializable;

/**
 * Created by Ranu on 16/12/2015.
 */
public class Wilayah implements Serializable {
    int id;
    String stasiun,nama;
    double lat,lon;
    Info info;
    Prediksi prediksi;


    public Wilayah(int id, String stasiun, String nama, double lat, double lon, Info info, Prediksi prediksi) {
        this.id = id;
        this.stasiun = stasiun;
        this.nama = nama;
        this.lat = lat;
        this.lon = lon;
        this.info = info;
        this.prediksi = prediksi;
    }
}

