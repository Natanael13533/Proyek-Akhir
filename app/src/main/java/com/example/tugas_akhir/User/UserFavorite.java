package com.example.tugas_akhir.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.tugas_akhir.Adapter.FavAdapter;
import com.example.tugas_akhir.Database.FavDB;
import com.example.tugas_akhir.Model.FavItem;
import com.example.tugas_akhir.R;

import java.util.ArrayList;
import java.util.List;

public class UserFavorite extends AppCompatActivity {

    private RecyclerView favoriteRecycler;
    private FavDB favDB;
    private List<FavItem> favItemList = new ArrayList<>();
    private FavAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favorite);

        favDB = new FavDB(this);

        favoriteRecycler = findViewById(R.id.favorite_recycler);
        favoriteRecycler.setHasFixedSize(true);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        if(favItemList != null) {
            favItemList.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();
        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                String id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                int image = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));
                FavItem favItem = new FavItem(title, id, image);
                favItemList.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        favAdapter = new FavAdapter(this, favItemList);
        favoriteRecycler.setAdapter(favAdapter);
    }
}