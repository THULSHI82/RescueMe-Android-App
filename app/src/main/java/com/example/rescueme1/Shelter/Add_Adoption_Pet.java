package com.example.rescueme1.Shelter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.PetModel;  // SPetModel wenuwata PetModel import ekak
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_Adoption_Pet extends AppCompatActivity {

    private static final int PICK_ADOPTER_IMAGE = 101;

    private Spinner spinnerPetId;
    private ImageView imgpetProfile, imgadoptionphoto;
    private TextView etpetname, etpetcategory, etpetage, etpetgender, etpetdescription;
    private EditText etadoptionName, etadoptionage, etadoptioncontact, etadoptionnic, etadoptionaddress, etadoptiondate;
    private RadioGroup rgadoptionGender;
    private RadioButton rbMale, rbFemale;
    private CheckBox cbTerms;
    private Button btnadoption;

    private DBHelper dbHelper;
    private List<PetModel> petList;

    private int currentShelterId = -1;
    private byte[] adopterPhotoBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adoption_pet);

        initViews();
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("shelter_id")) {
            currentShelterId = intent.getIntExtra("shelter_id", -1);
        }

        if (currentShelterId == -1) {
            SharedPreferences prefs = getSharedPreferences("shelter_data", MODE_PRIVATE);
            currentShelterId = prefs.getInt("sid", -1);
        }

        if (currentShelterId == -1) {
            Toast.makeText(this, "Shelter ID not found. Please login again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        loadPets();
        etadoptiondate.setOnClickListener(v -> showDatePicker());
        btnadoption.setOnClickListener(v -> processAdoption());
        imgadoptionphoto.setOnClickListener(v -> selectAdopterImage());
    }

    private void initViews() {
        spinnerPetId = findViewById(R.id.spinnerPetId);
        imgpetProfile = findViewById(R.id.imgpetProfile);
        imgadoptionphoto = findViewById(R.id.imgadoptionphoto);

        etpetname = findViewById(R.id.tvpetname);
        etpetcategory = findViewById(R.id.tvpetcategory);
        etpetage = findViewById(R.id.tvpetage);
        etpetgender = findViewById(R.id.tvpetgender);
        etpetdescription = findViewById(R.id.tvpetdescription);

        etadoptionName = findViewById(R.id.etadoptionName);
        etadoptionage = findViewById(R.id.etadoptionage);
        etadoptioncontact = findViewById(R.id.etadoptioncontact);
        etadoptionnic = findViewById(R.id.etadoptionnic);
        etadoptionaddress = findViewById(R.id.etadoptionaddress);
        etadoptiondate = findViewById(R.id.etadoptiondate);

        rgadoptionGender = findViewById(R.id.rgadoptionGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        cbTerms = findViewById(R.id.cbTerms);
        btnadoption = findViewById(R.id.btnadoption);
    }

    private void loadPets() {
        petList = dbHelper.getUPetsByShelterId(currentShelterId);
        if (petList == null) petList = new ArrayList<>();

        PetModel selectPet = new PetModel();
        selectPet.setPetName("Select Pet");
        selectPet.setPetId(-1);
        petList.add(0, selectPet);

        ArrayAdapter<PetModel> adapter = new ArrayAdapter<PetModel>(
                this, android.R.layout.simple_spinner_item, petList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                PetModel pet = petList.get(position);
                view.setText(pet.getPetName() + (pet.getPetId() == -1 ? "" : " (ID: " + pet.getPetId() + ")"));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                PetModel pet = petList.get(position);
                view.setText(pet.getPetName() + (pet.getPetId() == -1 ? "" : " (ID: " + pet.getPetId() + ")"));
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetId.setAdapter(adapter);
        spinnerPetId.setSelection(0);

        if (petList.size() <= 1) {
            spinnerPetId.setEnabled(false);
            etpetname.setText("No pets available for adoption");
            imgpetProfile.setImageResource(R.drawable.add_pet);
        }

        spinnerPetId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (petList.get(position).getPetId() == -1) clearPetDetails();
                else updatePetDetails(petList.get(position));
            }

            @Override public void onNothingSelected(AdapterView<?> parent) { clearPetDetails(); }
        });
    }

    private void updatePetDetails(PetModel pet) {
        etpetname.setText(pet.getPetName());
        etpetcategory.setText(pet.getCategory());
        etpetage.setText(pet.getPetAge());
        etpetgender.setText(pet.getPetGender());
        etpetdescription.setText(pet.getPetDescription());

        if (pet.getProfileImage() != null && pet.getProfileImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(pet.getProfileImage(), 0, pet.getProfileImage().length);
            imgpetProfile.setImageBitmap(bitmap);
        } else {
            imgpetProfile.setImageResource(R.drawable.add_pet);
        }
    }

    private void clearPetDetails() {
        etpetname.setText(""); etpetcategory.setText(""); etpetage.setText("");
        etpetgender.setText(""); etpetdescription.setText("");
        imgpetProfile.setImageResource(R.drawable.add_pet);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR), m = calendar.get(Calendar.MONTH), d = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) ->
                etadoptiondate.setText(year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day)),
                y, m, d);
        dialog.show();
    }

    private void selectAdopterImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_ADOPTER_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_ADOPTER_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgadoptionphoto.setImageBitmap(selectedBitmap);
                adopterPhotoBytes = bitmapToByteArray(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Image load failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    private void processAdoption() {
        int selectedPetPosition = spinnerPetId.getSelectedItemPosition();
        if (selectedPetPosition <= 0) {
            Toast.makeText(this, "Please select a pet", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etadoptionName.getText().toString().trim();
        String ageStr = etadoptionage.getText().toString().trim();
        String contact = etadoptioncontact.getText().toString().trim();
        String nic = etadoptionnic.getText().toString().trim();
        String address = etadoptionaddress.getText().toString().trim();
        String date = etadoptiondate.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty() || contact.isEmpty() || nic.isEmpty() || address.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!name.matches("^[A-Za-z\\s]{3,}$")) {
            Toast.makeText(this, "Enter valid name (min 3 letters)", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
            return;
        }
        if (age < 10 || age > 100) {
            Toast.makeText(this, "Age should be between 10 and 100", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contact.matches("^(0|94)?[7][0-9]{8}$")) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(nic.matches("^[0-9]{9}[vVxX]$") || nic.matches("^[0-9]{12}$"))) {
            Toast.makeText(this, "Invalid NIC number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (address.length() < 5) {
            Toast.makeText(this, "Enter a valid address", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedGenderId = rgadoptionGender.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
            return;
        }
        String gender = (selectedGenderId == R.id.rbMale) ? "Male" : "Female";

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Agree to Terms & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adopterPhotoBytes == null) {
            Toast.makeText(this, "Please upload a photo of the adopter", Toast.LENGTH_SHORT).show();
            return;
        }

        PetModel selectedPet = petList.get(selectedPetPosition);

        boolean inserted = dbHelper.insertAdoptionAndDeletePet(
                selectedPet, adopterPhotoBytes, name, ageStr, gender, contact, nic, address, date, currentShelterId
        );

        if (inserted) {
            Toast.makeText(this, "Adoption successful!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save adoption", Toast.LENGTH_LONG).show();
        }
    }

}
