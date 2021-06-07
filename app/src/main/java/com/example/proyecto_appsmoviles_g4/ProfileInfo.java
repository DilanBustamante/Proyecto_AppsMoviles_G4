package com.example.proyecto_appsmoviles_g4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileInfo extends Fragment {

    private FirebaseFirestore db;
    private User user;
    private TextView nameVet;

    public ProfileInfo() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileInfo newInstance() {
        ProfileInfo fragment = new ProfileInfo();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);

        String name = this.getArguments().getString("veterinariName");
        Log.e(">>>>>>>>","el valor del vet es sisaaaaaaa"+name);
        getData(name);
    }

    public void getData(String name){
        db.collection("vet").whereEqualTo("name",name)
                .get().addOnSuccessListener(
                query -> {
                    nameVet.setText(query.getDocuments().get(0).get("name").toString());
                    Log.e(">>>>>>>>>>","SI TRAJO EL DOCUMENTO"+query.getDocuments());
                }
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_info, container, false);
        nameVet = root.findViewById(R.id.nameVetProfile);
        return root;
    }
}