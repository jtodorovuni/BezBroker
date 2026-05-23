package com.example.bezbroker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bezbroker.ApiClient;
import com.example.bezbroker.R;
import com.example.bezbroker.SessionManager;
import com.example.bezbroker.adapters.EstateAdapter;
import com.example.bezbroker.model.Category;
import com.example.bezbroker.model.Estate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstatesFragment extends Fragment {

    private EstateAdapter adapter;
    private SwipeRefreshLayout swipe;
    private EditText cityET;
    private Spinner categorySpinner;
    private List<Category> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estates, container, false);

        cityET = root.findViewById(R.id.filterCityET);
        categorySpinner = root.findViewById(R.id.filterCategoryS);
        Button findB = root.findViewById(R.id.filterApplyB);
        swipe = root.findViewById(R.id.swipeRefresh);
        RecyclerView rv = root.findViewById(R.id.estatesRV);

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new EstateAdapter( estate -> {
            Toast.makeText(requireContext(), "Showing estate info...", Toast.LENGTH_SHORT).show();
        });

        rv.setAdapter(adapter);

        swipe.setOnRefreshListener(this::loadEstates);
        findB.setOnClickListener( v -> loadEstates());

        loadCategories();

        return root;
    }

    private void loadCategories(){
        ApiClient.get("/api/categories", null, new ApiClient.Callback() {
            @Override
            public void onSuccess(JSONObject body) {
                try {
                    categories = Category.listFromJson(body.optJSONArray("categories"));
                    List<String> names = new ArrayList<>();

                    for(Category c: categories){
                        names.add(c.toString());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            names
                    );

                    categorySpinner.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(int httpCode, String message) {

            }
        });
    }

    private void loadEstates(){
        swipe.setRefreshing(true);
        String token = new SessionManager(requireContext()).getToken();

        Map<String, String> q = new HashMap<>();
        String city = cityET.getText().toString();

        if(!city.isEmpty()){
            q.put("city", city);
        }

        int catPos = categorySpinner.getSelectedItemPosition();
        if(catPos > 0){
            q.put("categoryId", categories.get(catPos - 1).id + "");
        }

        ApiClient.get("/api/estates", q, token, new ApiClient.Callback() {
            @Override
            public void onSuccess(JSONObject body) {
                swipe.setRefreshing(false);

                try {
                    adapter.setItems(Estate.listFromJson(body.optJSONArray("estates")));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(int httpCode, String message) {

            }
        });



    }

}
