package com.example.proyecto_appsmoviles_g4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class vetFrag extends Fragment implements View.OnClickListener, mapFrag.OnNewAddressListener{

    private TextView nameVet;
    private ImageView image;
    private Button map;
    private TextView phoneNumber;
    private Button editInformation;
    private TextView AddresVetxt;
    private String addressGlo;


    public vetFrag() {
        // Required empty public constructor
        addressGlo = "";
    }


    public static vetFrag newInstance() {
        vetFrag fragment = new vetFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_vet, container, false);


      //Instancias los elementos visibles

        image = root.findViewById(R.id.imageUser);
        map = root.findViewById(R.id.buttonAddPlace);
        phoneNumber = root.findViewById(R.id.userPhoneTxt);
        editInformation = root.findViewById(R.id.buttonInformation);
        nameVet = root.findViewById(R.id.nameVetTxt);
        AddresVetxt = root.findViewById(R.id.vetAddress);

        //Colocar OnclickListener a los botones

        map.setOnClickListener(this);
        editInformation.setOnClickListener(this);


        if(addressGlo != null){
            AddresVetxt.setText(addressGlo);
        }


        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddPlace:
                Toast.makeText(getActivity(), "Agrega la ubicación de tu veterinaria", Toast.LENGTH_LONG).show();
                inicioActivity activity = (inicioActivity)getActivity();
                activity.showFragment(activity.getmapFragment());

            break;

            case R.id.buttonInformation:
                Toast.makeText(getActivity(), "BOTON FUNCIONANDO", Toast.LENGTH_LONG).show();
            break;


        }
    }

    @Override
    public void onNewAddress(String address) {
       this.addressGlo = address;
    }


}