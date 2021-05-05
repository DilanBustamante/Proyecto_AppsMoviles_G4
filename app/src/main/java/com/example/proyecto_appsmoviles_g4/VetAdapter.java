package com.example.proyecto_appsmoviles_g4;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VetAdapter extends RecyclerView.Adapter<VetView> {

    private ArrayList<Vet> vets;
    private ArrayList<Vet> vetsOriginalstatus;


    public VetAdapter() {

        vets = new ArrayList<>();
        vetsOriginalstatus = new ArrayList<>();

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


    public void filer(String busqueda) {

        if(busqueda.length()==0){
            vets.clear();
            vets.addAll(vetsOriginalstatus);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<Vet> collect = vets.stream().filter(i -> i.getName().contains(busqueda)).collect(Collectors.toList());
                vets.clear();
                vets.addAll(collect);
            }else{
                vets.clear();
                for (Vet i: vetsOriginalstatus) {
                    if(i.getName().toLowerCase().contains(busqueda)){
                        vets.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();

    }


    public void clear() {
        vets.clear();
        notifyDataSetChanged();
    }
}

