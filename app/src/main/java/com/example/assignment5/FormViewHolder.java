package com.example.assignment5;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FormViewHolder extends RecyclerView.ViewHolder {

    TextView name,email,mobile,address;

    public FormViewHolder(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.fName);
        email=itemView.findViewById(R.id.fEmail);
        mobile=itemView.findViewById(R.id.fMobile);
        address=itemView.findViewById(R.id.fAddress);
    }
}
