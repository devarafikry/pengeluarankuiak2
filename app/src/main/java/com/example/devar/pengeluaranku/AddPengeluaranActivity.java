package com.example.devar.pengeluaranku;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPengeluaranActivity extends AppCompatActivity {

    View rootView;
    Button btn_simpan;
    EditText edt_nama, edt_deskripsi, edt_harga;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pengeluaran);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Add Pengeluaran Activity");
        }

        dbHelper = new DatabaseHelper(this);
        mDb = dbHelper.getWritableDatabase();

        rootView = (View) findViewById(R.id.rootView);
        btn_simpan = (Button) findViewById(R.id.btn_simpan);
        edt_nama = (EditText) findViewById(R.id.nama);
        edt_deskripsi = (EditText) findViewById(R.id.deskripsi);
        edt_harga = (EditText) findViewById(R.id.harga);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDatabase();
            }
        });
    }

    private void saveToDatabase(){
        String nama = edt_nama.getText().toString();
        String deskripsi = edt_deskripsi.getText().toString();
        String harga = edt_harga.getText().toString();

        if(!TextUtils.isEmpty(nama)
                && !TextUtils.isEmpty(deskripsi)
                && !TextUtils.isEmpty(harga)){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PengeluaranEntry.COLUMN_NAMA, nama);
            contentValues.put(DatabaseContract.PengeluaranEntry.COLUMN_DESKRIPSI, deskripsi);
            contentValues.put(DatabaseContract.PengeluaranEntry.COLUMN_HARGA, harga);

            long result = mDb.insert(
                    DatabaseContract.PengeluaranEntry.TABLE_NAME,
                    null,
                    contentValues
            );
            if(result >0){
                finish();
            } else{
                Snackbar snackbar = Snackbar.make(
                        rootView,
                        "Gagal",
                        Snackbar.LENGTH_LONG
                );
            }

        } else{
            Snackbar snackbar = Snackbar.make(
                    rootView,
                    "Silahkan isi form terlebih dahulu.",
                    Snackbar.LENGTH_LONG
            );
            snackbar.show();
        }
    }
}
