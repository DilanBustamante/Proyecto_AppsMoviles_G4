package com.example.proyecto_appsmoviles_g4;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ServicesVetAdapter extends RecyclerView.Adapter<ServicesVetRow> {

    private ArrayList<String> services;



    public ServicesVetAdapter(ArrayList<String>services){
        this.services = services;

    }

    public ServicesVetAdapter(){

    }




    public void addService(String s){
        services.add(s);
        this.notifyDataSetChanged();;
    }



    @NonNull
    @Override
    public ServicesVetRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.servicesvetrow,null);

        ConstraintLayout rowroot = (ConstraintLayout) row;
        ServicesVetRow vetRow = new ServicesVetRow(rowroot);

        return vetRow;
    }






    @Override
    public void onBindViewHolder(@NonNull ServicesVetRow holder, int position) {

            String[] servArray = services.get(position).split(",");
            holder.getDescription().setText(servArray[0]);
            holder.getPrice().setText(servArray[1]);

    }



    public void clear() {
        services.clear();
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return services.size();
    }




    public ArrayList<String> getServices() {
        return services;
    }

}
