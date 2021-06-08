package com.example.proyecto_appsmoviles_g4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CommentView extends RecyclerView.ViewHolder {


    private ConstraintLayout root;
    private TextView user;
    private TextView commentUser;
    private TextView dateComment;

    public CommentView(ConstraintLayout root) {
        super(root);
        this.root = root;
        user = root.findViewById(R.id.userNamevalue);
        commentUser = root.findViewById(R.id.messaUser);
        dateComment = root.findViewById(R.id.dateComment);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public void setRoot(ConstraintLayout root) {
        this.root = root;
    }

    public TextView getUser() {
        return user;
    }

    public void setUser(TextView user) {
        this.user = user;
    }

    public TextView getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(TextView commentUser) {
        this.commentUser = commentUser;
    }

    public TextView getDateComment() {
        return dateComment;
    }

    public void setDateComment(TextView dateComment) {
        this.dateComment = dateComment;
    }



}
