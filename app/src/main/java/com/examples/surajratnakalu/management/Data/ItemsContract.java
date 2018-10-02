package com.examples.surajratnakalu.management.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ItemsContract {
    private ItemsContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.examples.surajratnakalu.management.Data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_URL = "itemlist";

    public static class ItemsEntry implements BaseColumns {
        public static final String TABLE_NAME = "ItemsDatabase";

        public static final String ID = BaseColumns._ID;
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_QUANTITY = "item_quantity";
        public static final String ITEM_RATE = "item_rate";
        public static final String ITEM_DATE = "item_date";
        public static final String ITEM_TOTAL_COST = "item_total_cost";
        public static final String ITEM_QUANTITY_DESCRIPTION = "quantity_description";

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_URL;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_URL;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_URL);
    }
}
