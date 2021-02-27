package com.example.assignment5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FormDetails extends AppCompatActivity {

    ImageView add,save;
    private Sqlite sqlite;
    String s1,s2,s3,s4;
    private String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_details);

        add=findViewById(R.id.formAdd);
        save=findViewById(R.id.btnSave);

        RecyclerView recyclerView = findViewById(R.id.formDetails);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        sqlite = new Sqlite(this);
        ArrayList<FormModel> allDetails = sqlite.listForm();

        FormAdapter adapter=new FormAdapter(this,allDetails);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormDetails.this,AddForm.class));
            }
        });

    }

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_add_form, null);
        EditText name = subView.findViewById(R.id.etName);
        EditText email = subView.findViewById(R.id.etEmail);
        EditText mobile = subView.findViewById(R.id.etMobile);
        EditText address = subView.findViewById(R.id.etAddress);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Details");
        builder.setView(subView);
        builder.setPositiveButton("Add Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FormDetails.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Boolean error=false;

                s1 = name.getText().toString();
                s2 = email.getText().toString();
                s3 = mobile.getText().toString();
                s4 = address.getText().toString();

                error=true;

                //Validating all the inputs
                if (s1.isEmpty())
                {
                    error=true;
                    name.setError("Name Field Cannot be Empty");
                }
                else if (!s2.matches(ePattern))
                {
                    error=true;
                    email.setError("Invalid Email Id");
                }
                else if (s4.isEmpty())
                {
                    error=true;
                    address.setError("Address Field Cannot be Empty ");
                }else if (s3.length()==10)
                {
                    error=false;

                    FormModel formModel = new FormModel(s1, s2, s3, s4);
                    sqlite.insertFormDetails(formModel);

                    Toast.makeText(FormDetails.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
                else {
                    error=true;
                    mobile.setError("Invalid Mobile Number");
                }
                if (!error)
                {
                    alertDialog.dismiss();
                }


            }
        });


    }
}