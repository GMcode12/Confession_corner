package com.example.volleylibrary;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfessionFragment extends Fragment {

    String URL = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/displayConfessions";
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    MyAdapter adapter;
    ArrayList<Confession_Pojo> api_data;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
//   SwipeRefreshLayout swipeRefreshLayout;




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.confession_fragment, null);

        recyclerView = v.findViewById(R.id.recyclerViewofconfessions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        api_data = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(requireContext());

//        swipeRefreshLayout=v.findViewById(R.id.swipe_refresh);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Confession_Pojo data = gson.fromJson(jsonObject.toString(), Confession_Pojo.class);

                                api_data.add(data);
                            }
                            adapter = new MyAdapter(api_data,getContext());
                            recyclerView.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();

                Log.d("confession_fragment","Service Error:"+error.toString());
                error.printStackTrace();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("authtoken", "9E34C5GW1BN11IQORGAHR512590WJQOIAXK1DB0L9UHDDUHHSE");
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);




//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//
//        });


        return v;


    }
}