package com.example.proyecto_appsmoviles_g4;

public class User {

    private String id;
    private String name;
    private String phone;
    private String pathImage;
    private String validatePassword;
    private String password;
    private String email;




    public User(String id , String name, String phone, String pathImage, String password,String validatePassword, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.pathImage = pathImage;
        this.password = password;
        this.email = email;
        this.validatePassword = validatePassword;


    }



    public User (){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidatePassword() {
        return validatePassword;
    }

    public void setValidatePassword(String validatePassword) {
        this.validatePassword = validatePassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
