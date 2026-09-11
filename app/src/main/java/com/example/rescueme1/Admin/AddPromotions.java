package com.example.rescueme1.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPromotions extends AppCompatActivity {

    private ImageView imgPromo;
    private EditText etTitle;
    private Button btnAddPromo;
    private byte[] promoImageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotions);

        imgPromo = findViewById(R.id.imgpetProfile);
        etTitle = findViewById(R.id.ettitle);
        btnAddPromo = findViewById(R.id.btnpromo);

        imgPromo.setOnClickListener(v -> openImageChooser());

        btnAddPromo.setOnClickListener(v -> addPromotion());
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        pickImage.launch(intent);
    }

    ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        imgPromo.setImageBitmap(bitmap);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        promoImageBytes = stream.toByteArray();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void addPromotion() {
        String title = etTitle.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Title is required");
            etTitle.requestFocus();
            return;
        }

        if (promoImageBytes == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        boolean isInserted = dbHelper.insertPromotion(title, promoImageBytes);

        if (isInserted) {
            Toast.makeText(this, "Promotion added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            finish();
        } else {
            Toast.makeText(this, "Failed to add promotion", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etTitle.setText("");
        imgPromo.setImageResource(R.drawable.promo);
        promoImageBytes = null;
    }
}