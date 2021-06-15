package com.example.proyecto_appsmoviles_g4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class VetView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ConstraintLayout root;
    private Button watch;
    private TextView nameVet;
    private TextView vetStatus;
    private ProfileInfo profileFragment;
    private Context context;




    public VetView(ConstraintLayout root) {
        super(root);
        this.root = root;
        nameVet = root.findViewById(R.id.nameVetProfile);
        vetStatus = root.findViewById(R.id.statusVet);
        watch = root.findViewById(R.id.buttonWatch);

        watch.setOnClickListener(this);
        this.context = root.getContext();

    }




    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonWatch){
            Log.e(">>>>>>>>","el valor del vet es sisaaaaaaa");
            profileFragment = ProfileInfo.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("veterinariName",nameVet.getText().toString());
            Intent intent = new Intent(context, VetView.class);
            String user = intent.getStringExtra("usersend");
            bundle.putString("usernameProfile",user);
            profileFragment.setArguments(bundle);
            showFragment( profileFragment);
        }
    }



    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager =((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment);
        transaction.commit();
    }


    public ConstraintLayout getRoot() { return root; }

    public Button getWatch() {
        return watch;
    }

    public TextView getNameVet() {
        return nameVet;
    }

    public TextView getVetStatus() {
        return vetStatus;
    }


}
