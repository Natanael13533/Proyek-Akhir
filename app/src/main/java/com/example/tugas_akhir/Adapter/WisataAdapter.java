package com.example.tugas_akhir.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_akhir.Database.FavDB;
import com.example.tugas_akhir.Model.PopulerItem;
import com.example.tugas_akhir.Model.WisataItem;
import com.example.tugas_akhir.R;

import java.util.ArrayList;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    private ArrayList<WisataItem> wisataItems;
    private Context context;
    private FavDB favDB;

    public WisataAdapter(ArrayList<WisataItem> wisataItems, Context context) {
        this.wisataItems = wisataItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //Buat Tabel
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if(firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tempat_wisata_card_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WisataAdapter.ViewHolder holder, int position) {
        final WisataItem wisataItem = wisataItems.get(position);

        readCursorData(wisataItem, holder);
        holder.wisataImg.setImageResource(wisataItem.getImageResource());
        holder.wisataTitle.setText(wisataItem.getTitle());
        holder.wisataDesc.setText(wisataItem.getDesc());
    }

    @Override
    public int getItemCount() {
        return wisataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView wisataImg;
        TextView wisataTitle, wisataDesc;
        ImageButton wisataBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wisataImg = itemView.findViewById(R.id.wisata_img);
            wisataTitle = itemView.findViewById(R.id.wisata_title);
            wisataDesc = itemView.findViewById(R.id.wisata_desc);
            wisataBtn = itemView.findViewById(R.id.wisata_btn);

            wisataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    WisataItem wisataItem = wisataItems.get(position);

                    if(wisataItem.getFavStatus().equals("0")) {
                        wisataItem.setFavStatus("1");
                        favDB.InsertIntoDatabase(wisataItem.getTitle(), wisataItem.getImageResource(), wisataItem.getDesc(), wisataItem.getKey_id(), wisataItem.getFavStatus());

                        wisataBtn.setBackgroundResource(R.drawable.ic_favorite_red);
                    }
                    else {
                        wisataItem.setFavStatus("0");
                        favDB.remove_fav(wisataItem.getKey_id());
                        wisataBtn.setBackgroundResource(R.drawable.ic_favorite);
                    }
                }
            });
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(WisataItem wisataItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(wisataItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while(cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                wisataItem.setFavStatus(item_fav_status);

                if(item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.wisataBtn.setBackgroundResource(R.drawable.ic_favorite_red);
                }
                else if (item_fav_status != null && item_fav_status.equals("0")){
                    viewHolder.wisataBtn.setBackgroundResource(R.drawable.ic_favorite);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }
}
