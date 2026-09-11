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
import com.example.rescueme1.DB.SHelpModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;


public class A_SContactFragment extends Fragment {
    private RecyclerView recyclerView;
    private SHelpAdapter adapter;
    private List<SHelpModel> sHelpList;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a__s_contact, container, false);

        dbHelper = new DBHelper(getContext());
        recyclerView = view.findViewById(R.id.a_sheltermessagerecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        loadSHelpMessages();

        return view;
    }

    private void loadSHelpMessages() {
        sHelpList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllSHelpRequests();

        if (cursor.moveToFirst()) {
            do {
                SHelpModel sHelp = new SHelpModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELP_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELP_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELP_CONTACT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELP_MESSAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELP_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELP_TIME))
                );
                sHelpList.add(sHelp);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new SHelpAdapter(sHelpList, getContext());
        recyclerView.setAdapter(adapter);
    }
}