package com.example.proyecto_appsmoviles_g4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;

import io.grpc.Server;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class vetFrag extends Fragment implements View.OnClickListener, mapFrag.OnNewAddressListener{

    private TextView nameVet;
    private ImageView image;
    private Button map;
    private EditText phoneNumber;
    private Button editInformation;
    private TextView AddresVetxt;
    private String addressGlo;
    private TextView txtImage;
    private Button editPhone;
    private Button addRow;


    private ArrayList<String> servicesAux;
    private RecyclerView servicesRecycler;
    private ServicesVetAdapter adapter;
    private ArrayList<String> services;


    private OnNewKey observerkey;
    public static final int GALLERY_CALLBACK = 13;
    private String path;

    private FirebaseFirestore db;



    public vetFrag() {
        // Required empty public constructor
        addressGlo = "";
        services = new ArrayList<>();
        servicesAux = new ArrayList<>();

    }


    public static vetFrag newInstance() {
        vetFrag fragment = new vetFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vet, container, false);


        //Instancias los elementos visibles

        addRow = root.findViewById(R.id.buttonAddRow);
        image = root.findViewById(R.id.imageUser);
        map = root.findViewById(R.id.buttonAddPlace);
        phoneNumber = root.findViewById(R.id.userPhoneTxt);
        editInformation = root.findViewById(R.id.buttonInformation);
        nameVet = root.findViewById(R.id.nameVetTxt);
        AddresVetxt = root.findViewById(R.id.vetAddress);
        txtImage = root.findViewById(R.id.txtImage);
        editPhone = root.findViewById(R.id.buttonEditPhone);
        servicesRecycler = root.findViewById(R.id.servicesRecycler);


        adapter = new ServicesVetAdapter(services);
        adapter.notifyDataSetChanged();

        servicesRecycler.setAdapter(adapter);
        servicesRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));





        //Colocar OnclickListener a los botones

        map.setOnClickListener(this);
        editInformation.setOnClickListener(this);
        txtImage.setOnClickListener(this);
        editPhone.setOnClickListener(this);
        addRow.setOnClickListener(this);

        phoneNumber.setEnabled(false);


        String key1glo = this.getActivity().getIntent().getStringExtra("key2");
        nameVet.setText(key1glo);


        db = FirebaseFirestore.getInstance();




        this.getPhoneBD();
        this.getImageBD();
        this.getAddressBD();
        this.getServicesBD();




        if (addressGlo != null) {
            AddresVetxt.setText(addressGlo);
        }


        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddPlace:
                Toast.makeText(getActivity(), "Agrega la ubicación de tu veterinaria", Toast.LENGTH_LONG).show();

                observerkey.onNewkey("normalMap");
                inicioActivity activity = (inicioActivity) getActivity();
                activity.showFragment(activity.getmapFragment());


                break;

            case R.id.buttonInformation:
                this.UpdateData();
                phoneNumber.setEnabled(false);
                Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_LONG).show();
                break;

            case R.id.txtImage:
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_CALLBACK);
                break;

            case R.id.buttonEditPhone:
                Toast.makeText(getActivity(), "Por favor edita tu telefono", Toast.LENGTH_SHORT).show();
                phoneNumber.setEnabled(true);
                break;

            case R.id.buttonAddRow:
                if(servicesAux.isEmpty()){
                    this.addNewRow();
                    servicesRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    this.refreshList();
                    this.addNewRow();
                    //this.addServiceBD();
                    servicesRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
               break;
        }
    }

    public void update(){

        for(int i = 0; i< adapter.getItemCount();i++){

        }

    }


    public void addNewRow() {
        services.add("Description,price");
        adapter = new ServicesVetAdapter(services);
        adapter.notifyDataSetChanged();
    }



    public void addNRowwithData(String d,String p) {
        services.add(d+","+p);
        adapter = new ServicesVetAdapter(services);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onNewAddress(String address) {
        this.addressGlo = address;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_CALLBACK) {

            Uri uri = data.getData();
            path = UtilDomi.getPath(getActivity(), uri);
            Bitmap image1 = BitmapFactory.decodeFile(path);
            image.setImageBitmap(image1);

        }
    }



    public void UpdateData(){


        String address = AddresVetxt.getText().toString();
        String name = nameVet.getText().toString();
        String phone = phoneNumber.getText().toString();


        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                        updateVet.setAddress(address);
                        updateVet.setPathImage(path);
                        updateVet.setPhone(phone);
                        this.addServiceBD();
                        db.collection("vet").document(updateVet.getId()).set(updateVet);

                    }
                }
        );

    }


    public void getPhoneBD(){
        String name = nameVet.getText().toString();
        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet vet = query.getDocuments().get(0).toObject(Vet.class);
                        phoneNumber.setText(vet.getPhone());
                    }
                }
        );

    }

    public void getImageBD(){
        String name = nameVet.getText().toString();
        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet vet = query.getDocuments().get(0).toObject(Vet.class);
                         path = vet.getPathImage();
                         if(!path.isEmpty()){
                             Bitmap imagevet = BitmapFactory.decodeFile(path);
                             image.setImageBitmap(imagevet);
                         }
                    }
                }
        );
    }


    public void getAddressBD(){
        String name = nameVet.getText().toString();
        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet vet = query.getDocuments().get(0).toObject(Vet.class);
                        String address = vet.getAddress();
                        if(!address.isEmpty()){
                            AddresVetxt.setText(address);
                        }
                    }
                }
        );
    }




    public void getServicesBD(){

        String name = nameVet.getText().toString();
        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet vet = query.getDocuments().get(0).toObject(Vet.class);

                         ArrayList<String> servicesBD = vet.getServices();
                         servicesAux = servicesBD;
                         adapter = new ServicesVetAdapter(servicesBD);
                         adapter.notifyDataSetChanged();

                             for(int i = 0;i<servicesBD.size();i++){
                                 String[] sp = servicesBD.get(i).split(",");
                                 this.addNRowwithData(sp[0],sp[1]);

                         }
                        servicesRecycler.setAdapter(adapter);
                        servicesRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
                    }
                }
        );

    }




//    public void setService(String service) {
//       serviceGlo = service;
//    }






    public void addServiceBD(){

        SharedPreferences sp = this.getActivity().getSharedPreferences("service",MODE_PRIVATE);
        String s = sp.getString("serviceKey","nokeyService");
        String name = nameVet.getText().toString();
        servicesAux.add(s);


        if(!s.equals("nokeyService")) {

            db.collection("vet").whereEqualTo("name", name).get().addOnSuccessListener(
                    query -> {
                        if (query.getDocuments().size() > 0) {
                            Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);

                            updateVet.setServices(servicesAux);
                            db.collection("vet").document(updateVet.getId()).set(updateVet);

                            adapter = new ServicesVetAdapter(servicesAux);
                            adapter.notifyDataSetChanged();

                            }
                    }
            );

        }

    }


    public void refreshList(){
        adapter.clear();
        servicesAux.clear();
        Log.e(">>>>>>>>>>","EL SIZE DE SERVICES EN EL ADAPTER ES :"+adapter.getItemCount());
        adapter.getServices().clear();
        this.getServicesBD();
        services = servicesAux;
        adapter = new ServicesVetAdapter(services);
        adapter.notifyDataSetChanged();

    }





    public void setStringkey(OnNewKey observer){
        this.observerkey = observer;
    }

}

   interface OnNewKey{
    void onNewkey(String key2);
}