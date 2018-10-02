package com.examples.surajratnakalu.management.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class ItemsContentProvider extends ContentProvider {

    private ItemsDbHelper mDbHelper;


    private static final int ITEM = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ItemsContract.CONTENT_AUTHORITY, ItemsContract.PATH_URL, ITEM);
        sUriMatcher.addURI(ItemsContract.CONTENT_AUTHORITY, ItemsContract.PATH_URL + "/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemsDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match) {
            case ITEM:
                cursor = sqLiteDatabase.query(ItemsContract.ItemsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = ItemsContract.ItemsEntry.ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(ItemsContract.ItemsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM:
                return ItemsContract.ItemsEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return ItemsContract.ItemsEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM:
                long insertedId = database.insert(ItemsContract.ItemsEntry.TABLE_NAME, null, values);
                Uri newUri = ContentUris.withAppendedId(uri, insertedId);
                getContext().getContentResolver().notifyChange(uri, null);
                return newUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted = 0;
        switch (match) {
            case ITEM:
                rowsDeleted = database.delete(ItemsContract.ItemsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = ItemsContract.ItemsEntry.ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ItemsContract.ItemsEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsDeleted;
        } else {
            return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM:
                rowsUpdated = database.update(ItemsContract.ItemsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = ItemsContract.ItemsEntry.ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = database.update(ItemsContract.ItemsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsUpdated;
        } else {
            return 0;
        }
    }
}
