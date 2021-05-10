package com.example.proyecto_appsmoviles_g4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ServicesVetRow extends RecyclerView.ViewHolder implements View.OnClickListener{


    private ConstraintLayout root;
    private EditText description;
    private EditText price;
    private Button edit;
    private Button accept;
    private FirebaseFirestore db;
    ArrayList<String> services;

    private Activity act = new Activity();


    public final static int REQUEST_CODE = 11;
    public final static int VALUE = 1;


    private vetFrag vetFragment ;

    public ServicesVetRow(ConstraintLayout root) {
        super(root);

        this.root = root;
        description = root.findViewById(R.id.textService);
        price = root.findViewById(R.id.textServiceprice);
        edit = root.findViewById(R.id.buttonEditService);
        accept = root.findViewById(R.id.buttonAccept);

        services = new ArrayList<>();

        edit.setOnClickListener(this);
        accept.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();


        vetFragment = vetFrag.newInstance();

        description.setEnabled(false);
        price.setEnabled(false);

    }


    public ConstraintLayout getRoot() {
        return root;
    }

    public void setRoot(ConstraintLayout root) {
        this.root = root;
    }

    public EditText getDescription() {
        return description;
    }

    public void setDescription(EditText description) {
        this.description = description;
    }

    public EditText getPrice() {
        return price;
    }

    public void setPrice(EditText price) {
        this.price = price;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Button getAccept() {
        return accept;
    }

    public void setAccept(Button accept) {
        this.accept = accept;
    }





    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonEditService){

            description.setEnabled(true);
            price.setEnabled(true);

        }else if(v.getId()== R.id.buttonAccept){

            String service = description.getText().toString()+","+price.getText().toString();
            Log.e(">>>>>>>>>>>","LO QUE TIENE SERVICE EN EL ROW ES: "+service);


            Context context = v.getContext();
            SharedPreferences sp = context.getSharedPreferences("service",Context.MODE_PRIVATE);
            sp.edit().putString("serviceKey",service).apply();


            description.setEnabled(false);
            price.setEnabled(false
            );
        }


    }






}
