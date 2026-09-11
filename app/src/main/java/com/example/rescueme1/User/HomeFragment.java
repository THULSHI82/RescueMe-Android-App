package com.example.rescueme1.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UserModel;
import com.example.rescueme1.R;
import com.example.rescueme1.DB.AdoptionModel;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView etName, etTime, txtRehome, tvsearch;
    private RecyclerView adoptionRecyclerView;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etName = view.findViewById(R.id.etName);
        etTime = view.findViewById(R.id.etTime);
        txtRehome = view.findViewById(R.id.txtrehome);
        adoptionRecyclerView = view.findViewById(R.id.adoptionrecyclerview);
        tvsearch = view.findViewById(R.id.tvsearch);

        dbHelper = new DBHelper(requireContext());

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("uid", -1);

        if (userId != -1) {
            UserModel model = dbHelper.getUserById(String.valueOf(userId));
            if (model != null) {
                etName.setText(model.getName());
            }
        }

        etTime.setText(getGreeting());

        int adoptionCount = dbHelper.getAdoptionCount();
        txtRehome.setText("Re-homing : 0" + adoptionCount);

        List<AdoptionModel> adoptionList = dbHelper.getAllAdoptions();
        U_AdoptionAdapter adapter = new U_AdoptionAdapter(getContext(), adoptionList);
        adoptionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adoptionRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgbell1 = view.findViewById(R.id.imgbell1);
        imgbell1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Messages.class);
            startActivity(intent);
        });

        Button btnreportnow = view.findViewById(R.id.reportnow);
        btnreportnow.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Myreport.class);
            startActivity(intent);
        });

    }

    private String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return "Good Morning!!";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon!!";
        } else if (hour >= 17 && hour < 21) {
            return "Good Evening!!";
        } else {
            return "Good Night!!";
        }
    }
}
