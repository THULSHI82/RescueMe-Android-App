package com.example.rescueme1.Admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.util.List;

public class A_AdoptionFragment extends Fragment {

    private RecyclerView recyclerView;
    private A_AdoptionAdapter adapter;
    private DBHelper dbHelper;
    private EditText searchBox;

    public A_AdoptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a__adoption, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.a_adoption_recyclerShelters);
        searchBox = view.findViewById(R.id.a_adoption_search_box);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper = new DBHelper(getContext());

        List<AdoptionModel> adoptionList = dbHelper.getAllAdoptions();
        adapter = new A_AdoptionAdapter(getContext(), adoptionList);
        recyclerView.setAdapter(adapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
