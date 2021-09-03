package com.example.volleylibrary;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class own_confessionClickactivity extends AppCompatActivity {

    ImageView img;
    TextView msg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confesssion_activity);

        img = findViewById(R.id.image_ofActivity);
        msg = findViewById(R.id.message_ofActivity);
//
        msg.setText(getIntent().getStringExtra("message"));

        String image = getIntent().getStringExtra("image");
        Glide.with(getApplicationContext()).load(image).into(img);

        Bundle bundle = getIntent().getExtras();


        RecyclerView recyclerView = findViewById(R.id.recyclerViewofcomment_inActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(own_confessionClickactivity.this));


        ArrayList<Mycomments> myarraylist = bundle.getParcelableArrayList("comment");
        myCommentAdapter mycommentAdapter = new myCommentAdapter(myarraylist, own_confessionClickactivity.this);
        recyclerView.setAdapter(mycommentAdapter);


    }

}
