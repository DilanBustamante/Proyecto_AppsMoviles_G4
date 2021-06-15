package com.example.proyecto_appsmoviles_g4;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    //state or variables
    String name;
    private TextView userText;
    private EditText editTextTextPersonName;
    private TextView logout;
    String userName;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name = this.getArguments().getString("userName");

        logout = root.findViewById(R.id.logout);
        editTextTextPersonName = root.findViewById(R.id.editTextTextPersonName);
        userText = root.findViewById(R.id.userNameProfile);

        if(!name.equals(null)){
            userText.setText(name);
            editTextTextPersonName.setText(name);
        }

        logout.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("finish", true);
                startActivity(intent);
        }
    }
}