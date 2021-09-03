package com.example.volleylibrary;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class clickActivity extends AppCompatActivity {

    ImageView img;
    TextView msg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confesssion_activity);

        img=findViewById(R.id.image_ofActivity);
        msg=findViewById(R.id.message_ofActivity);
//
       msg.setText(getIntent().getStringExtra("message"));

       String image=getIntent().getStringExtra("image");
       Glide.with(getApplicationContext()).load(image).into(img);

        Bundle bundle = getIntent().getExtras();


       RecyclerView recyclerView=findViewById(R.id.recyclerViewofcomment_inActivity);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(clickActivity.this));



            ArrayList<Comment> arraylist = bundle.getParcelableArrayList("comment");
            commentAdapter commentAdapter=new commentAdapter(arraylist,clickActivity.this);
            recyclerView.setAdapter(commentAdapter);




    }
}
