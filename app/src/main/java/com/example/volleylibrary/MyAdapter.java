package com.example.volleylibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    @NonNull
    ArrayList<Confession_Pojo> data;
    Context context;


    public MyAdapter(@NonNull ArrayList<Confession_Pojo> data, Context context) {
        this.data = data;
        this.context = context;


    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {


        Confession_Pojo pojo_obj = data.get(position);
        String message = pojo_obj.getMessage();
        String message_text = Jsoup.parse(message).text();
        holder.message_headtext.setText(Html.fromHtml(message));
        holder.message_text.setText(message_text);


        String image = pojo_obj.getImage();
        Glide.with(context).load(image).into(holder.imageView);


        int likes_count = pojo_obj.getLikes();
        holder.likes_count.setText(String.valueOf(likes_count) + " Likes");
        int Comment_count = pojo_obj.getComment_count();
        holder.comments_count.setText(String.valueOf(Comment_count) + " Comments");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, clickActivity.class);


                ArrayList<Comment> comments = new ArrayList<>();
                ArrayList<Mycomments> myComments = new ArrayList<>();
                Bundle bundle = new Bundle();


                    comments = (ArrayList<Comment>) pojo_obj.getComments();
                    bundle.putParcelableArrayList("comment", comments);

                intent.putExtras(bundle);

                intent.putExtra("image", pojo_obj.getImage());
                intent.putExtra("message", Html.fromHtml(pojo_obj.getMessage()).toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }

        });

        holder.likes_confession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int like_ID = pojo_obj.getId();
                confession_like(like_ID);
            }
        });
    }

        @Override
        public int getItemCount () {
            return data.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView message_headtext;
            TextView message_text;
            TextView likes_count;
            ImageView imageView;
            TextView comments_count;
            ImageButton likes_confession;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                message_text = itemView.findViewById(R.id.message_text);
                message_headtext = itemView.findViewById((R.id.message_headtext));
                likes_count = itemView.findViewById(R.id.likes_count);
                imageView = itemView.findViewById(R.id.imageview);
                comments_count = itemView.findViewById(R.id.comment_count);
                likes_confession = itemView.findViewById(R.id.confession_like);


            }


        }

    private void confession_like(int id){


        String url="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/likeConfession";

        HashMap<String, Object> hashMap=new HashMap<>();

        RequestQueue requestQueue=Volley.newRequestQueue(context);

        hashMap.put("id",id);
        hashMap.put("is_like",1);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,new JSONObject(hashMap),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){

                        Log.d("Response is ",response.toString());
                        try{
                            if(response.getString("error").equals("")){
                                Toast.makeText(context,"Successfuy sent",Toast.LENGTH_LONG).show();
                            }else if(response.getString("error").equals("You have already Liked it")){
                                Toast.makeText(context,"You have already like it !!!",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(context,"Error...",Toast.LENGTH_SHORT).show();
                            }
                        }catch(JSONException e){
                            Toast.makeText(context,"json error "+e.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("jsonException",e.toString());
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

                Toast.makeText(context,"error "+error.toString(),Toast.LENGTH_SHORT).show();//enable the button
                Log.d("like error","Service Error:"+error.toString());

            }
        })
        {
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError{
                Map<String, String> params=new HashMap<String, String>();
                params.put("authtoken","VGSBP6L3FG80I6RKNIR8RN6LV42LJAHDV0UYOVVS69ISGLXB4T");
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }


    }
