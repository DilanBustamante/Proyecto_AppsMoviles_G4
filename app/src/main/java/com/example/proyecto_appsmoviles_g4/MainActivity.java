package com.example.proyecto_appsmoviles_g4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText userName;
    private EditText userPassword;
    private Button logInButton;
    private TextView regiterButton;




    private RegisterActivity registerActivity;
    private RegisterActivity2 registerActivity2;
    //private FormAppActivity formAppActivity;



    public static final int  PERMISSIONS_CALLBACK = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Instancias de las actividades
        registerActivity = new RegisterActivity();
        registerActivity2 = new RegisterActivity2();
       // formAppActivity = new FormAppActivity();


        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        logInButton = findViewById(R.id.logInButton);
        regiterButton = findViewById(R.id.RegisterButton);




        logInButton.setOnClickListener(this);
        regiterButton.setOnClickListener(this);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        },PERMISSIONS_CALLBACK);



    }


    @Override
    public void onClick(View v) {

     switch (v.getId()) {
            case R.id.logInButton:
                Intent intent = new Intent(this, inicioActivity.class);
                startActivity(intent);
             break;
         case R.id.RegisterButton:
             Intent intent2 = new Intent(this, RegisterActivity.class);
             startActivity(intent2);
         break;

        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSIONS_CALLBACK){
            boolean allGrant = true ;
            for (int i = 0 ; i<grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    allGrant = false ;
                    break;
                }
            }
            if(allGrant){
                Toast.makeText(this,"Todos los permisos concedidos",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Ojo, no todos los permisos fueron concedidos",Toast.LENGTH_LONG).show();
            }
        }
    }





}