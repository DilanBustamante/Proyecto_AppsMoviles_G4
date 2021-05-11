package com.example.proyecto_appsmoviles_g4;

import java.util.ArrayList;

public class Vet {


  private String id;
  private String Name;
  private String address;
  private String phone;
  private Double latitud;
  private Double longitud;
  private String pathImage;
  private int score;
  private String status;

  private ArrayList<String> services;
private ArrayList<String> photosPaths;

  private String email;
  private String password;
  private String validatePassword;


    public Vet(String id,String name, String address, String phone, Double latitud, Double longitud, String pathImage, int score, String status, String email , String password , String validatePassword,ArrayList<String> services ,ArrayList<String>photosPaths) {
        this.id = id;
        this.Name = name;
        this.address = address;
        this.phone = phone;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pathImage = pathImage;
        this.score = score;
        this.status = status;
        this.email = email;
        this.password = password;
        this.validatePassword = validatePassword;
        this.services = services;
        this.photosPaths = photosPaths;
    }


    public Vet(){

    }




    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidatePassword() {
        return validatePassword;
    }

    public void setValidatePassword(String validatePassword) {
        this.validatePassword = validatePassword;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public ArrayList<String> getPhotosPaths() {
        return photosPaths;
    }

    public void setPhotosPaths(ArrayList<String> photosPaths) {
        this.photosPaths = photosPaths;
    }
}
