package com.example.rescueme1.Admin;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UHelpModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;


public class A_UContactFragment extends Fragment {
    private RecyclerView recyclerView;
    private UHelpAdapter adapter;
    private List<UHelpModel> uHelpList;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a__u_contact, container, false);

        dbHelper = new DBHelper(getContext());
        recyclerView = view.findViewById(R.id.a_usermessagerecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        loadUHelpMessages();

        return view;
    }

    private void loadUHelpMessages() {
        uHelpList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllUHelpRequests();

        if (cursor.moveToFirst()) {
            do {
                UHelpModel uHelp = new UHelpModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_UHELP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_UHELP_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_UHELP_CONTACT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_UHELP_MESSAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_UHELP_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_UHELP_TIME))
                );
                uHelpList.add(uHelp);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new UHelpAdapter(uHelpList, getContext());
        recyclerView.setAdapter(adapter);
    }
}