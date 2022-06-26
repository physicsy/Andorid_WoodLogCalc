package com.example.bajrangbalitimber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Bills(log_SlNo TEXT primary key, log_Name TEXT, log_Length TEXT, log_Girth TEXT, log_rate TEXT, log_CubicFeet TEXT, log_Amount TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE if exists Bills");
    }

    public Boolean insertBillingData(String log_SlNo, String log_Name, String log_Length, String log_Girth, String log_rate, String log_CubicFeet, String log_Amount)
        {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("log_SlNo", log_SlNo);
            contentValues.put("log_Name", log_Name);
            contentValues.put("log_Length", log_Length);
            contentValues.put("log_Width", log_Girth);
            contentValues.put("log_rate", log_rate);
            contentValues.put("log_CubicFeet", log_CubicFeet);
            contentValues.put("log_Amount", log_Amount);
            long result = DB.insert("Bills", null, contentValues);
            if(result==-1)
                {
                    return false;
                }
            else
                {
                    return true;
                }
        }


    public Cursor getBillingData()
        {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("SELECT * FROM Bills", null);
            return cursor;
        }

}
