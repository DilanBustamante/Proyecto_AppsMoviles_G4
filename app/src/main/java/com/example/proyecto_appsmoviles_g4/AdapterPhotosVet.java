package com.example.proyecto_appsmoviles_g4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPhotosVet extends RecyclerView.Adapter<PhotosVetRow> {



    private ArrayList<String> photosVet;


    public AdapterPhotosVet(ArrayList<String> photosVet) {
        this.photosVet = photosVet;
    }


    public void addPhoto(String path){
        this.photosVet.add(path);
        this.notifyDataSetChanged();
    }



    @NonNull
    @Override
    public PhotosVetRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.photosvetrow,null);

        ConstraintLayout rowroot = (ConstraintLayout) row;
        PhotosVetRow photosView = new PhotosVetRow(rowroot);

        return photosView;
    }



    @Override
    public void onBindViewHolder(@NonNull PhotosVetRow holder, int position) {

        String path = photosVet.get(position).toString();
        Bitmap imageVetGalery = BitmapFactory.decodeFile(path);

        holder.getImage().setImageBitmap(imageVetGalery);

    }



    @Override
    public int getItemCount() {
        return photosVet.size();
    }



    public void clear() {
        photosVet.clear();
        notifyDataSetChanged();
    }





    public ArrayList<String> getPhotos() {
        return photosVet;
    }



}
