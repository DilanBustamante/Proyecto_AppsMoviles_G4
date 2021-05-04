package com.example.proyecto_appsmoviles_g4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener {


    private Button googleButton;
    private Button facebookButton;
    private Button appButton;

    private String response;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);



        extras = getIntent().getExtras();
        response = extras.getString("response");
        Log.e(">>>>>>","RESPONSE ES :"+response);



        //Instanciar objetos visibles

        googleButton = findViewById(R.id.buttonGoogle);
        facebookButton = findViewById(R.id.buttonFacebook);
        appButton = findViewById(R.id.AppButton);


        //enviar onclik a botones
        googleButton.setOnClickListener(this);
        facebookButton.setOnClickListener(this);
        appButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGoogle:
            break;

            case R.id.buttonFacebook:
            break;

            case R.id.AppButton:
                Intent intent = new Intent(this, FormAppActivity.class);
                intent.putExtra("response", response);
                startActivity(intent);
            break;


        }
    }



}