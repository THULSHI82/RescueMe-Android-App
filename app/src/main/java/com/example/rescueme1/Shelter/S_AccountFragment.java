package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;


public class S_AccountFragment extends Fragment {
    private TextView etSName, etSOwner, etSEmail, etSContact, etRegNumber, etEstDate, etDescription;
    private ImageView imgProfile;
    private Button btnEdit;
    private DBHelper dbHelper;
    private int shelterId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__account, container, false);

        etSName = view.findViewById(R.id.etsname);
        etSOwner = view.findViewById(R.id.etsoname);
        etSEmail = view.findViewById(R.id.etsemail);
        etSContact = view.findViewById(R.id.etscontact);
        etRegNumber = view.findViewById(R.id.etrnumber);
        etEstDate = view.findViewById(R.id.etdate);
        etDescription = view.findViewById(R.id.etdescription);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnEdit = view.findViewById(R.id.btnedit);

        SharedPreferences prefs = requireContext().getSharedPreferences("shelter_data", Context.MODE_PRIVATE);
        shelterId = prefs.getInt("sid", -1);

        if (shelterId == -1) {
            Toast.makeText(getContext(), "Shelter not logged in", Toast.LENGTH_SHORT).show();
            return view;
        }

        dbHelper = new DBHelper(getContext());
        loadShelterData();

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), S_EditShelter.class);
            intent.putExtra("shelter_id", shelterId);
            startActivity(intent);
        });

        return view;
    }

    private void loadShelterData() {
        Cursor cursor = dbHelper.getSShelterById(shelterId);
        if (cursor != null && cursor.moveToFirst()) {
            etSName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_NAME)));
            etSOwner.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_OWNER_NAME)));
            etSEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_EMAIL)));
            etSContact.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_CONTACT)));
            etRegNumber.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_REGISTRATION_NUMBER)));
            etEstDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ESTABLISHED_DATE)));
            etDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESCRIPTION)));

            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROFILE_SHELTER_IMAGE));
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgProfile.setImageBitmap(bitmap);
            }
            cursor.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadShelterData();
    }
}