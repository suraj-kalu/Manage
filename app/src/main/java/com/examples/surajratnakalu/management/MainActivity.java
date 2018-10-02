package com.examples.surajratnakalu.management;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.examples.surajratnakalu.management.Data.ItemsContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ItemDisplayCursorAdapter mCursorAdapter;
    private FloatingActionButton fab;
    private SwipeMenuListView mListView;
    private  long getId = -1;
    private boolean showMenu = false;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                startActivity(intent);
            }
        });

        mListView = findViewById(R.id.list_view);

        mCursorAdapter = new ItemDisplayCursorAdapter(this, null, 0);
        mListView.setAdapter(mCursorAdapter);
        getSupportLoaderManager().initLoader(0, null, this);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick: item clicked");
//                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
//                Uri contentUri = ContentUris.withAppendedId(ItemsContract.ItemsEntry.CONTENT_URI, id);
//                intent.setData(contentUri);
//                startActivity(intent);
//            }
//        });
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                editItem.setIcon(R.drawable.baseline_edit_24);
                editItem.setWidth(300);
                editItem.setBackground(new ColorDrawable(Color.rgb(104, 153, 232)));
                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setIcon(R.drawable.baseline_delete_24);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(226, 63, 85)));

                deleteItem.setWidth(300);
                menu.addMenuItem(deleteItem);
            }

        };

        mListView.setMenuCreator(swipeMenuCreator);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getId = id;
                showMenu = true;
            }
        };
        mListView.setOnItemClickListener(listener);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(showMenu) {
                    switch (index) {
                        case 0:

                            Log.d(TAG, "onItemClick: item clicked");
                            Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                            Uri contentUri = ContentUris.withAppendedId(ItemsContract.ItemsEntry.CONTENT_URI, getId);
                            intent.setData(contentUri);
                            startActivity(intent);
                            break;
                        case 1:

                            Log.d(TAG, "onMenuItemClick: deleted");
                            final AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            deleteDialogBuilder.setTitle(R.string.delete_item);
                            deleteDialogBuilder.setMessage(R.string.delete_message);
                            deleteDialogBuilder.setPositiveButton(R.string.delete_item, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri contentUris = ContentUris.withAppendedId(ItemsContract.ItemsEntry.CONTENT_URI, getId);
                                    int rowsDeleted = getContentResolver().delete(contentUris, null, null);
                                    if (rowsDeleted != 0) {
                                        Toast.makeText(MainActivity.this, "Item successfully deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            deleteDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(dialog != null){
                                        dialog.dismiss();
                                    }
                                }
                            });
                            AlertDialog deleteDialog = deleteDialogBuilder.create();
                            deleteDialog.show();

                            break;
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Please select the item list to register for taking action", Toast.LENGTH_SHORT).show();
                }
                showMenu = false;
                return true;
            }
        });

            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = new String[]{ItemsContract.ItemsEntry.ID,
                ItemsContract.ItemsEntry.ITEM_NAME,
                ItemsContract.ItemsEntry.ITEM_QUANTITY,
                ItemsContract.ItemsEntry.ITEM_RATE,
                ItemsContract.ItemsEntry.ITEM_DATE,
                ItemsContract.ItemsEntry.ITEM_TOTAL_COST,
                ItemsContract.ItemsEntry.ITEM_QUANTITY_DESCRIPTION};
        return new CursorLoader(this, ItemsContract.ItemsEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
