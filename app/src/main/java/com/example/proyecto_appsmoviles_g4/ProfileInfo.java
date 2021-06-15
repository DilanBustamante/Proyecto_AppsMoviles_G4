package com.example.proyecto_appsmoviles_g4;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileInfo extends Fragment implements View.OnClickListener {

    private FirebaseFirestore db;
    private User user;
    private TextView nameVet;
    private TextView horariosProfile;
    private TextView pricesServices;
    private TextView servicesName;
    private ImageView imageProfile;
    private Button commentButton;
    private EditText commentText;
    private RecyclerView recyclerComments;
    private LinearLayoutManager layoutComments;
    private CommentAdapter commentAdapter;
    String userName;
    String name;

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
        String userName = this.getArguments().getString("usersend");
        name = this.getArguments().getString("veterinariName");
        Log.e(">>>>>>>>","el valor del vet es sisaaaaaaa"+userName);
        getData(name);
    }

    public void getData(String name){
        db.collection("vet").whereEqualTo("name",name)
                .get().addOnSuccessListener(
                query -> {
                    //Retornamos los comentaros de la veterinaria

                    Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                    commentAdapter.setComments(updateVet.getComments());

                    nameVet.setText(query.getDocuments().get(0).get("name").toString());

                   /* if(!query.getDocuments().get(0).get("pathImage").toString().equals("")){
                        Uri uri = Uri.parse(query.getDocuments().get(0).get("pathImage").toString());
                        Glide.with(getContext()).load(uri.toString()).into(imageProfile);
                    }*/
                     query.getDocuments().get(0).get("hours");
                     ArrayList array = (ArrayList) query.getDocuments().get(0).get("hours");
                    ArrayList arrayServices = (ArrayList) query.getDocuments().get(0).get("services");
                     String horarios = "";
                    for (int i = 0; i <array.size() ; i++) {
                        Log.e(">>>>>>>>>>",array.get(i).toString());
                        horarios += array.get(i).toString()+"\n";
                    }
                    String prices ="";
                    String services = "";

                    for (int i = 0; i <arrayServices.size() ; i++) {
                        Log.e(">>>>>>>>>>",arrayServices.get(i).toString());
                        String[] servicesArray = arrayServices.get(i).toString().split(" ");
                        String optional ="";
                        for (int j = 0; j <servicesArray.length ; j++) {
                            if(j==servicesArray.length-1){
                                prices += servicesArray[j]+"\n";

                            }else{
                                optional+=servicesArray[j]+" ";
                            }
                        }
                        Log.e(">>>>>>>>>>",prices);
                        services+=optional+"\n";
                    }
                    servicesName.setText(services);
                    pricesServices.setText(prices);
                    horariosProfile.setText(horarios);
                    Log.e(">>>>>>>>>>",query.getDocuments().get(0).get("hours").getClass().getName());

                    if(!query.getDocuments().get(0).get("pathImage").toString().equals("")){
                        Uri uri = Uri.parse(query.getDocuments().get(0).get("pathImage").toString());
                        Log.println(Log.DEBUG,"PHOTO",uri.toString());
                        Glide.with(getContext()).load(uri.toString()).into(imageProfile);
                    }
                    //Log.e(">>>>>>>>>>","SI TRAJO EL DOCUMENTO"+query.getDocuments());
                }
        );
    }

    public void addComments(String userName, String message){

        db.collection("vet").whereEqualTo("name", name).get().addOnSuccessListener(
                query -> {
                    if (query.getDocuments().size() > 0) {
                        Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                        Comentarios coment = new Comentarios(userName,"08/10/2021",message);
                        updateVet.getComments().add(coment);
                        updateVet.setComments(updateVet.getComments());
                        db.collection("vet").document(updateVet.getId()).set(updateVet);
                        Toast.makeText(this.getActivity(),"Se ha añadido un comentario",Toast.LENGTH_LONG);

                    }
                }
        );
        // }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_info, container, false);
        nameVet = root.findViewById(R.id.nameVetProfile);
        horariosProfile = root.findViewById(R.id.horariosProfile);
        pricesServices = root.findViewById(R.id.pricesServices);
        servicesName = root.findViewById(R.id.servicesName);
        commentButton = root.findViewById(R.id.commentButton);
        commentText = root.findViewById(R.id.commentText);
        recyclerComments = root.findViewById(R.id.commentsRecycler);
        recyclerComments.setHasFixedSize(true);
        layoutComments = new LinearLayoutManager(getContext());
        recyclerComments.setLayoutManager(layoutComments);
        commentAdapter = new CommentAdapter();
        recyclerComments.setAdapter(commentAdapter);
        imageProfile = root.findViewById(R.id.photoPerfilProfile);

        commentButton.setOnClickListener(this);
        userName = this.getArguments().getString("usernameProfile");

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentButton:
                if (!commentText.getText().toString().equals("")) {
                    addComments(userName, commentText.getText().toString());
                    commentText.setText("");
                }
                break;
        }
    }
}