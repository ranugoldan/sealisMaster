package com.kelompok4.sealis.sealis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PrediksiFragment extends Fragment {
    Wilayah wil;
    TextView wilayahpre,cuacapre,tinggipre,arahpre,kecpre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prediksi, container, false);
        wilayahpre = (TextView)rootView.findViewById(R.id.txtWilayah);
        cuacapre = (TextView)rootView.findViewById(R.id.txtCuaca);
        tinggipre = (TextView)rootView.findViewById(R.id.txtTinggi);
        arahpre = (TextView)rootView.findViewById(R.id.txtArah);
        kecpre = (TextView)rootView.findViewById(R.id.txtKec);
        wil = ((InfoPrediksiActivity)getActivity()).getWil();
        wilayahpre.setText(wil.nama);
        cuacapre.setText(wil.prediksi.cuaca);
        tinggipre.setText(wil.prediksi.tinggigel);
        arahpre.setText(wil.prediksi.arahangin);
        kecpre.setText(wil.prediksi.kecangin);
        // Inflate the layout for this fragment
        return rootView;
    }

}
