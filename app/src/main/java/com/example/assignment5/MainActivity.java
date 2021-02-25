package com.example.assignment5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private Button btnLogin;
    private CheckBox checkBox;
    private String ePattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail=findViewById(R.id.etEmailId);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        checkBox=findViewById(R.id.checkR);

        //Checking for Data if Available in Shared Preference
        SharedPreferences sharedPreferences=getSharedPreferences("Login_Details",MODE_PRIVATE);
        String s1=sharedPreferences.getString("Email_Id","");
        String s2=sharedPreferences.getString("Password","");

        if (s1 !=null && s2 !=null)
        {
            //Here the Values would be set
            etEmail.setText(s1);
            etPassword.setText(s2);
        }

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sEmail = etEmail.getText().toString();
                    String sPassword = etPassword.getText().toString();

                    if (!sEmail.matches(ePattern)) {
                        etEmail.setError("Invalid Email Id");

                    } else if (sPassword.length() < 6) {
                        etPassword.setError("Password too short ");
                    } else if (checkBox.isChecked()) {
                        sharedLogin(1, sEmail, sPassword);
                    } else {
                        sharedLogin(0, sEmail, sPassword);
                    }

                }
            });
        }


    private void sharedLogin(int flag,String email,String pass)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("Login_Details",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        if (flag==0)
        {
            //Deleting the value's from SharedPreference As user Didn't click Remember Me
            editor.clear();
            editor.commit();
            etEmail.setText("");
            etPassword.setText("");
            Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, FormDetails.class));
        }
        else
        {
            //Here flag=1 and Data is Saved in Shared Preference as User Clicked on Remember Me
            editor.putString("Email_Id",email);
            editor.putString("Password",pass);
            editor.apply();
            Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, FormDetails.class));
        }
    }

}