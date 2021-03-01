package com.example.assignment5;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormViewHolder> {


    private Context context;
    private ArrayList<FormModel> listForm;
    private Sqlite mSqlite;
    private String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String s1,s2,s3,s4;


    FormAdapter(Context context,ArrayList<FormModel> listForm)
    {

        this.context=context;
        this.listForm=listForm;
        mSqlite=new Sqlite(context);

    }
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_form,parent,false);
        return new FormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {

        final FormModel formModel=listForm.get(position);
        holder.name.setText(formModel.getName());
        holder.email.setText(formModel.getEmail());
        holder.mobile.setText(formModel.getMobile());
        holder.address.setText(formModel.getAddress());
        holder.editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    editDialog(formModel);
            }
        });

        holder.deleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSqlite.deleteForm(formModel.getEmail().trim());
                Toast.makeText(context, formModel.getName()+" Details Deleted Successfully ", Toast.LENGTH_SHORT).show();
                context.startActivity(((Activity) context).getIntent());

            }
        });

    }


    @Override
    public int getItemCount() {
        return listForm.size();
    }

    public void editDialog(final FormModel formModel)
    {

    LayoutInflater inflater = LayoutInflater.from(context);
    View subView = inflater.inflate(R.layout.activity_add_form, null);
    EditText name = subView.findViewById(R.id.etName);
    EditText email = subView.findViewById(R.id.etEmail);
    EditText mobile = subView.findViewById(R.id.etMobile);
    EditText address = subView.findViewById(R.id.etAddress);

    if (formModel!=null)
    {
        name.setText(formModel.getName());
        email.setText(formModel.getEmail());
        mobile.setText(formModel.getMobile());
        address.setText(formModel.getAddress());
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Details");
        builder.setView(subView);
        builder.setPositiveButton("Edit Details", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
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
                mSqlite.updateForm(formModel);

                Toast.makeText(context, "Data Edited Successfully", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
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


