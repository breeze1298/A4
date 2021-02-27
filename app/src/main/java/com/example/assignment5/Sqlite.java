package com.example.assignment5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqlite  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "DetailsDB";
    private static final String TABLE_FORM = "FormDetails";
    private static final String NAME = "name";
    private static final String EMAIL = "email_Id";
    private static final String MOBILE = "mobile_Number";
    private static final String ADDRESS="address";

    Sqlite(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_DETAILS= "CREATE TABLE "
                + TABLE_FORM + "("
                + NAME +" TEXT UNIQUE ,"
                + EMAIL + " TEXT UNIQUE,"
                + MOBILE + " TEXT ,"
                + ADDRESS + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_DETAILS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORM);
        onCreate(db);

    }

    void insertFormDetails(FormModel formModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME,formModel.getName());
        values.put(EMAIL,formModel.getEmail());
        values.put(MOBILE,formModel.getMobile());
        values.put(ADDRESS,formModel.getAddress());
        db.insert(TABLE_FORM,null,values);

    }

    ArrayList<FormModel> listForm()
    {

        String sql="select * from "+TABLE_FORM;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<FormModel> details=new ArrayList<>();

        Cursor cursor=db.rawQuery(sql,null);
        if (cursor.moveToNext())
        {
            do {
                String name = cursor.getString(0);
                String email = cursor.getString(1);
                String mobile = cursor.getString(2);
                String address = cursor.getString(3);

                details.add(new FormModel(name, email, mobile, address));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return details;
    }


    void updateForm(FormModel formModel)
    {
        ContentValues values=new ContentValues();
        values.put(NAME,formModel.getName());
        values.put(EMAIL,formModel.getEmail());
        values.put(MOBILE,formModel.getMobile());
        values.put(ADDRESS,formModel.getAddress());
        SQLiteDatabase db=this.getWritableDatabase();

        db.update(TABLE_FORM,values, EMAIL+"= ?",new String[]{String.valueOf(formModel.getEmail())});
    }

    void deleteForm(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FORM, EMAIL + " = ?", new String[]{String.valueOf(email)});
    }
}
