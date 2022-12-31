package com.example.tugas_akhir.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.tugas_akhir.Adapter.PopulerAdapter;
import com.example.tugas_akhir.Adapter.WisataAdapter;
import com.example.tugas_akhir.Common.LoginActivity;
import com.example.tugas_akhir.Common.RegisterActivity;
import com.example.tugas_akhir.Model.PopulerItem;
import com.example.tugas_akhir.Model.WisataItem;
import com.example.tugas_akhir.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class UserDashboard extends AppCompatActivity {

    ImageView logout;
    FirebaseAuth mAuth;
    FirebaseUser user;

    private ArrayList<PopulerItem> populerItems = new ArrayList<>();
    private RecyclerView populerRecycler;

    private ArrayList<WisataItem> wisataItems = new ArrayList<>();
    private RecyclerView wisataRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        populerRecycler = findViewById(R.id.populer_recycler);
        wisataRecycler = findViewById(R.id.wisata_recycler);

        populerRecycler();
        wisataRecycler();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        navbar();
    }

    private void populerRecycler() {
        populerRecycler.setHasFixedSize(true);
        populerRecycler.setAdapter(new PopulerAdapter(populerItems, this));
        populerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        populerItems.add(new PopulerItem(R.drawable.lawang_sewu, "Lawang Sewu", "Lawang Sewu adalah bangunan perkantoran yang terletak di seberang Tugu Muda, Kota Semarang, Jawa Tengah, Indonesia.", "0", "0"));
        populerItems.add(new PopulerItem(R.drawable.kota_lama, "Kota Lama", "Kota Lama adalah suatu kawasan di Semarang yang menjadi pusat perdagangan pada abad 19-20.", "1", "0"));
        populerItems.add(new PopulerItem(R.drawable.sam_poo_kong, "Sam Poo Kong", "Sam Poo Kong yaitu tempat persinggahan dan pendaratan pertama seorang Laksamana Tiongkok beragama Islam yang bernama Zheng He/Cheng Ho.", "2", "0"));
    }

    private void wisataRecycler() {
        wisataRecycler.setHasFixedSize(true);
        wisataRecycler.setAdapter(new WisataAdapter(wisataItems, this));
        wisataRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        wisataItems.add(new WisataItem(R.drawable.majt_smg, "Masjid Agung Jawa Tengah", "MAJT adalah masjid yang terletak di Semarang, provinsi Jawa Tengah, Indonesia.", "3", "0"));
        wisataItems.add(new WisataItem(R.drawable.kampung_pelangi, "Kampung Pelangi", "Kampung Pelangi merupakan sebutan untuk pemukiman atau kampung yang bangunan rumah penduduknya diwarnai berbagai macam warna cat.", "4", "0"));
        wisataItems.add(new WisataItem(R.drawable.tugu_muda, "Tugu Muda", "Tugu Muda merupakan monumen yang dibuat untuk mengenang jasa para pahlawan yang telah gugur dalam Pertempuran Lima Hari di Semarang.", "5", "0"));
        wisataItems.add(new WisataItem(R.drawable.brown_canyon, "Brown Canyon", "Brown Canyon adalah sebuah bekas penambangan tanah di Meteseh, Tembalang, Semarang.", "6", "0"));
    }

    private void navbar() {
        logout = findViewById(R.id.logoutBtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getBaseContext(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

    public void toFavorite(View v) {
        startActivity(new Intent(this, UserFavorite.class));
    }
}