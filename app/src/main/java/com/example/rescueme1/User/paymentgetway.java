package com.example.rescueme1.User;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.util.regex.Pattern;

public class paymentgetway extends AppCompatActivity {

    EditText etCardNumber, etDate, etCVV, etCname;
    Button btnpayment2;
    CheckBox checkBox;
    TextView txtamount;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_paymentgetway);

        etCardNumber = findViewById(R.id.etFname);
        etDate = findViewById(R.id.etDate);
        etCVV = findViewById(R.id.etcvv);
        etCname = findViewById(R.id.etCname);
        checkBox = findViewById(R.id.checkBox);
        txtamount = findViewById(R.id.txtAmount);
        btnpayment2 = findViewById(R.id.btnpayment2);

        TextView txtamount = findViewById(R.id.txtAmount);

        String amountStr = getIntent().getStringExtra("amount");

        if (amountStr != null && !amountStr.isEmpty()) {
            txtamount.setText(amountStr + " LKR");
        }

        btnpayment2.setOnClickListener(v -> {
            String cardNumber = etCardNumber.getText().toString().trim();
            String expiry = etDate.getText().toString().trim();
            String cvv = etCVV.getText().toString().trim();
            String cardHolderName = etCname.getText().toString().trim();

            if (!validateCardInputs(cardNumber, expiry, cvv, cardHolderName)) {
                return;
            }

            int userId = getIntent().getIntExtra("user_id", -1);
            int shelterId = getIntent().getIntExtra("shelter_id", -1);
            String fname = getIntent().getStringExtra("fname");
            String lname = getIntent().getStringExtra("lname");
            String remark = getIntent().getStringExtra("remark");

            if (userId != -1 && shelterId != -1 && amountStr != null && !amountStr.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    dbHelper = new DBHelper(this);
                    boolean success = dbHelper.insertDonation(shelterId, userId, fname, lname, amount, remark);

                    if (success) {
                        Toast.makeText(this, "Donation recorded successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to record donation", Toast.LENGTH_SHORT).show();
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Missing or invalid donation details", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean validateCardInputs(String cardNumber, String expiry, String cvv, String name) {
        if (TextUtils.isEmpty(cardNumber) || cardNumber.length() < 12 || cardNumber.length() > 19 || !TextUtils.isDigitsOnly(cardNumber)) {
            etCardNumber.setError("Enter a valid card number");
            return false;
        }

        if (TextUtils.isEmpty(expiry) || !Pattern.matches("(0[1-9]|1[0-2])/\\d{2}", expiry)) {
            etDate.setError("Enter expiry as MM/YY");
            return false;
        }

        if (TextUtils.isEmpty(cvv) || cvv.length() < 3 || cvv.length() > 4 || !TextUtils.isDigitsOnly(cvv)) {
            etCVV.setError("Enter valid CVV");
            return false;
        }

        if (TextUtils.isEmpty(name) || name.length() < 3) {
            etCname.setError("Enter cardholder name");
            return false;
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Please agree to Terms and Conditions", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}