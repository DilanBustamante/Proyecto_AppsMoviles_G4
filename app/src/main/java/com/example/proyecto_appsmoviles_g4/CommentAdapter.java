package com.example.proyecto_appsmoviles_g4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentView> {


    private ArrayList<Comentarios> comments;

    public CommentAdapter(){
        comments = new ArrayList<Comentarios>();
    }

    @NonNull
    @Override
    public CommentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.commentrow,null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        CommentView commentView = new CommentView(rowroot);
        return commentView;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentView holder, int position) {
        holder.getUser().setText(comments.get(position).getUserName());
        holder.getCommentUser().setText(comments.get(position).getCommentario());
        holder.getDateComment().setText(comments.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
    public ArrayList<Comentarios> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comentarios> comments) {
        this.comments = comments;
        this.notifyDataSetChanged();
    }

    public void updateComments(){

    }

}
