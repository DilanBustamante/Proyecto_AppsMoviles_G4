package com.example.proyecto_appsmoviles_g4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private Button addPhotoButton;

    private StorageReference mstorage;


    private ArrayList<String> servicesAux;
    private RecyclerView servicesRecycler;
    private ServicesVetAdapter adapter;
    private ArrayList<String> services;


    private OnNewKey observerkey;
    public static final int GALLERY_CALLBACK = 13;
    private String path;

    private FirebaseFirestore db;


    public static final int GALLERY_CALLBACK2 = 14;
    private RecyclerView photosRecycler;
    private AdapterPhotosVet adapterPhotos;
    private ArrayList<String> photosVetGalery;
    private ArrayList<String> photosAux;

    private String valueProfilePhoto = "";

    private EditText hours1;
    private EditText hours2;
    private EditText hours3;
    private EditText hours4;
    private EditText hours5;
    private EditText hours6;
    private EditText hours7;

    private Button buttonEditHours;
    private Button buttonSaveHours;

    private ArrayList<String> hours;



    public vetFrag() {
        // Required empty public constructor
        addressGlo = "";
        services = new ArrayList<>();
        servicesAux = new ArrayList<>();
        photosVetGalery = new ArrayList<>();
        photosAux = new ArrayList<>();
        hours = new ArrayList<>();

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


        //Instancia storage

        mstorage = FirebaseStorage.getInstance().getReference();

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
        photosRecycler = root.findViewById(R.id.photosRecycler);
        addPhotoButton = root.findViewById(R.id.addPhotoButton);
        buttonEditHours = root.findViewById(R.id.buttonEditHours);
        buttonSaveHours = root.findViewById(R.id.buttonSaveHours);

        hours1 = root.findViewById(R.id.hours1);
        hours2 = root.findViewById(R.id.hours2);
        hours3 = root.findViewById(R.id.hours3);
        hours4 = root.findViewById(R.id.hours4);
        hours5 = root.findViewById(R.id.hours5);
        hours6 = root.findViewById(R.id.hours6);
        hours7 = root.findViewById(R.id.hours7);


        hours1.setEnabled(false);
        hours2.setEnabled(false);
        hours3.setEnabled(false);
        hours4.setEnabled(false);
        hours5.setEnabled(false);
        hours6.setEnabled(false);
        hours7.setEnabled(false);






        //inicializar adapters
        adapter = new ServicesVetAdapter(services);
        adapter.clear();
        adapter.notifyDataSetChanged();

        adapterPhotos = new AdapterPhotosVet(photosVetGalery);
        adapterPhotos.clear();
        adapterPhotos.notifyDataSetChanged();


        //pasar adaptadores a los recyclers y pasar managers
        servicesRecycler.setAdapter(adapter);
        servicesRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        photosRecycler.setAdapter(adapterPhotos);
        photosRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));



        //Colocar OnclickListener a los botones

        map.setOnClickListener(this);
        editInformation.setOnClickListener(this);
        txtImage.setOnClickListener(this);
        editPhone.setOnClickListener(this);
        addRow.setOnClickListener(this);
        addPhotoButton.setOnClickListener(this);
        buttonEditHours.setOnClickListener(this);
        buttonSaveHours.setOnClickListener(this);


        phoneNumber.setEnabled(false);


        String key1glo = this.getActivity().getIntent().getStringExtra("key2");
        nameVet.setText(key1glo);


        db = FirebaseFirestore.getInstance();


        try{
            this.getPhoneBD();
            this.getImageBD();
            this.getAddressBD();
            this.getServicesBD();
            this.getPhotosGaleryBD();
            this.getHoursBD();

        }catch (Exception e){
            e.printStackTrace();
        }




        if (addressGlo != null) {
            AddresVetxt.setText(addressGlo);
        }

        editInformation.setEnabled(true);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddPlace:
                Toast.makeText(getActivity(), "Agrega la ubicación de tu veterinaria", Toast.LENGTH_LONG).show();
                observerkey.onNewkey("normalMap");

                editInformation.setEnabled(true);
                this.UpdateBlankAddress();
                inicioActivity activity = (inicioActivity) getActivity();
                activity.showFragment(activity.getmapFragment());


                break;

            case R.id.buttonInformation:
                editInformation.setEnabled(false);
                this.UpdateData();
                phoneNumber.setEnabled(false);
                Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_LONG).show();
                break;

            case R.id.txtImage:

                editInformation.setEnabled(true);

                valueProfilePhoto = "onlyProfilePhoto";

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_CALLBACK);
                break;

            case R.id.buttonEditPhone:
                editInformation.setEnabled(true);
                Toast.makeText(getActivity(), "Por favor edita tu telefono", Toast.LENGTH_SHORT).show();
                phoneNumber.setEnabled(true);
                break;

            case R.id.buttonAddRow:
                editInformation.setEnabled(true);

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


            case R.id.addPhotoButton:

                Intent j = new Intent(Intent.ACTION_GET_CONTENT);
                j.setType("image/*");
                startActivityForResult(j, GALLERY_CALLBACK2);

            break;

            case R.id.buttonEditHours:

                hours1.setEnabled(true);
                hours2.setEnabled(true);
                hours3.setEnabled(true);
                hours4.setEnabled(true);
                hours5.setEnabled(true);
                hours6.setEnabled(true);
                hours7.setEnabled(true);
                Toast.makeText(getActivity(), "Edita las horas como se indica, si no prestarás servicio un dia, deja el espacio en blanco", Toast.LENGTH_LONG).show();

            break;

            case R.id.buttonSaveHours:
                this.CleanHoursBD();
                this.hours.clear();
                this.addHoursBD();

                hours1.setEnabled(false);
                hours2.setEnabled(false);
                hours3.setEnabled(false);
                hours4.setEnabled(false);
                hours5.setEnabled(false);
                hours6.setEnabled(false);
                hours7.setEnabled(false);

            break;

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

            StorageReference filePath = mstorage.child("images/").child(uri.getLastPathSegment());

            Task upload = filePath.putFile(uri);

            Task<Uri> urlTask = upload.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Glide.with(getContext()).load(downloadUri).into(image);
                        db.collection("vet").whereEqualTo("name", nameVet.getText().toString()).get().addOnSuccessListener(
                                query -> {
                                    if (query.getDocuments().size() > 0) {
                                        Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                                        updateVet.setPathImage(downloadUri.toString());
                                        db.collection("vet").document(updateVet.getId()).set(updateVet);
                                        Toast.makeText(getActivity(),"Se ha actualizado la foto de perfil",Toast.LENGTH_LONG);
                                    }
                                }
                        );
                        Log.println(Log.DEBUG,"URL",downloadUri.toString());
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });


        }else if(resultCode==RESULT_OK && requestCode == GALLERY_CALLBACK2) {

            Uri uri = data.getData();
            path = UtilDomi.getPath(getActivity(), uri);
                 /*   */
            this.addPhotoBD(path);
            this.addRowWitPhoto(path);

            photosRecycler.setAdapter(adapterPhotos);
            adapterPhotos.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Imagen guardada", Toast.LENGTH_SHORT).show();
        }

    }

    public Bitmap dowloadImage (String image){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(image);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }


    public void UpdateData(){
        String name = nameVet.getText().toString();
        String phone = phoneNumber.getText().toString();
        String address = AddresVetxt.getText().toString();


        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                        updateVet.setPathImage(path);
                        updateVet.setPhone(phone);
                        updateVet.setAddress(address);
                        this.addServiceBD();

                        db.collection("vet").document(updateVet.getId()).set(updateVet);

                    }
                }
        );

    }



    public void UpdateBlankAddress(){
        String name = nameVet.getText().toString();
        String address = "";

        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                        updateVet.setAddress(address);
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
                             Glide.with(getContext()).load(path).into(image);
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



    public void getHoursBD(){
        String name = nameVet.getText().toString();
        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet vet = query.getDocuments().get(0).toObject(Vet.class);
                         ArrayList<String> hoursBD = vet.getHours();

                         if(!hoursBD.isEmpty()){
                             for(int i = 0; i< hoursBD.size();i++){
                                 String[] array = hoursBD.get(i).split(",");

                                 if (i == 0 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours1.setText(hoursBD.get(i).toString());

                                 }else if(i == 1 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours2.setText(hoursBD.get(i).toString());

                                 }else if(i == 2 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours3.setText(hoursBD.get(i).toString());

                                 }else if(i == 3 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours4.setText(hoursBD.get(i).toString());

                                 }else if(i == 4 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours5.setText(hoursBD.get(i).toString());

                                 }else if(i == 5 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours6.setText(hoursBD.get(i).toString());

                                 }else if(i == 6 && hoursBD.get(i).equals("Sin servicio")) {
                                     hours7.setText(hoursBD.get(i).toString());
                                 }


                                 if(array[0].equals("Lunes")){
                                     hours1.setText(array[1]+", a ,"+array[2]);

                                 }else if (array[0].equals("Martes")){
                                     hours2.setText(array[1]+", a ,"+array[2]);

                                 }else if (array[0].equals("Miercoles")){
                                     hours3.setText(array[1]+", a ,"+array[2]);

                                 }else if (array[0].equals("Jueves")){
                                     hours4.setText(array[1]+", a ,"+array[2]);

                                 }else if (array[0].equals("Viernes")){
                                     hours5.setText(array[1]+", a ,"+array[2]);

                                 }else if (array[0].equals("Sabado")){
                                     hours6.setText(array[1]+", a ,"+array[2]);

                                 }else if (array[0].equals("Domingo")){
                                     hours7.setText(array[1]+", a ,"+array[2]);
                                 }

                             }


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



    public void getPhotosGaleryBD(){

        String name = nameVet.getText().toString();
        db.collection("vet").whereEqualTo("name",name).get().addOnSuccessListener(
                query ->{
                    if(query.getDocuments().size()>0){
                        Vet vet = query.getDocuments().get(0).toObject(Vet.class);

                        ArrayList<String> photosBD = vet.getPhotosPaths();
                        photosAux = photosBD;
                        adapterPhotos = new AdapterPhotosVet(photosBD);
                        adapterPhotos.notifyDataSetChanged();

                        for(int i = 0;i<photosBD.size();i++){
                            this.addRowWitPhoto( photosBD.get(i).toString());
                        }
                        photosRecycler.setAdapter(adapterPhotos);
                        photosRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
                    }
                }
        );

    }



       public void addRowWitPhoto(String photoGalery){
           photosVetGalery.add(photoGalery);
           adapterPhotos = new AdapterPhotosVet(photosVetGalery);
           adapterPhotos.notifyDataSetChanged();

       }





    public void addServiceBD(){
        //this.refreshList();
        SharedPreferences sp = this.getActivity().getSharedPreferences("service",MODE_PRIVATE);
        String s = sp.getString("serviceKey","nokeyService");

     if(valueProfilePhoto.equals("onlyProfilePhoto"))  {
         s = "nokeyService";
         valueProfilePhoto = "";
     }else{
         valueProfilePhoto = "";
         String name = nameVet.getText().toString();
         if(!s.equals("nokeyService")) {
             servicesAux.add(s);
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



    }



    public void addPhotoBD(String path){


        String name = nameVet.getText().toString();
        photosAux.add(path);

        if(!path.equals("")) {

            db.collection("vet").whereEqualTo("name", name).get().addOnSuccessListener(
                    query -> {
                        if (query.getDocuments().size() > 0) {
                            Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);

                            updateVet.setPhotosPaths(photosAux);
                            db.collection("vet").document(updateVet.getId()).set(updateVet);

                            adapterPhotos = new AdapterPhotosVet(photosAux);
                            adapterPhotos.notifyDataSetChanged();

                        }
                    }
            );

        }

    }





    public void validateHours(){


        String h1 = hours1.getText().toString();
        String h2 = hours2.getText().toString();
        String h3 =hours3.getText().toString();
        String h4 =hours4.getText().toString();
        String h5 =hours5.getText().toString();
        String h6 =hours6.getText().toString();
        String h7 =hours7.getText().toString();


        String[] hA1 = h1.split(",");
        String[] hA2 = h2.split(",");
        String[] hA3 = h3.split(",");
        String[] hA4 = h4.split(",");
        String[] hA5 = h5.split(",");
        String[] hA6 = h6.split(",");
        String[] hA7 = h7.split(",");

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss a");



        try {

            String closeValue = "sin servicio";

            if(h1.equals("")){
                hours.add(0,closeValue);
            }else{
                Date date1 = dateFormat1.parse(hA1[0]); //Lunes start
                Date date2 = dateFormat1.parse(hA1[2]); //Lunes close

                String out1 = dateFormat2.format(date1); //Lunes start
                String out2 = dateFormat2.format(date2); //Lunes close

                String fh1 = "Lunes,"+out1+","+out2;
                hours.add(0,fh1);
            }

            if(h2.equals("")){
                hours.add(1,closeValue);
            }else{
                Date date3 = dateFormat1.parse(hA2[0]); //Martes start
                Date date4 = dateFormat1.parse(hA2[2]); //Martes close

                String out3 = dateFormat2.format(date3); //Martes start
                String out4 = dateFormat2.format(date4); //Martes close

                String fh2 = "Martes,"+out3+","+out4;
                hours.add(1,fh2);
            }


            if(h3.equals("")){
                hours.add(2,closeValue);
            }else{
                Date date5 = dateFormat1.parse(hA3[0]); //Miercoles start
                Date date6 = dateFormat1.parse(hA3[2]); //Miercoles close

                String out5 = dateFormat2.format(date5); //Miercoles start
                String out6 = dateFormat2.format(date6); //Miercoles close

                String fh3 = "Miercoles,"+out5+","+out6;
                hours.add(2,fh3);
            }


            if(h4.equals("")){
                hours.add(3,closeValue);
            }else{
                Date date7 = dateFormat1.parse(hA4[0]); //Jueves start
                Date date8 = dateFormat1.parse(hA4[2]); //Jueves close

                String out7 = dateFormat2.format(date7); //Jueves start
                String out8 = dateFormat2.format(date8); //Jueves close

                String fh4 = "Jueves,"+out7+","+out8;
                hours.add(3,fh4);
            }


            if(h5.equals("")){
                hours.add(4,closeValue);
            }else{
                Date date9 = dateFormat1.parse(hA5[0]); //Viernes start
                Date date10 = dateFormat1.parse(hA5[2]); //Viernes close

                String out9 = dateFormat2.format(date9); //Viernes start
                String out10 = dateFormat2.format(date10); //Viernes close

                String fh5 = "Viernes,"+out9+","+out10;
                hours.add(4,fh5);
            }


            if(h6.equals("")){
                hours.add(5,closeValue);
            }else{
                Date date11 = dateFormat1.parse(hA6[0]); //Sabado start
                Date date12 = dateFormat1.parse(hA6[2]); //Sabado close

                String out11 = dateFormat2.format(date11); //Sabado start
                String out12 = dateFormat2.format(date12); //Sabado close

                String fh6 = "Sabado,"+out11+","+out12;
                hours.add(5,fh6);
            }


            if(h7.equals("")){
                hours.add(6,closeValue);
            }else{
                Date date13 = dateFormat1.parse(hA7[0]); //Sabado start
                Date date14 = dateFormat1.parse(hA7[2]); //Sabado close

                String out13 = dateFormat2.format(date13); //Domingo start
                String out14 = dateFormat2.format(date14); //Domingo close

                String fh7 = "Domingo,"+out13+","+out14;
                hours.add(6,fh7);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }





    }



    public void addHoursBD(){
            String name = nameVet.getText().toString();
            //if(hours.size()==6){
                this.validateHours();
                db.collection("vet").whereEqualTo("name", name).get().addOnSuccessListener(
                        query -> {
                            if (query.getDocuments().size() > 0) {
                                Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);

                                updateVet.setHours(hours);
                                db.collection("vet").document(updateVet.getId()).set(updateVet);
                                Toast.makeText(this.getActivity(),"Se han añadido los horarios",Toast.LENGTH_LONG);

                            }
                        }
                );
           // }

    }


    public void CleanHoursBD(){
        String name = nameVet.getText().toString();
        ArrayList<String> blanckArray = new ArrayList<>();
        db.collection("vet").whereEqualTo("name", name).get().addOnSuccessListener(
                query -> {
                    if (query.getDocuments().size() > 0) {
                        Vet updateVet = query.getDocuments().get(0).toObject(Vet.class);
                        updateVet.setHours(blanckArray);
                        db.collection("vet").document(updateVet.getId()).set(updateVet);
                    }
                }
        );
    }


//     public void refreshListPhotos(){
//        adapterPhotos.clear();
//        photosAux.clear();
//        adapterPhotos.getPhotos().clear();
//
//        this.getPhotosGaleryBD();
//        photosVetGalery = photosAux;
//
//        adapterPhotos = new AdapterPhotosVet(photosVetGalery);
//        adapterPhotos.notifyDataSetChanged();
//
//    }


    public void refreshListHours(){
        servicesAux.clear();
        adapter.getServices().clear();
        this.getServicesBD();
        services = servicesAux;
        adapter = new ServicesVetAdapter(services);
        adapter.notifyDataSetChanged();

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