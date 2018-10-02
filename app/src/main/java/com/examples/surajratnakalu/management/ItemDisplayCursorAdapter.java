package com.examples.surajratnakalu.management;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.examples.surajratnakalu.management.Data.ItemsContract;

public class ItemDisplayCursorAdapter extends CursorAdapter {
    private TextView mDisplayItemName;
    private TextView mDisplayQuantity;
    private TextView mDisplayRate;
    private TextView mDisplayDate;
    private TextView mDisplayCost;
    private TextView mDisplayQuantityDescription;
    private TextView mDisplayRateDescription;

    public ItemDisplayCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_display, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mDisplayItemName = view.findViewById(R.id.display_name);
        mDisplayQuantity = view.findViewById(R.id.display_quantity);
        mDisplayRate = view.findViewById(R.id.display_rate);
        mDisplayDate = view.findViewById(R.id.display_date);
        mDisplayCost = view.findViewById(R.id.display_cost);
        mDisplayQuantityDescription = view.findViewById(R.id.display_quantity_description);
        mDisplayRateDescription = view.findViewById(R.id.display_rate_description);

        mDisplayItemName.setText(cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME)));
        mDisplayQuantity.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_QUANTITY))));
        String rateDescription = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_QUANTITY_DESCRIPTION));
        mDisplayRate.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_RATE))));
        mDisplayDate.setText(cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_DATE)));
        mDisplayCost.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_TOTAL_COST))));
        mDisplayQuantityDescription.setText(rateDescription);
        switch (rateDescription) {
            case "kg":
                mDisplayRateDescription.setText("Pet Kg");
                break;
            case "Dozen(s)":
                mDisplayRateDescription.setText("Pet Dozen");
                break;
            case "Cartoon(s)":
                mDisplayRateDescription.setText("Per Cartoon");
                break;
        }


    }
}
