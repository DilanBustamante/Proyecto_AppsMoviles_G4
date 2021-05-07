package com.example.proyecto_appsmoviles_g4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText userName;
    private EditText userPassword;
    private Button logInButton;
    private TextView regiterButton;
    private Spinner spinner;

    private FirebaseFirestore db;
    private boolean resp;
    private boolean resp2;
    private String vetOrUser ="";

    private RegisterActivity registerActivity;
    private RegisterActivity2 registerActivity2;
    //private FormAppActivity formAppActivity;

    private inicioActivity iniActivity;
    private vetFrag vetFragment;




    public static final int  PERMISSIONS_CALLBACK = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resp2 = false;

        //Instancias de las actividades
        registerActivity = new RegisterActivity();
        registerActivity2 = new RegisterActivity2();

        iniActivity = new inicioActivity();
        vetFragment = iniActivity.getvetFragment();

       // formAppActivity = new FormAppActivity();


        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        logInButton = findViewById(R.id.logInButton);
        regiterButton = findViewById(R.id.RegisterButton);
        spinner = findViewById(R.id.spinner);

        String[] elements = {"Veterinaria","Dueño de mascota"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,elements);
        spinner.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();




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

                if(spinner.getSelectedItem().equals("Veterinaria")){
                    loginVet();


               }else if (spinner.getSelectedItem().equals("Dueño de mascota")){
                    loginUser();
               }
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


     public boolean loginVet(){

         //mirar si usuario existe
         db.collection("vet")
                 .whereEqualTo("name",userName.getText().toString())
                 .whereEqualTo("password",userPassword.getText().toString())
                 .get().addOnSuccessListener(

                 query -> {
                     if(query.getDocuments().size()==0){
                         resp = false;
                         Toast.makeText(this, "Usuario o contraseña invalidos por favor intente de nuevo", Toast.LENGTH_LONG).show();
                         userName.setText("");
                         userPassword.setText("");
                     }else{
                          resp = true;
                          Intent intent = new Intent(this, inicioActivity.class);
                          intent.putExtra("key1","loginvet");
                          intent.putExtra("key2",userName.getText().toString());
                         Log.e(">>>>>>>>>>","KEY 2 EN VET ES :"+userName.getText().toString());
                          startActivity(intent);

                     }
                 }
         );

        return resp;
 }



    public boolean loginUser(){

        //mirar si usuario existe
        db.collection("users")
                .whereEqualTo("name",userName.getText().toString())
                .whereEqualTo("password",userPassword.getText().toString())
                .get().addOnSuccessListener(

                query -> {
                    if(query.getDocuments().size()==0){
                        resp = false;
                        Toast.makeText(this, "Usuario o contraseña invalidos por favor intente de nuevo", Toast.LENGTH_LONG).show();
                        userName.setText("");
                        userPassword.setText("");
                    }else{
                        vetOrUser = "user" ;
                        resp = true;
                        Intent intent = new Intent(this, inicioActivity.class);
                        intent.putExtra("key1","loginuser");
                        intent.putExtra("key2",userName.getText().toString());
                        startActivity(intent);

                    }
                }
        );

        return resp;
    }




}

