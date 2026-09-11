package com.example.rescueme1.Shelter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class S_Adoption_Edit extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 101;

    private EditText etName, etAge, etContact, etNIC, etAddress, etDate;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private CheckBox cbTerms;
    private Button btnUpdate;
    private ImageView imgAdopter;

    private int adoptionId;
    private DBHelper dbHelper;
    private AdoptionModel model;

    public static final String EXTRA_ADOPTION_ID = "adoptionId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sadoption_edit);

        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initializeViews();

        dbHelper = new DBHelper(this);

        adoptionId = getIntent().getIntExtra(EXTRA_ADOPTION_ID, -1);
        Log.d("S_Adoption_Edit", "Received adoptionId: " + adoptionId);

        if (adoptionId == -1) {
            Toast.makeText(this, "Invalid record ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        model = dbHelper.getAdoptionById(adoptionId);
        if (model == null) {
            Toast.makeText(this, "Adoption record not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateFields(model);

        etDate.setOnClickListener(v -> showDatePicker());
        imgAdopter.setOnClickListener(v -> openImagePicker());
        btnUpdate.setOnClickListener(v -> updateRecord());
    }

    private void initializeViews() {
        etName = findViewById(R.id.etadoptionName);
        etAge = findViewById(R.id.etadoptionage);
        etContact = findViewById(R.id.etadoptioncontact);
        etNIC = findViewById(R.id.etadoptionnic);
        etAddress = findViewById(R.id.etadoptionaddress);
        etDate = findViewById(R.id.etadoptiondate);

        rgGender = findViewById(R.id.rgadoptionGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        cbTerms = findViewById(R.id.cbTerms);
        btnUpdate = findViewById(R.id.btneditadoption);

        imgAdopter = findViewById(R.id.imgadoptionphoto);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, day) -> etDate.setText(year + "-" + (month + 1) + "-" + day),
                y, m, d);
        dpd.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgAdopter.setImageBitmap(bitmap);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();
                    model.setAdopterPhoto(imageBytes);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Image loading failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void populateFields(AdoptionModel model) {
        etName.setText(model.getAdopterName());
        etAge.setText(model.getAdopterAge());
        etContact.setText(model.getAdopterContact());
        etNIC.setText(model.getAdopterNIC());
        etAddress.setText(model.getAdopterAddress());
        etDate.setText(model.getAdoptionDate());

        if ("Male".equalsIgnoreCase(model.getAdopterGender())) {
            rbMale.setChecked(true);
        } else if ("Female".equalsIgnoreCase(model.getAdopterGender())) {
            rbFemale.setChecked(true);
        }

        if (model.getAdopterPhoto() != null) {
            Bitmap adopterBitmap = BitmapFactory.decodeByteArray(model.getAdopterPhoto(), 0, model.getAdopterPhoto().length);
            imgAdopter.setImageBitmap(adopterBitmap);
        }
    }

    private void updateRecord() {
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please accept the terms", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etName.getText().toString().trim().isEmpty() ||
                etAge.getText().toString().trim().isEmpty() ||
                etContact.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        model.setAdopterName(etName.getText().toString().trim());
        model.setAdopterAge(etAge.getText().toString().trim());
        model.setAdopterContact(etContact.getText().toString().trim());
        model.setAdopterNIC(etNIC.getText().toString().trim());
        model.setAdopterAddress(etAddress.getText().toString().trim());
        model.setAdoptionDate(etDate.getText().toString().trim());

        int genderId = rgGender.getCheckedRadioButtonId();
        if (genderId != -1) {
            RadioButton rb = findViewById(genderId);
            model.setAdopterGender(rb.getText().toString());
        }

        boolean updated = dbHelper.updateAdoption(model);
        if (updated) {
            Toast.makeText(this, "Adoption updated successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }
}
