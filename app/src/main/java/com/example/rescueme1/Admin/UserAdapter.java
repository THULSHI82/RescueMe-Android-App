package com.example.rescueme1.Admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UserModel1;
import com.example.rescueme1.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<UserModel1> userList;
    private DBHelper dbHelper;

    public UserAdapter(Context context, List<UserModel1> userList) {
        this.context = context;
        this.userList = userList;
        this.dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.a_user_cardview, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel1 user = userList.get(position);

        holder.userName.setText(user.getName());
        holder.userEmail.setText(user.getEmail());

        if (user.getProfileImage() != null) {
            holder.userImage.setImageBitmap(BitmapFactory.decodeByteArray(
                    user.getProfileImage(), 0, user.getProfileImage().length));
        }

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, A_User_Edit.class);
            intent.putExtra("USER_ID", user.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void filterList(List<UserModel1> filteredList) {
        userList = filteredList;
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName, userEmail;
        Button btnViewMore;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.useremail);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
        }
    }
}
