package com.example.rescueme1.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class DashbordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashbord, container, false);

        TextView txtShelterNum = view.findViewById(R.id.txtsheltenum);
        TextView txtPetNum = view.findViewById(R.id.txtPetnum);
        TextView txtAdoptionNum = view.findViewById(R.id.txtadoptionnum);
        TextView txtUserNum = view.findViewById(R.id.txtusernum);

        DBHelper db = new DBHelper(getContext());

        int shelterCount = db.getCount("shelter");
        int petCount = db.getCount("pet");
        int adoptionCount = db.getCount("adoption");
        int userCount = db.getCount("users");

        txtShelterNum.setText(String.valueOf(shelterCount));
        txtPetNum.setText(String.valueOf(petCount));
        txtAdoptionNum.setText(String.valueOf(adoptionCount));
        txtUserNum.setText(String.valueOf(userCount));

        return view;
    }
}
