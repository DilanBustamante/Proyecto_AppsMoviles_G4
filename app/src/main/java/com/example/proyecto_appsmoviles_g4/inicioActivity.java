package com.example.proyecto_appsmoviles_g4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;


public class inicioActivity extends AppCompatActivity {

    private ConstraintLayout root;
    private homeFrag homeFragment;
    private mapFrag mapFragment;
    private vetFrag vetFragment;
    private ProfileFragment profileFragment;
    private BottomNavigationView navigationView;



    private String key1glo = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        root = findViewById(R.id.rootPrueba);

        //Instancias
        homeFragment = homeFrag.newInstance();
        mapFragment = mapFrag.newInstance();
        vetFragment = vetFrag.newInstance();
        profileFragment = ProfileFragment.newInstance();




        //Referencias de elementos visibles
          navigationView =findViewById(R.id.navigator);


          //patron observer para enviar dirección
           mapFragment.setObserver(vetFragment);
           vetFragment.setStringkey(mapFragment);



           showFragment(homeFragment);

           this.ExtrasValidation();






        navigationView.setOnNavigationItemSelectedListener(
                (MenuItem) ->{
                    switch (MenuItem.getItemId()){
                        case R.id.homeid :
                              homeFrag home = new homeFrag();
                              this.showFragment(home);
                        break;

                        case R.id.map:
                            SharedPreferences sp3 = getSharedPreferences("mapOnlyview",MODE_PRIVATE);
                            sp3.edit().putString("key1","mapOnlyview").apply();
                            this.showFragment(mapFragment);
                        break;

                        case R.id.veterinariaid:
                            String key2 = getIntent().getStringExtra("key2");
                            this.showFragment(vetFragment);
                        break;

                        case R.id.messageid:
                        break;


                        case R.id.profileid:
                            this.showFragment(profileFragment);
                        break;
                    }

                    return true;
                }
        );




    }

    public void ExtrasValidation(){

        key1glo = getIntent().getStringExtra("key1");

        if(key1glo.equals("loginvet")){
            navigationView.getMenu().getItem(2).setVisible(true);

        }else if(key1glo.equals("loginuser")) {
            navigationView.getMenu().getItem(2).setVisible(false);
        }

    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment);
        transaction.commit();
    }


      public homeFrag getHomeFragment(){
        return homeFragment;
       }

      public mapFrag getmapFragment(){
        return mapFragment;
       }

      public vetFrag getvetFragment(){
        return vetFragment;
      }




}