package com.kelompok4.sealis.sealis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InfoFragment extends Fragment {
    Wilayah wil;
    TextView wilayah,cuaca,tinggi,arah,kec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        wilayah = (TextView)rootView.findViewById(R.id.txtWilayah);
        cuaca = (TextView)rootView.findViewById(R.id.txtCuaca);
        tinggi = (TextView)rootView.findViewById(R.id.txtTinggi);
        arah = (TextView)rootView.findViewById(R.id.txtArah);
        kec = (TextView)rootView.findViewById(R.id.txtKec);
        wil = ((InfoPrediksiActivity)getActivity()).getWil();
        wilayah.setText(wil.nama);
        cuaca.setText(wil.info.cuaca);
        tinggi.setText(wil.info.tinggigel);
        arah.setText(wil.info.arahangin);
        kec.setText(wil.info.kecangin);
        // Inflate the layout for this fragment
        return rootView;

    }
}
