package com.example.assignment5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AddForm extends AppCompatActivity {

    private EditText name, email, mobile, address;
    Button btnSub;

    Sqlite sql;

    String s1, s2, s3, s4;
    private String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FileWriter File1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);


        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        mobile = findViewById(R.id.etMobile);
        address = findViewById(R.id.etAddress);

        btnSub = findViewById(R.id.btnSubmit);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s1 = name.getText().toString();
                s2 = email.getText().toString();
                s3 = mobile.getText().toString();
                s4 = address.getText().toString();

                validate(s1, s2, s3, s4);

            }
        });

    }

    public void validate(String s1, String s2, String s3, String s4) {
        if (s1.isEmpty()) {
            name.setError("Name Field Cannot be Empty");
        } else if (!s2.matches(ePattern)) {
            email.setError("Invalid Email Id");
        } else if (s4.isEmpty()) {
            address.setError("Address Field Cannot be Empty ");
        } else if (s3.length() == 10) {
            //sql.insertFormDetails(s1, s2, s3, s4);
            try {

                JSONObject jsonAdd = new JSONObject();
                jsonAdd.put("name", s1);
                jsonAdd.put("email", s2);
                jsonAdd.put("mobile", s3);
                jsonAdd.put("address", s4);

                JSONObject jsonObj = new JSONObject();
                jsonObj.put("Details", jsonAdd);

                String details = jsonObj.toString();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("details.txt", Context.MODE_PRIVATE));
                outputStreamWriter.append(details);
                outputStreamWriter.close();

            } catch (JSONException | IOException ex) {
                ex.printStackTrace();
            }

            name.setText("");
            email.setText("");
            mobile.setText("");
            address.setText("");

            Toast.makeText(AddForm.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddForm.this, FormDetails.class));
        } else {
            mobile.setError("Invalid Mobile Number");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSub.setVisibility(View.VISIBLE);
    }


}