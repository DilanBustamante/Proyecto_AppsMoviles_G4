package com.example.proyecto_appsmoviles_g4;

public class Comentarios {

    private String userName;
    private String date;
    private String commentario;

    public Comentarios(String userName, String date, String commentario) {
        this.userName = userName;
        this.date = date;
        this.commentario = commentario;
    }

    public Comentarios(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentario() {
        return commentario;
    }

    public void setCommentario(String commentario) {
        this.commentario = commentario;
    }
}
