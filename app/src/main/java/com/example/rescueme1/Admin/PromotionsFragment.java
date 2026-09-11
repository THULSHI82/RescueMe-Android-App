package com.example.rescueme1.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.PromotionsModel;
import com.example.rescueme1.R;
import java.util.List;

public class PromotionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PromotionsAdapter adapter;
    private List<PromotionsModel> promotionsList;
    private DBHelper dbHelper;
    private Button btnAddPromo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotions, container, false);

        dbHelper = new DBHelper(getActivity());

        recyclerView = view.findViewById(R.id.a_promorecyclerShelters);
        btnAddPromo = view.findViewById(R.id.btnaddprom);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadPromotions();

        btnAddPromo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPromotions.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadPromotions() {
        promotionsList = dbHelper.getAllPromotions();
        adapter = new PromotionsAdapter(getActivity(), promotionsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPromotions();
    }
}