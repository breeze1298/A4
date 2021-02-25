package com.example.assignment5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

public class FormAdapter extends RecyclerView.Adapter<FormViewHolder> {


    private Context context;
    private ArrayList<FormModel> listForm;
    private Sqlite mSqlite;


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

    }


    @Override
    public int getItemCount() {
        return listForm.size();
    }
}
