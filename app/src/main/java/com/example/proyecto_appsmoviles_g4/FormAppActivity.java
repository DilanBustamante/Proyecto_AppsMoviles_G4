package com.example.proyecto_appsmoviles_g4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;

public class FormAppActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText nameForm;
    private EditText phoneForm;
    private EditText email;
    private EditText passwordForm;
    private EditText validatePassForm;
    private Button registerButton;

    public final static String VET_RESPONSE = "veterinaria";
    public final static String USER_RESPONSE = "user";

    private String response;
    private Bundle extras;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_app);

        nameForm = findViewById(R.id.nameForm);
        phoneForm = findViewById(R.id.phoneForm);
        email = findViewById(R.id.emailForm);
        passwordForm = findViewById(R.id.passwordForm);
        validatePassForm = findViewById(R.id.validatePassForm);
        registerButton = findViewById(R.id.buttonRegisterForm);

        db = FirebaseFirestore.getInstance();


        registerButton.setOnClickListener(this);

        extras = getIntent().getExtras();
        response = extras.getString("response");
        Log.e(">>>>>>", "RESPONSE ES :" + response);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonRegisterForm:

                String id =  UUID.randomUUID().toString();
                String name = nameForm.getText().toString();
                String phone = phoneForm.getText().toString();
                String emailU = email.getText().toString();
                String passw = passwordForm.getText().toString();
                String paswVal = validatePassForm.getText().toString();
                ArrayList<String> services = new ArrayList<>();
                ArrayList<String> photosPaths = new ArrayList<>();
                ArrayList<String> hours = new ArrayList<>();
                ArrayList<Comentarios> comments = new ArrayList<>();


                if (response.equals(VET_RESPONSE)) {
                    Vet vet = new Vet(id , name, "", phone, 0.0, 0.0, "", 0, "", emailU, passw, paswVal,services,photosPaths,hours,comments);
                    if (!name.equals("") && !phone.equals("") && !emailU.equals("") && passw.equals(paswVal)) {

                        this.RegisterVet(vet);

                    } else {
                        Toast.makeText(this, "Alguno de sus campos es vacio o la contraseña y su confirmacion no coinsiden", Toast.LENGTH_LONG).show();
                    }

                } else if (response.equals(USER_RESPONSE)) {

                    User user = new User(id,name, phone, "", passw, paswVal, emailU);
                    if (!name.equals("") && !phone.equals("") && !emailU.equals("") && passw.equals(paswVal)) {

                        this.RegisterUser(user);


                    } else {
                        Toast.makeText(this, "Alguno de sus campos es vacio o la contraseña y su confirmacion no coinsiden", Toast.LENGTH_LONG).show();
                    }
                }

                break;

        }
    }


    private void RegisterUser(User user) {

        db.collection("users")
                .whereEqualTo("email",user.getEmail())
                .get().addOnSuccessListener(
                        query -> {
                          if(query.getDocuments().size()==0){

                              db.collection("users").document(user.getId()).set(user);
                              Toast.makeText(this, "Usuario Dueño de mascota creado : ", Toast.LENGTH_LONG).show();
                              this.GoToLoginActivity();

                          }else{
                                //usuario ya registrado
                                  Toast.makeText(this, "Este email de Dueño ya ha sido utilizado, por favor cambielo", Toast.LENGTH_LONG).show();
                                   email.setText("");
                          }
                        }
        );


    }




    private void RegisterVet(Vet vet) {
        db.collection("vet")
                .whereEqualTo("email",vet.getEmail())
                .get().addOnSuccessListener(
                query -> {
                    if(query.getDocuments().size()==0){
                        Toast.makeText(this, "Veterinaria creada : " , Toast.LENGTH_LONG).show();
                        db.collection("vet").document(vet.getId()).set(vet);
                        this.GoToLoginActivity();

                    }else{
                        //usuario ya registrado
                        Toast.makeText(this, "Este email de veterinaria ya ha sido utilizado, por favor cambielo", Toast.LENGTH_LONG).show();
                        email.setText("");
                    }
                }
        );
    }




    private void GoToLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}