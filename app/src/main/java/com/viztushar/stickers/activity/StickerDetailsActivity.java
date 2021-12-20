package com.viztushar.stickers.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.viztushar.stickers.BuildConfig;
import com.viztushar.stickers.MainActivity;
import com.viztushar.stickers.R;
import com.viztushar.stickers.Sticker;
import com.viztushar.stickers.StickerPack;
import com.viztushar.stickers.adapter.StickerDetailsAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.viztushar.stickers.MainActivity.EXTRA_STICKER_PACK_AUTHORITY;
import static com.viztushar.stickers.MainActivity.EXTRA_STICKER_PACK_ID;
import static com.viztushar.stickers.MainActivity.EXTRA_STICKER_PACK_NAME;

public class StickerDetailsActivity extends AppCompatActivity {

    Context context;
    private AdView mAdView;
    private RewardedAd mRewardedAd;

    private static final int ADD_PACK = 200;
    private static final String TAG = StickerDetailsActivity.class.getSimpleName();
    StickerPack stickerPack;
    StickerDetailsAdapter adapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Sticker> stickers;
    ArrayList<String> strings;
    public static String path;
    Button addtowhatsapp;
    final Context context1 = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_details);

        stickerPack = getIntent().getParcelableExtra(MainActivity.EXTRA_STICKERPACK);
        toolbar = findViewById(R.id.toolbar);
        addtowhatsapp = findViewById(R.id.add_to_whatsapp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(stickerPack.name);
        getSupportActionBar().setSubtitle(stickerPack.publisher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        stickers = stickerPack.getStickers();
        strings = new ArrayList<>();
        path = getFilesDir() + "/" + "stickers_asset" + "/" + stickerPack.identifier + "/";
        File file = new File(path + stickers.get(0).imageFileName);
        Log.d(TAG, "onCreate: " +path + stickers.get(0).imageFileName);
        for (Sticker s : stickers) {
            if (!file.exists()) {
                strings.add(s.imageFileName);
            } else {
                strings.add(path + s.imageFileName);
            }
        }
        adapter = new StickerDetailsAdapter(strings, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        addtowhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
                intent.putExtra(EXTRA_STICKER_PACK_ID, stickerPack.identifier);
                intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
                intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPack.name);
                try {
                    startActivityForResult(intent, ADD_PACK);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(StickerDetailsActivity.this, "error", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
