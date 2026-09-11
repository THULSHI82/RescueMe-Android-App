package com.example.rescueme1.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.rescueme1.Opening.Login;
import com.example.rescueme1.R;
import com.example.rescueme1.User.U_Appoiment_view;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnmyprofile= view.findViewById(R.id.btnmyprofile);
        btnmyprofile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Account_View.class);
            startActivity(intent);
        });

        Button btnabout = view.findViewById(R.id.btnabout);
        btnabout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUs.class);
            startActivity(intent);
        });

        Button btnMyAppointment = view.findViewById(R.id.btnmyappoinmet);
        btnMyAppointment.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Appoiment_view.class);
            startActivity(intent);
        });

        Button btnmyreservation = view.findViewById(R.id.btnmyreservation);
        btnmyreservation.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Reservation_view.class);
            startActivity(intent);
        });

        Button btnmyreport = view.findViewById(R.id.btnmyreport);
        btnmyreport.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Myreport.class);
            startActivity(intent);
        });

        Button btncontactus = view.findViewById(R.id.btncontactus);
        btncontactus.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_ContactUs.class);
            startActivity(intent);
        });

        ImageView imgbell4 = view.findViewById(R.id.imgbell4);
        imgbell4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Messages.class);
            startActivity(intent);
        });

        Button btnLogout = view.findViewById(R.id.btnlogout);
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = requireActivity().getSharedPreferences("user_prefs", getContext().MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getActivity(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

}
