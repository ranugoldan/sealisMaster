package com.kelompok4.sealis.sealis;

import java.io.Serializable;

public class Info implements Serializable{
    String arahangin,kecangin,cuaca,tinggigel;

    public Info(String arahangin, String kecangin, String cuaca, String tinggigel) {
        this.arahangin = arahangin;
        this.kecangin = kecangin;
        this.cuaca = cuaca;
        this.tinggigel = tinggigel;
    }
}
