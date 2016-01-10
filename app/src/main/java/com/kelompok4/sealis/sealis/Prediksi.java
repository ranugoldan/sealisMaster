package com.kelompok4.sealis.sealis;

import java.io.Serializable;

/**
 * Created by Ranu on 16/12/2015.
 */
public class Prediksi implements Serializable {
    String arahangin,kecangin,cuaca,tinggigel;

    public Prediksi(String arahangin, String kecangin, String cuaca, String tinggigel) {
        this.arahangin = arahangin;
        this.kecangin = kecangin;
        this.cuaca = cuaca;
        this.tinggigel = tinggigel;
    }
}
