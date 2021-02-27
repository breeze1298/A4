package com.example.assignment5;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FormViewHolder extends RecyclerView.ViewHolder {

    TextView name,email,mobile,address;
    ImageView editDetails,deleteDetails;
    CheckBox saveDetails;

    public FormViewHolder(@NonNull View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.fName);
        email=itemView.findViewById(R.id.fEmail);
        mobile=itemView.findViewById(R.id.fMobile);
        address=itemView.findViewById(R.id.fAddress);
        editDetails=itemView.findViewById(R.id.editDetails);
        deleteDetails=itemView.findViewById(R.id.deleteDetails);
        saveDetails=itemView.findViewById(R.id.saveDetails);

    }
}
