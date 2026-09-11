package com.example.rescueme1.Admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UserModel1;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;


public class A_UserFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<UserModel1> userList;
    private DBHelper dbHelper;
    private EditText searchBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a__user, container, false);

        recyclerView = view.findViewById(R.id.a_userrecycler);
        searchBox = view.findViewById(R.id.a_user_search_box);
        dbHelper = new DBHelper(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = getAAllUsers();
        adapter = new UserAdapter(getContext(), userList);
        recyclerView.setAdapter(adapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private List<UserModel1> getAAllUsers() {
        List<UserModel1> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                UserModel1 user = new UserModel1(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DOB)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_CONTACT)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROFILE_IMAGE))
                );
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    private void filterUsers(String text) {
        List<UserModel1> filteredList = new ArrayList<>();
        for (UserModel1 user : userList) {
            if (user.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter.filterList(filteredList);
    }
}