package com.example.devar.pengeluaranku;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ItemAction {

    FloatingActionButton btn_add;
    RecyclerView rv_pengeluaran;
    PengeluaranAdapter adapter;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(getAllPengeluaran());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DatabaseHelper(this);
        mDb = mDbHelper.getReadableDatabase();

        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        rv_pengeluaran = (RecyclerView) findViewById(R.id.rv_pengeluaranku);

        adapter = new PengeluaranAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_pengeluaran.setAdapter(adapter);
        rv_pengeluaran.setLayoutManager(linearLayoutManager);

        adapter.swapCursor(getAllPengeluaran());
        adapter.notifyDataSetChanged();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        AddPengeluaranActivity.class);
                startActivity(intent);
            }
        });
    }

    private Cursor getAllPengeluaran() {
        return mDb.query(
                DatabaseContract.PengeluaranEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void deletePengeluaran(final String deletedId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus pengeluaran ini?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                mDb.delete(
                        DatabaseContract.PengeluaranEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{deletedId}
                );
                adapter.swapCursor(getAllPengeluaran());
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
