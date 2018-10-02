package com.examples.surajratnakalu.management.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemsDbHelper extends SQLiteOpenHelper {
    private static final int VERSION_ID = 1;
    private static final String DATABASE_NAME = "items-db";

    public ItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                ItemsContract.ItemsEntry.TABLE_NAME + "( " +
                ItemsContract.ItemsEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemsContract.ItemsEntry.ITEM_NAME + " TEXT NOT NULL, " +
                ItemsContract.ItemsEntry.ITEM_QUANTITY + " DOUBLE NOT NULL, " +
                ItemsContract.ItemsEntry.ITEM_RATE + " DOUBLE NOT NULL, " +
                ItemsContract.ItemsEntry.ITEM_DATE + " TEXT NOT NULL, " +
                ItemsContract.ItemsEntry.ITEM_TOTAL_COST + " DOUBLE NOT NULL," +
                ItemsContract.ItemsEntry.ITEM_QUANTITY_DESCRIPTION + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
