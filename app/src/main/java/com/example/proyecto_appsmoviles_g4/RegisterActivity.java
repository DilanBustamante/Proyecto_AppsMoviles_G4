package com.example.proyecto_appsmoviles_g4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private Button vetButton;
    private Button userButton;
    private Button continueButton;

    public final static String VET_RESPONSE ="veterinaria";
    public final static String USER_RESPONSE ="user";
    private String compare;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inicializar instancias

        vetButton=findViewById(R.id.vetButton);
        userButton=findViewById(R.id.userButton);
        continueButton=findViewById(R.id.continueButton);


        //bloquear boton de continuar
         continueButton.setEnabled(false);

        //enviar Onclick a los botones

        vetButton.setOnClickListener(this);
        userButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vetButton:

                continueButton.setEnabled(true);
                userButton.setEnabled(false);
                vetButton.setEnabled(false);
                compare = VET_RESPONSE;
                Log.e(">>>>>","La respuesta es :"+compare);

            break;

            case R.id.userButton:

                continueButton.setEnabled(true);
                userButton.setEnabled(false);
                vetButton.setEnabled(false);
                compare = USER_RESPONSE;
                Log.e(">>>>>","La respuesta es :"+compare);

            break;

            case R.id.continueButton:
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                if(compare.equals(USER_RESPONSE)){
                    intent.putExtra("response", USER_RESPONSE);

                }else if(compare.equals(VET_RESPONSE)){
                    intent.putExtra("response", VET_RESPONSE);
                }
                startActivity(intent);

            break;


        }
    }





}