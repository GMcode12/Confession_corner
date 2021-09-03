package com.example.volleylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ViewHolder> {

    public commentAdapter(List<Comment> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    List<Comment> commentsList;
    Context context;
    @NonNull
    @Override
    public commentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.comment_view,parent,false);
        return  new commentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commentAdapter.ViewHolder holder, int position) {

        Comment comments_obj=commentsList.get(position);
        holder.comment.setText(comments_obj.getMessage());

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment=itemView.findViewById(R.id.comment_Message);
        }
    }
}
