package com.example.proyecto_appsmoviles_g4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class homeFrag extends Fragment implements SearchView.OnQueryTextListener{


    private SearchView search;
    private RecyclerView listVet;

    private VetAdapter vetAdapter;
    private FirebaseFirestore db;


    public homeFrag() {

    }


    public static homeFrag newInstance() {
        homeFrag fragment = new homeFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        search = root.findViewById(R.id.search);
        listVet = root.findViewById(R.id.ListVet);

        vetAdapter = new VetAdapter();

        listVet.setAdapter(vetAdapter);
        listVet.setLayoutManager(new LinearLayoutManager(this.getContext()));

        db = FirebaseFirestore.getInstance();
        this.getData("*");

        this.initListener();

        //scrolling event
        listVet.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {

                //TOP
                if(!recyclerView.canScrollVertically(1) && dy<0){

                }

                //BUTTON
                if(!recyclerView.canScrollVertically(1) && dy>0){
                    //Cargar las sgts 4 veterinarias
                    getData(vetAdapter.getLastVet().getName());

                }

            }
        });


        return root;
    }




    public void getData(String inicio){
        db.collection("vet")
                .orderBy("name")
                .limit(4)
                .startAfter(inicio)
                .get().addOnSuccessListener(
                command -> {
                    //Respuesta de la BD
                    for(DocumentSnapshot doc : command.getDocuments()){
                        // doc -> vet
                        Vet vet = doc.toObject(Vet.class);
                        vetAdapter.addVet(vet);

                    }
                }
        );
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.equals("")){
            vetAdapter.filer(newText);
        }else{
            vetAdapter.clear();
            this.getData("*");
        }
        return false;
    }


    private void initListener(){
        search.setOnQueryTextListener(this);
    }



}