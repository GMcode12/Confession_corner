package com.example.volleylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myCommentAdapter extends RecyclerView.Adapter<myCommentAdapter.ViewHolder> {

    List<Mycomments>mycommentsList;
    Context context;

    public myCommentAdapter(List<Mycomments> mycommentsList, Context context) {
        this.mycommentsList = mycommentsList;
        this.context = context;
    }


    @NonNull
    @Override
    public myCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(context).inflate(R.layout.comment_view,parent,false);
        return  new myCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myCommentAdapter.ViewHolder holder, int position) {

        Mycomments mycomments_obj=mycommentsList.get(position);

        holder.mycomment.setText(mycomments_obj.getComment());

    }

    @Override
    public int getItemCount() {
        return mycommentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView mycomment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mycomment=itemView.findViewById(R.id.comment_Message);
        }
    }
}
