package com.example.proyecto_appsmoviles_g4;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class VetView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ConstraintLayout root;
    private Button watch;
    private TextView nameVet;
    private TextView vetStatus;



    public VetView(ConstraintLayout root) {
        super(root);
        this.root = root;
        nameVet = root.findViewById(R.id.nameVet);
        vetStatus = root.findViewById(R.id.statusVet);
        watch = root.findViewById(R.id.buttonWatch);

        watch.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonWatch){

        }
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
