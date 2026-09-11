package com.example.rescueme1.User;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyPetFragment extends Fragment {

    EditText etpetName, etDob, etVac;
    AutoCompleteTextView petCategory;
    RadioGroup rgVac;
    RadioButton rbYes, rbNo;
    CheckBox checkRabies, checkDistemper, checkParvovirus;
    Switch switchExOn, switchMealOn;
    Button btnAddmypet, btnMypetedit, btnReservation, btnVetservise;
    ImageView imgpetProfile;
    TextView tvDaysRemaining;
    private byte[] imageBytes;
    DBHelper dbHelper;
    int uid;
    int petId;
    boolean isEditMode = false;

    private boolean isSwitchExOnUserAction = true;
    private boolean isSwitchMealOnUserAction = true;

    private int exerciseHour = -1, exerciseMinute = -1;
    private int mealHour = -1, mealMinute = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_pet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());

        imgpetProfile = view.findViewById(R.id.imgpetProfile);
        etpetName = view.findViewById(R.id.etpetName);
        etDob = view.findViewById(R.id.etDob);
        etVac = view.findViewById(R.id.etVac);
        petCategory = view.findViewById(R.id.petcategory);
        rgVac = view.findViewById(R.id.rgVac);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        checkRabies = view.findViewById(R.id.checkRabies);
        checkDistemper = view.findViewById(R.id.checkDistemper);
        checkParvovirus = view.findViewById(R.id.checkParvovirus);
        switchExOn = view.findViewById(R.id.switchExOn);
        switchMealOn = view.findViewById(R.id.switchMealOn);
        btnAddmypet = view.findViewById(R.id.btnAddmypet);
        btnMypetedit = view.findViewById(R.id.btnMypetedit);
        btnReservation = view.findViewById(R.id.btnReservation);
        tvDaysRemaining = view.findViewById(R.id.tvDaysRemaining);
        btnVetservise = view.findViewById(R.id.btnVetservise);

        imgpetProfile.setOnClickListener(v -> selectImageFromGallery());

        ImageView imgbell3 = view.findViewById(R.id.imgbell3);
        imgbell3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Messages.class);
            startActivity(intent);
        });

        String[] petCategories = getResources().getStringArray(R.array.petcategory);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, petCategories);
        petCategory.setAdapter(categoryAdapter);

        petCategory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) petCategory.showDropDown();
        });

        etDob.setOnClickListener(v -> showDatePicker(etDob));
        etVac.setOnClickListener(v -> showDatePickerWithNextYear(etVac));

        rbYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkRabies.setVisibility(View.VISIBLE);
                checkDistemper.setVisibility(View.VISIBLE);
                checkParvovirus.setVisibility(View.VISIBLE);
                etVac.setVisibility(View.VISIBLE);
                tvDaysRemaining.setVisibility(View.VISIBLE);
            }
        });

        rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkRabies.setVisibility(View.GONE);
                checkDistemper.setVisibility(View.GONE);
                checkParvovirus.setVisibility(View.GONE);
                etVac.setVisibility(View.GONE);
                tvDaysRemaining.setVisibility(View.GONE);
            }
        });

        switchExOn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isSwitchExOnUserAction && isChecked) {
                showTimePicker("Exercise");
            }
        });

        switchMealOn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isSwitchMealOnUserAction && isChecked) {
                showTimePicker("Meal");
            }
        });

        btnReservation.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReservationActivity.class);
            startActivity(intent);
        });

        btnVetservise.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VetserviceActivity.class);
            startActivity(intent);
        });

        btnAddmypet.setOnClickListener(v -> {

            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            int userId = prefs.getInt("uid", -1);
            if (userId == -1) {
                Toast.makeText(getContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = etpetName.getText().toString();
            String category = petCategory.getText().toString();
            String dob = etDob.getText().toString();
            String vaccinated = rbYes.isChecked() ? "Yes" : "No";

            String vaccines = "";
            if (rbYes.isChecked()) {
                if (checkRabies.isChecked()) vaccines += "Rabies,";
                if (checkDistemper.isChecked()) vaccines += "Distemper,";
                if (checkParvovirus.isChecked()) vaccines += "Parvovirus,";
                if (vaccines.endsWith(",")) vaccines = vaccines.substring(0, vaccines.length() - 1);
            }

            String vacDate = etVac.getText().toString();
            int exRem = switchExOn.isChecked() ? 1 : 0;
            int mealRem = switchMealOn.isChecked() ? 1 : 0;

            if (name.isEmpty() || category.isEmpty() || dob.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.insertMyPet(userId, name, category, dob, vaccinated, vaccines, vacDate, exRem, mealRem, imageBytes);

            if (success) {
                Toast.makeText(getContext(), "Pet saved successfully!", Toast.LENGTH_SHORT).show();
                disableAllInputs();
            } else {
                Toast.makeText(getContext(), "Error saving pet!", Toast.LENGTH_SHORT).show();
            }
        });

        btnMypetedit.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            int userId = prefs.getInt("uid", -1);
            if (!isEditMode) {
                enableAllInputs();
                btnMypetedit.setText("Save");
                isEditMode = true;
            } else {
                String name = etpetName.getText().toString();
                String category = petCategory.getText().toString();
                String dob = etDob.getText().toString();
                String vaccinated = rbYes.isChecked() ? "Yes" : "No";

                String vaccines = "";
                if (rbYes.isChecked()) {
                    if (checkRabies.isChecked()) vaccines += "Rabies,";
                    if (checkDistemper.isChecked()) vaccines += "Distemper,";
                    if (checkParvovirus.isChecked()) vaccines += "Parvovirus,";
                    if (vaccines.endsWith(",")) vaccines = vaccines.substring(0, vaccines.length() - 1);
                }

                String vacDate = etVac.getText().toString();
                int exRem = switchExOn.isChecked() ? 1 : 0;
                int mealRem = switchMealOn.isChecked() ? 1 : 0;

                boolean updated = dbHelper.updateMyPet(petId, userId, name, category, dob, vaccinated, vaccines, vacDate, exRem, mealRem, imageBytes);
                if (updated) {
                    Toast.makeText(getContext(), "Pet updated!", Toast.LENGTH_SHORT).show();
                    disableAllInputs();
                    btnMypetedit.setText("Edit");
                    isEditMode = false;
                } else {
                    Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadPetData();
    }

    private void showTimePicker(String type, TimePickedCallback callback) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute1) -> {
            if (callback != null) callback.onTimePicked(hourOfDay, minute1);
        }, hour, minute, true);

        dialog.show();
    }

    interface TimePickedCallback {
        void onTimePicked(int hour, int minute);
    }

    private void scheduleReminder(String channelId, int notificationId, String title, String content, int hour, int minute) {
        createNotificationChannel(channelId);

        Intent intent = new Intent(getContext(), ReminderReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("channelId", channelId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private void cancelReminder(int notificationId) {
        Intent intent = new Intent(getContext(), ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private void createNotificationChannel(String channelId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Pet Channel";
            String description = "Reminders for pets";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        switchExOn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isSwitchExOnUserAction && isChecked) {
                showTimePicker("Exercise", (hour, minute) -> {
                    exerciseHour = hour;
                    exerciseMinute = minute;
                    scheduleReminder("exercise_channel", 1001, "Exercise Reminder", "Time to exercise your pet!", hour, minute);
                });
            } else if (!isChecked) {
                cancelReminder(1001);
                exerciseHour = -1;
                exerciseMinute = -1;
            }
        });

        // Meal Switch
        switchMealOn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isSwitchMealOnUserAction && isChecked) {
                showTimePicker("Meal", (hour, minute) -> {
                    mealHour = hour;
                    mealMinute = minute;
                    scheduleReminder("meal_channel", 1002, "Meal Reminder", "Time to feed your pet!", hour, minute);
                });
            } else if (!isChecked) {
                cancelReminder(1002);
                mealHour = -1;
                mealMinute = -1;
            }
        });

    }


    private void enableAllInputs() {
        etpetName.setEnabled(true);
        petCategory.setEnabled(true);
        etDob.setEnabled(true);
        etVac.setEnabled(true);
        rbYes.setEnabled(true);
        rbNo.setEnabled(true);
        checkRabies.setEnabled(true);
        checkDistemper.setEnabled(true);
        checkParvovirus.setEnabled(true);
        switchExOn.setEnabled(true);
        switchMealOn.setEnabled(true);
        imgpetProfile.setEnabled(true);
        btnAddmypet.setEnabled(true);
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Pet Image"), 100);
    }

    private void showDatePicker(EditText targetField) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            targetField.setText(selectedDate);
        }, year, month, day);
        dpd.show();
    }

    private void showDatePickerWithNextYear(EditText targetField) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            targetField.setText(selectedDate);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date selected = sdf.parse(selectedDate);

                Calendar nextYearCal = Calendar.getInstance();
                nextYearCal.setTime(selected);
                nextYearCal.add(Calendar.YEAR, 1);
                Date nextYearDate = nextYearCal.getTime();

                long diffMillis = nextYearDate.getTime() - System.currentTimeMillis();
                long remainingDays = TimeUnit.MILLISECONDS.toDays(diffMillis);

                if (remainingDays > 0) {
                    tvDaysRemaining.setText("Next vaccine due in " + remainingDays + " days");

                    if (remainingDays > 10) {
                        tvDaysRemaining.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    } else {
                        tvDaysRemaining.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }

                } else {
                    tvDaysRemaining.setText("Next vaccine is overdue");
                    tvDaysRemaining.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }, year, month, day);
        dpd.show();
    }

    private void showTimePicker(String type) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute1) -> {
            String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
            Toast.makeText(getContext(), type + " time set to: " + formattedTime, Toast.LENGTH_SHORT).show();
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void loadPetData() {
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("uid", -1);
        if (userId == -1) return;

        DBHelper.Pet pet = dbHelper.getLatestPetByUserId(userId);
        if (pet != null) {
            petId = pet.id; // for future edit
            etpetName.setText(pet.name);
            petCategory.setText(pet.category);
            etDob.setText(pet.dob);
            etVac.setText(pet.vaccineDate);

            if ("Yes".equals(pet.vaccinated)) {
                rbYes.setChecked(true);
                checkRabies.setVisibility(View.VISIBLE);
                checkDistemper.setVisibility(View.VISIBLE);
                checkParvovirus.setVisibility(View.VISIBLE);
                etVac.setVisibility(View.VISIBLE);
                tvDaysRemaining.setVisibility(View.VISIBLE);

                if (pet.vaccines.contains("Rabies")) checkRabies.setChecked(true);
                if (pet.vaccines.contains("Distemper")) checkDistemper.setChecked(true);
                if (pet.vaccines.contains("Parvovirus")) checkParvovirus.setChecked(true);
                calculateDaysRemaining(pet.vaccineDate);
            } else {
                rbNo.setChecked(true);
                checkRabies.setVisibility(View.GONE);
                checkDistemper.setVisibility(View.GONE);
                checkParvovirus.setVisibility(View.GONE);
                etVac.setVisibility(View.GONE);
                tvDaysRemaining.setVisibility(View.GONE);
            }

            isSwitchExOnUserAction = false;
            isSwitchMealOnUserAction = false;

            switchExOn.setChecked(pet.exerciseReminder == 1);
            switchMealOn.setChecked(pet.mealReminder == 1);

            isSwitchExOnUserAction = true;
            isSwitchMealOnUserAction = true;

            if (pet.image != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(pet.image, 0, pet.image.length);
                imgpetProfile.setImageBitmap(bmp);
                imageBytes = pet.image;
            }

            disableAllInputs();
        }
    }

    private void calculateDaysRemaining(String selectedDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date selected = sdf.parse(selectedDate);

            Calendar nextYearCal = Calendar.getInstance();
            nextYearCal.setTime(selected);
            nextYearCal.add(Calendar.YEAR, 1);
            Date nextYearDate = nextYearCal.getTime();

            long diffMillis = nextYearDate.getTime() - System.currentTimeMillis();
            long remainingDays = TimeUnit.MILLISECONDS.toDays(diffMillis);

            if (remainingDays > 0) {
                tvDaysRemaining.setText("Next vaccine due in " + remainingDays + " days");
                tvDaysRemaining.setTextColor(getResources().getColor(
                        remainingDays > 10 ? android.R.color.holo_green_dark : android.R.color.holo_red_dark
                ));
            } else {
                tvDaysRemaining.setText("Next vaccine is overdue");
                tvDaysRemaining.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imgpetProfile.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void disableAllInputs() {
        etpetName.setEnabled(false);
        petCategory.setEnabled(false);
        etDob.setEnabled(false);
        etVac.setEnabled(false);

        rbYes.setEnabled(false);
        rbNo.setEnabled(false);
        checkRabies.setEnabled(false);
        checkDistemper.setEnabled(false);
        checkParvovirus.setEnabled(false);

        switchExOn.setEnabled(false);
        switchMealOn.setEnabled(false);

        imgpetProfile.setEnabled(false);
        btnAddmypet.setEnabled(false);
    }
}
