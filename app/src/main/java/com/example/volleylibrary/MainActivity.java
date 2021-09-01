package com.example.volleylibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    Confession_PagerAdapter pagerAdapter;


    Button button;

    Uri imageData;
    Dialog dialog;
    TextView confessionImage_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.confession_customDialog);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Dialog();
            }
        });

        tabLayout = findViewById(R.id.confession_tab);
        ViewPager viewPager = findViewById(R.id.confession_fragmentcontainer);
        pagerAdapter = new Confession_PagerAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    public void show_Dialog()
    {

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.confession_dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button confessionImage_button=dialog.findViewById(R.id.confessionImage_button);
        Button confessionSubmit_button=dialog.findViewById(R.id.confessionSubmit_button);

        confessionImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Object mimeTypes;
                ImagePicker.with(MainActivity.this)

                        .cropSquare()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        confessionSubmit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_Confession();
                dialog.dismiss();
            }
        });

        dialog.show();

    }



    public  void submit_Confession() {


        EditText confessionMessage_text = dialog.findViewById(R.id.confessionMessage_text);


        String url = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/addToConfession";
        HashMap<String, Object> hashMap = new HashMap<>();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        hashMap.put("id", 2);
        hashMap.put("is_comment", 0);
        hashMap.put("message",confessionMessage_text.getText().toString());
        hashMap.put("files",imageData);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Log.d("Response is ", response.toString());
                            if (response.getString("error").equals("")) {
                                Toast.makeText(MainActivity.this, "Successfuy sent", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "An error occurred ", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {

                            Toast.makeText(MainActivity.this, "json error " + e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("jsonException", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "error " + error.toString(), Toast.LENGTH_SHORT).show();//enable the button
                Log.d("kkk1", "Service Error:" + error.toString());
            }
        }) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("onActivityresult",String.valueOf(resultCode));
        assert data != null;
        imageData= data.getData();

       confessionImage_path=dialog.findViewById(R.id.confessionImage_path);
       confessionImage_path.setText(imageData.toString());
    }

}








