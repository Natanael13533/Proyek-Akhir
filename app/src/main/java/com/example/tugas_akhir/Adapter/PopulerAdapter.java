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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas_akhir.Database.FavDB;
import com.example.tugas_akhir.Model.PopulerItem;
import com.example.tugas_akhir.R;

import java.util.ArrayList;

public class PopulerAdapter extends RecyclerView.Adapter<PopulerAdapter.ViewHolder> {

    private ArrayList<PopulerItem> populerItems;
    private Context context;
    private FavDB favDB;

    public PopulerAdapter(ArrayList<PopulerItem> populerItems, Context context) {
        this.populerItems = populerItems;
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.populer_card_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopulerAdapter.ViewHolder holder, int position) {
        final PopulerItem populerItem = populerItems.get(position);

        readCursorData(populerItem, holder);
        holder.populerImg.setImageResource(populerItem.getImageResource());
        holder.populerTitle.setText(populerItem.getTitle());
        holder.populerDesc.setText(populerItem.getDesc());
    }

    @Override
    public int getItemCount() {
        return populerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView populerImg;
        TextView populerTitle, populerDesc;
        ImageButton populerBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            populerImg = itemView.findViewById(R.id.populer_image);
            populerTitle = itemView.findViewById(R.id.populer_title);
            populerDesc = itemView.findViewById(R.id.populer_desc);
            populerBtn = itemView.findViewById(R.id.populer_btn);

            populerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PopulerItem populerItem = populerItems.get(position);

                    if(populerItem.getFavStatus().equals("0")) {
                        populerItem.setFavStatus("1");
                        favDB.InsertIntoDatabase(populerItem.getTitle(), populerItem.getImageResource(), populerItem.getDesc(), populerItem.getKey_id(), populerItem.getFavStatus());

                        populerBtn.setBackgroundResource(R.drawable.ic_favorite_red);
                    }
                    else {
                        populerItem.setFavStatus("0");
                        favDB.remove_fav(populerItem.getKey_id());
                        populerBtn.setBackgroundResource(R.drawable.ic_favorite);
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

    private void readCursorData(PopulerItem populerItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(populerItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                populerItem.setFavStatus(item_fav_status);

                if(item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.populerBtn.setBackgroundResource(R.drawable.ic_favorite_red);
                }
                else if(item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.populerBtn.setBackgroundResource(R.drawable.ic_favorite);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }
}
