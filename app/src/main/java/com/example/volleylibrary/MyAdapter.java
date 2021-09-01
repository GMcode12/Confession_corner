package com.example.volleylibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
    ArrayList<Confession_Pojo>data;
    Context context;
    String type;




    public MyAdapter(@NonNull ArrayList<Confession_Pojo> data, Context context,String type) {
        this.data = data;
        this.context = context;
        this.type=type;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(type.equals("Self")){

          view= LayoutInflater.from(context).inflate(R.layout.cardviewofmyconfession,parent,false);

        }else {

            view= LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        }
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {



        Confession_Pojo pojo_obj=data.get(position);
        String message=pojo_obj.getMessage();
        String message_text= Jsoup.parse(message).text();
        holder.message_headtext.setText(Html.fromHtml(message));
        holder.message_text.setText(message_text);



        holder.id.setText(String.valueOf(pojo_obj.getId()));
        String image=pojo_obj.getImage();
        Glide.with(context).load(image).into(holder.imageView);

        if(type.equals("All"))
        {
            int likes_count=pojo_obj.getLikes();
            holder.likes_count.setText(String.valueOf(likes_count)+" Likes");
            int Comment_count= pojo_obj.getComment_count();
            holder.comments_count.setText(String.valueOf(Comment_count)+" Comments");
        }
        else {

            holder.likes_count.setText(String.valueOf(pojo_obj.getLike())+" Likes");
//            int MyComment_count= pojo_obj.get_myCommentCount();
//            holder.comments_count.setText(String.valueOf(MyComment_count)+" Comments");
            holder.delete_confession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                    Button delete,cancle;

                    Dialog  dialog=new Dialog(context);
                    dialog.setContentView(R.layout.deleteconfirmation_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    delete=dialog.findViewById(R.id.confirm_delete);
                    cancle=dialog.findViewById(R.id.cancle_delete);

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int delete_ID=pojo_obj.getId();
                            holder.delete_confession(delete_ID,position);
                            dialog.dismiss();

                        }
                    });
                    cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();

                }
            });
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,clickActivity.class);

                Spanned head = Html.fromHtml(pojo_obj.getMessage());
                intent.putExtra("image",pojo_obj.getImage());
                intent.putExtra("message",Html.fromHtml(pojo_obj.getMessage()).toString());
                intent.putExtra("Heading",head);
//                intent.putExtra("message",pojo_obj.getMessage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }

        });

        holder.likes_confession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int like_ID=pojo_obj.getId();
                holder.confession_like(like_ID);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView message_headtext;
        TextView message_text;
        TextView likes_count;
        ImageView imageView;
        TextView comments_count;
        ImageButton likes_confession;
        ImageButton delete_confession;

        TextView id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message_text=itemView.findViewById(R.id.message_text);
            message_headtext=itemView.findViewById((R.id.message_headtext));
            likes_count= itemView.findViewById(R.id.likes_count);
            imageView= itemView.findViewById(R.id.imageview);
            comments_count=itemView.findViewById(R.id.comment_count);
            likes_confession=itemView.findViewById(R.id.confession_like);
            id=itemView.findViewById(R.id.ID);



            if(type.equals("Self"))
            {
                delete_confession=itemView.findViewById(R.id.delete_confession);
            }

        }
        public void delete_Viewbyposition(int pos)
        {
            data.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, data.size());
        }
        private void delete_confession(int id,int pos)
        {

            String url="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/deleteConfession";

            HashMap<String,Object> hashMap=new HashMap<>();

            RequestQueue requestQueue= Volley.newRequestQueue(context);

            hashMap.put("id",id);
            hashMap.put("is_comment",0);

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response is ", response.toString());
                            try {
                                if (response.getString("error").equals("")) {
                                    delete_Viewbyposition(pos);
                                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(context, "Error while deleting..", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(context, "json error " + e.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("jsonException", e.toString());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context, "error " + error.toString(), Toast.LENGTH_SHORT).show();//enable the button
                    Log.d("like error", "Service Error:" + error.toString());

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("authtoken", "VGSBP6L3FG80I6RKNIR8RN6LV42LJAHDV0UYOVVS69ISGLXB4T");
                    return params;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        }


        private void confession_like(int id) {


            String url="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/likeConfession";

            HashMap<String,Object> hashMap=new HashMap<>();

            RequestQueue requestQueue= Volley.newRequestQueue(context);

            hashMap.put("id",id);
            hashMap.put("is_like",1);

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response is ", response.toString());
                            try {
                                if (response.getString("error").equals("")) {
                                    Toast.makeText(context, "Successfuy sent", Toast.LENGTH_LONG).show();
                                } else if(response.getString("error").equals("You have already Liked it")){
                                    Toast.makeText(context, "You have already like it !!!", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, "json error " + e.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("jsonException", e.toString());
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context, "error " + error.toString(), Toast.LENGTH_SHORT).show();//enable the button
                    Log.d("like error", "Service Error:" + error.toString());

                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("authtoken", "VGSBP6L3FG80I6RKNIR8RN6LV42LJAHDV0UYOVVS69ISGLXB4T");
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




}
