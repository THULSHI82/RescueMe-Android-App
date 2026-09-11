package com.example.rescueme1.User;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.PromotionsModel;
import com.example.rescueme1.R;
import java.util.List;

public class U_Messages extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PromotionsAdapter adapter;
    private List<PromotionsModel> promotionsList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umessages);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.u_promo_recyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPromotions();
    }

    private void loadPromotions() {
        promotionsList = dbHelper.getAllPromotions();
        adapter = new PromotionsAdapter(this, promotionsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPromotions();
    }
}