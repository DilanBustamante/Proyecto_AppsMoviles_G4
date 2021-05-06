package com.example.proyecto_appsmoviles_g4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;


public class inicioActivity extends AppCompatActivity {


    private homeFrag homeFragment;
    private mapFrag mapFragment;
    private vetFrag vetFragment;
    private BottomNavigationView navigationView;
    private inicioActivity inicioActivity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Instancias
        homeFragment = homeFrag.newInstance();
        mapFragment = mapFrag.newInstance();
        vetFragment = vetFrag.newInstance();

        inicioActivity = new inicioActivity();


        //Referencias de elementos visibles
          navigationView =findViewById(R.id.navigator);



          //vista de items del navigation
          navigationView.getMenu().getItem(2).setVisible(true);




          //patron observer para enviar dirección
           mapFragment.setObserver(vetFragment);

           vetFragment.setStringkey(mapFragment);

           showFragment(homeFragment);





        navigationView.setOnNavigationItemSelectedListener(
                (MenuItem) ->{
                    switch (MenuItem.getItemId()){
                        case R.id.homeid :
                             this.showFragment(homeFragment);
                        break;

                        case R.id.map:
                            SharedPreferences sp = getSharedPreferences("mapOnlyview",MODE_PRIVATE);
                            sp.edit().putString("key1","mapOnlyview").apply();
                            this.showFragment(mapFragment);
                        break;

                        case R.id.veterinariaid:

                        this.showFragment(vetFragment);
                        break;

                        case R.id.messageid:
                        break;


                        case R.id.profileid:
                        break;
                    }

                    return true;
                }
        );




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