package com.example.volleylibrary;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class clickActivity extends AppCompatActivity {

    ImageView img;
    TextView head,msg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confesssion_activity);

        img=findViewById(R.id.image_ofActivity);
        head=findViewById(R.id.head);
        msg=findViewById(R.id.message_ofActivity);
//
       msg.setText(getIntent().getStringExtra("message"));


       String image=getIntent().getStringExtra("image");
       Glide.with(getApplicationContext()).load(image).into(img);




    }
}
