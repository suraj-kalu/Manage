//package com.examples.surajratnakalu.management;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Adapter;
//import android.widget.CursorAdapter;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import java.util.Date;
//import java.util.List;
//
//public abstract class ItemDisplayRecyclerViewAdapter extends Adapter<ItemDisplayRecyclerViewAdapter.ListItemViewHolder> {
//
////    private List<Item> mItemList;
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    CursorAdapter mCursorAdapter;
//    public ItemDisplayRecyclerViewAdapter(Context context, Cursor cursor) {
//
//
//    }
//
////    @NonNull
////    @Override
////    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_display, viewGroup, false);
////        ListItemViewHolder listItemViewHolder = new ListItemViewHolder(view);
////        return listItemViewHolder;
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
////        Item currentItem = mItemList.get(i);
////
////        listItemViewHolder.mDisplayItemName.setText(currentItem.getClassItemName());
////        listItemViewHolder.mDisplayQuantity.setText(String.valueOf(currentItem.getClassQuantity()));
////        listItemViewHolder.mDisplayRate.setText(String.valueOf(currentItem.getClassRate()));
////        listItemViewHolder.mDisplayDate.setText(currentItem.getClassDate());
////        listItemViewHolder.mDisplayCost.setText(String.valueOf(currentItem.getClassTotalCost()));
////    }
////
////    @Override
////    public int getItemCount() {
////        if (mItemList.size() > 0) {
////            return mItemList.size();
////        }
////        return 0;
////    }
//
//    public class ListItemViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView mDisplayItemName;
//        private TextView mDisplayQuantity;
//        private TextView mDisplayRate;
//        private TextView mDisplayDate;
//        private TextView mDisplayCost;
//
//        public ListItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            mDisplayItemName = itemView.findViewById(R.id.display_name);
//            mDisplayQuantity = itemView.findViewById(R.id.display_quantity);
//            mDisplayRate = itemView.findViewById(R.id.display_rate);
//            mDisplayDate = itemView.findViewById(R.id.display_date);
//            mDisplayCost = itemView.findViewById(R.id.display_cost);
//        }
//    }
//}
