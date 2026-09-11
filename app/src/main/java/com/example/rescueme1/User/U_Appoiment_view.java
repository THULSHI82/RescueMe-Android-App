package com.example.rescueme1.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.AppointmentModel;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class U_Appoiment_view extends AppCompatActivity implements AppointmentAdapter.OnViewMoreClickListener {

    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    private List<AppointmentModel> filteredAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_uappoiment_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.u_appoinmet_recyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);

        int userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("uid", -1);

        if (userId == -1) {
            Toast.makeText(this, "Please login first.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<AppointmentModel> appointmentList = dbHelper.getAppointmentsByUserId(userId);

        filteredAppointments = filterTodayAndFutureAppointments(appointmentList);

        AppointmentAdapter adapter = new AppointmentAdapter(this, filteredAppointments, this);
        recyclerView.setAdapter(adapter);
    }

    private List<AppointmentModel> filterTodayAndFutureAppointments(List<AppointmentModel> allAppointments) {
        List<AppointmentModel> filteredAppointments = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = sdf.format(new Date());

        try {
            Date todayDate = sdf.parse(todayStr);

            for (AppointmentModel appointment : allAppointments) {
                Date appointmentDate = sdf.parse(appointment.getDate());

                if (!appointmentDate.before(todayDate)) {
                    filteredAppointments.add(appointment);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return filteredAppointments;
    }

    @Override
    public void onViewMoreClick(int position) {
        AppointmentModel appointment = filteredAppointments.get(position);

        Intent intent = new Intent(this, U_Appoinmet_ViewMore.class);
        intent.putExtra("appointmentId", appointment.getAppointmentId());
        intent.putExtra("petName", appointment.getPetName());
        intent.putExtra("petCategory", appointment.getPetCategory());
        intent.putExtra("petAge", appointment.getPetAge());
        intent.putExtra("petGender", appointment.getPetGender());
        intent.putExtra("petDescription", appointment.getPetDescription());
        intent.putExtra("petImage", appointment.getPetImageBlob());
        intent.putExtra("shelterName", appointment.getShelterName());
        intent.putExtra("shelterContact", appointment.getShelterContact());
        startActivity(intent);
    }


}
