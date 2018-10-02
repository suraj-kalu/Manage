package com.examples.surajratnakalu.management;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.surajratnakalu.management.Data.ItemsContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mAddUpdateItemName;
    private EditText mAddUpdateQuantity;
    private EditText mAddUpdateRate;
    private EditText mAddUpdateDate;
    private Button mAddUpdateButton;
    private CalendarView mCalendarView;
    private static final String TAG = "AddUpdateActivity";
    private Uri mReceievedUri;
    private String name = "";
    private double rate = -1;
    private double quantity = -1;
    String date_date = "";
    private Spinner mSpinner;
    private TextView mTextView;
    private String quantity_description;

    public AddUpdateActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddUpdateItemName = findViewById(R.id.add_update_item_name);
        mAddUpdateQuantity = findViewById(R.id.add_update_item_quantity);
        mAddUpdateRate = findViewById(R.id.add_update_item_rate);
        mAddUpdateButton = findViewById(R.id.add_update_button);
        mCalendarView = findViewById(R.id.add_update_item_date);
        mSpinner = findViewById(R.id.spinner_quantity_description);
        mTextView = findViewById(R.id.textView_rate_description);

        Intent intent = getIntent();
        mReceievedUri = intent.getData();
        if (mReceievedUri != null) {
            setTitle(R.string.update_item);
            mAddUpdateButton.setText(R.string.update);
            getSupportLoaderManager().initLoader(1, null, this);

        } else {
            setTitle(R.string.add_item);
        }

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int date_day = dayOfMonth;
                int date_month = month + 1;
                int date_year = year;
//                Log.i(TAG, "onClick: " + date_date);
                date_date = date_day + "/" + date_month + "/" + date_year;
//                Log.i(TAG, "onClick: " + date_date);
            }
        });

        mAddUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrUpdateList();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantity_description = (String) parent.getItemAtPosition(position);
                switch (quantity_description) {
                    case "Kg":
                        mTextView.setText(R.string.per_kg);
                        break;
                    case "Dozen(s)":
                        mTextView.setText(R.string.per_dozen);
                        break;
                    case "Cartoon(s)":
                        mTextView.setText(R.string.per_cartoon);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                quantity_description = "Kg";
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = new String[]{
                ItemsContract.ItemsEntry.ID,
                ItemsContract.ItemsEntry.ITEM_NAME,
                ItemsContract.ItemsEntry.ITEM_QUANTITY,
                ItemsContract.ItemsEntry.ITEM_RATE,
                ItemsContract.ItemsEntry.ITEM_DATE,
                ItemsContract.ItemsEntry.ITEM_TOTAL_COST,
                ItemsContract.ItemsEntry.ITEM_QUANTITY_DESCRIPTION
        };
        return new CursorLoader(this, mReceievedUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            mAddUpdateItemName.setText(cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME)));
            mAddUpdateQuantity.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_QUANTITY))));
            mAddUpdateRate.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_RATE))));
            String selectedItemPosition = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_QUANTITY_DESCRIPTION));
            if (selectedItemPosition.equals("kg")) {
                mSpinner.setSelection(1);
                mTextView.setText(R.string.per_kg);
            } else if (selectedItemPosition.equals("Dozen(s)")) {
                mSpinner.setSelection(2);
                mTextView.setText(R.string.per_dozen);
            } else if (selectedItemPosition.equals("Cartoon(s)")) {
                mSpinner.setSelection(3);
                mTextView.setText(R.string.per_cartoon);
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
            try {
                Date date = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_DATE)));
                long timeInMilliSeconds = date.getTime();
                mCalendarView.setDate(timeInMilliSeconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAddUpdateItemName.setText("");
        mAddUpdateRate.setText("");
        mAddUpdateQuantity.setText("");
    }

    private void saveOrUpdateList() {
        name = mAddUpdateItemName.getText().toString();
        quantity = Double.parseDouble(mAddUpdateQuantity.getText().toString());
        rate = Double.parseDouble(mAddUpdateRate.getText().toString());
        if (date_date.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date_date = simpleDateFormat.format(mCalendarView.getDate());
            Log.i(TAG, "saveOrUpdateList: " + date_date);
        }

        if (!TextUtils.isEmpty(name) && (quantity > 0) && (rate > 0)) {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(ItemsContract.ItemsEntry.ITEM_NAME, name);
            contentValues.put(ItemsContract.ItemsEntry.ITEM_QUANTITY, quantity);
            contentValues.put(ItemsContract.ItemsEntry.ITEM_RATE, rate);
            contentValues.put(ItemsContract.ItemsEntry.ITEM_DATE, date_date);
            contentValues.put(ItemsContract.ItemsEntry.ITEM_TOTAL_COST, quantity * rate);
            contentValues.put(ItemsContract.ItemsEntry.ITEM_QUANTITY_DESCRIPTION, quantity_description);

            if (mReceievedUri == null) {
                AlertDialog.Builder saveDialogBuilder = new AlertDialog.Builder(this);
                saveDialogBuilder.setTitle(R.string.save_item_title);
                saveDialogBuilder.setMessage(R.string.save_message);
                saveDialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getContentResolver().insert(ItemsContract.ItemsEntry.CONTENT_URI, contentValues);
                        finish();
                    }
                });
                saveDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                AlertDialog saveDialog = saveDialogBuilder.create();
                saveDialog.show();
            } else {
                final String selection = ItemsContract.ItemsEntry.ID + "=?";
                final String[] selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(mReceievedUri))
                };
                AlertDialog.Builder updateDialogBuilder = new AlertDialog.Builder(this);
                updateDialogBuilder.setTitle(R.string.update_item_title);
                updateDialogBuilder.setMessage(R.string.update_message);
                updateDialogBuilder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getContentResolver().update(mReceievedUri, contentValues, selection, selectionArgs);
                        finish();
                    }
                });
                updateDialogBuilder.setNegativeButton(getString(R.string.keep_editing), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                AlertDialog updateDialog = updateDialogBuilder.create();
                updateDialog.show();
            }
        } else {
            Toast.makeText(AddUpdateActivity.this, "All fields need to be filled", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder backDialogBuilder = new AlertDialog.Builder(this);
        backDialogBuilder.setTitle(R.string.update_item_title);
        backDialogBuilder.setMessage(R.string.keep_updating_message);
        backDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        backDialogBuilder.setPositiveButton(getString(R.string.keep_editing), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog backDialog = backDialogBuilder.create();
        backDialog.show();
    }
}
