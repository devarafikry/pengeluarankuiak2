package com.example.devar.pengeluaranku;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by devar on 11/26/2017.
 */

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranViewHolder> {
    Context mContext;
    Cursor mCursor;
    ItemAction mItemAction;
    public PengeluaranAdapter(Context context, ItemAction itemAction){
        this.mContext = context;
        this.mItemAction = itemAction;
    }
    public void swapCursor(Cursor cursor){
        this.mCursor = cursor;
    }

    @Override
    public PengeluaranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new PengeluaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PengeluaranViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int namaColumnIndex = mCursor.getColumnIndex(
                DatabaseContract.PengeluaranEntry.COLUMN_NAMA);
        int deskripsiColumnIndex = mCursor.getColumnIndex(
                DatabaseContract.PengeluaranEntry.COLUMN_DESKRIPSI
        );
        int hargaColumnIndex = mCursor.getColumnIndex(
                DatabaseContract.PengeluaranEntry.COLUMN_HARGA
        );

        int idColumnIndex = mCursor.getColumnIndex(
                DatabaseContract.PengeluaranEntry._ID
        );
        final String id = mCursor.getString(idColumnIndex);

        String nama = mCursor.getString(namaColumnIndex);
        String deskripsi = mCursor.getString(deskripsiColumnIndex);
        String harga = mCursor.getString(hargaColumnIndex);

        holder.tv_name.setText(nama);
        holder.tv_deskripsi.setText(deskripsi);
        holder.tv_harga.setText(harga);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemAction.deletePengeluaran(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        Log.d("COBA", String.valueOf(mCursor.getCount()));
        return mCursor.getCount();
    }
}
