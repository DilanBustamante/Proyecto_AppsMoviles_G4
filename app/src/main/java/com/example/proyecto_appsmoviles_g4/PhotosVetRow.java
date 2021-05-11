package com.example.proyecto_appsmoviles_g4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;


public class PhotosVetRow extends RecyclerView.ViewHolder{


    private ImageView image;
    public static final int GALLERY_CALLBACK = 18;




    public PhotosVetRow(@NonNull View itemView) {
         super(itemView);

         image = itemView.findViewById(R.id.imageInRow);

    }



    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }





}
