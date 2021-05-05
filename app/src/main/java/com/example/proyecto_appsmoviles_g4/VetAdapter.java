package com.example.proyecto_appsmoviles_g4;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class VetAdapter extends RecyclerView.Adapter<VetView> {

    private ArrayList<Vet> vets;


    public VetAdapter(){
        vets = new ArrayList<>();
    }


    public void addVet(Vet v){
        vets.add(v);
        Log.e(">>>>>>>>","el valor del vet es :"+v.getName());
        this.notifyDataSetChanged();
    }



    @NonNull
    @Override
    public VetView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.vetrow,null);

        ConstraintLayout rowroot = (ConstraintLayout) row;
        VetView vetView = new VetView(rowroot);

        return vetView;
    }



    @Override
    public void onBindViewHolder(VetView holder, int position) {
        Log.e(">>>>>>>>>>","VETS EN 0 ES :"+vets.get(0).getName()+vets.get(0).getEmail());
        Log.e(">>>>>>>>>>","VETS EN 0 ES :"+vets.get(1).getName()+vets.get(1).getEmail());
        holder.getNameVet().setText(vets.get(position).getName());
        holder.getVetStatus().setText(vets.get(position).getStatus());

    }




    @Override
    public int getItemCount() {

        return vets.size();
    }


    public Vet getLastVet() {
        return vets.get(vets.size()-1);
    }


}
