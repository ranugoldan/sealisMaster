package com.kelompok4.sealis.sealis;

/**
 * Created by Ranu on 17/12/2015.
 */
public class Stasiun {
    private int id;
    private String nama;

    public Stasiun(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return this.getNama();
    }
}
