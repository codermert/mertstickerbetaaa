package com.viztushar.stickers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;






import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.orhanobut.hawk.Hawk;
import com.viztushar.stickers.adapter.StickerAdapter;
import com.viztushar.stickers.model.StickerModel;
import com.viztushar.stickers.task.GetStickers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetStickers.Callbacks {


    public static final String EXTRA_STICKER_PACK_ID = "sticker_pack_id";
    public static final String EXTRA_STICKER_PACK_AUTHORITY = "sticker_pack_authority";
    public static final String EXTRA_STICKER_PACK_NAME = "sticker_pack_name";
    public static final String EXTRA_STICKERPACK = "stickerpack";
    private static final String TAG = MainActivity.class.getSimpleName();
    private final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static String path;
    ArrayList<String> strings;
    StickerAdapter adapter;
    ArrayList<StickerPack> stickerPacks = new ArrayList<>();
    List<Sticker> mStickers;
    ArrayList<StickerModel> stickerModels = new ArrayList<>();
    RecyclerView recyclerView;
    List<String> mEmojis,mDownloadFiles;
    String android_play_store_link;
    Toolbar toolbar;
    Context context;
    private AdView mAdView2;
    final Context context1 = this;
    final Context context2 = this;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        @SuppressLint("RestrictedApi")
        ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(context1, "atm_shortcut")
                .setShortLabel(getString(R.string.app_name))
                .setLongLabel(getString(R.string.developer_name))
                .setIcon(IconCompat.createFromIcon(Icon.createWithResource(context1, R.drawable.cakepop)))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/codermert")))
                .build();
        ShortcutManagerCompat.pushDynamicShortcut(context1, shortcut);
        @SuppressLint("RestrictedApi")
        ShortcutInfoCompat shortcut2 = new ShortcutInfoCompat.Builder(context2, "atm2_shortcut")
                .setShortLabel(getString(R.string.app_name))
                .setLongLabel(getString(R.string.telegram_yonlendir))
                .setIcon(IconCompat.createFromIcon(Icon.createWithResource(context2, R.drawable.verified)))
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://t.me/codermert")))
                .build();
        ShortcutManagerCompat.pushDynamicShortcut(context2, shortcut2);


        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        stickerPacks = new ArrayList<>();
        path = getFilesDir() + "/" + "stickers_asset";
        mStickers = new ArrayList<>();
        stickerModels = new ArrayList<>();
        mEmojis = new ArrayList<>();
        mDownloadFiles = new ArrayList<>();
        mEmojis.add("");
        adapter = new StickerAdapter(this, stickerPacks);
        getPermissions();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        new GetStickers(this, this, getResources().getString(R.string.json_link)).execute();
    }



    public static void SaveImage(Bitmap finalBitmap, String name, String identifier) {

        String root = path + "/" + identifier;
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = name;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.WEBP, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SaveTryImage(Bitmap finalBitmap, String name, String identifier) {

        String root = path + "/" + identifier;
        File myDir = new File(root + "/" + "try");
        myDir.mkdirs();
        String fname = name.replace(".png","").replace(" ","_") + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 40, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getPermissions() {
        int perm = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (perm != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    1
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case(R.id.about_me):
                startActivity(new Intent(MainActivity.this, about.class));
                break;
            case(R.id.action_donate):
                startActivity(new Intent(MainActivity.this, settings.class));
                break;
            case(R.id.discover_video):

                Toast.makeText(getApplicationContext(),"Yapım aşamasında!",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    @Override
    public void onListLoaded(String jsonResult, boolean jsonSwitch) {
        try {
            if (jsonResult != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(jsonResult);
                    android_play_store_link = jsonResponse.getString("android_play_store_link");
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("sticker_packs");
                    Log.d(TAG, "onListLoaded: " + jsonMainNode.length());
                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        Log.d(TAG, "onListLoaded: " + jsonChildNode.getString("name"));
                        stickerPacks.add(new StickerPack(
                                jsonChildNode.getString("identifier"),
                                jsonChildNode.getString("name"),
                                jsonChildNode.getString("publisher"),
                                getLastBitFromUrl(jsonChildNode.getString("tray_image_file")).replace(" ","_"),
                                jsonChildNode.getString("publisher_email"),
                                jsonChildNode.getString("publisher_website"),
                                jsonChildNode.getString("privacy_policy_website"),
                                jsonChildNode.getString("license_agreement_website")
                        ));
                        JSONArray stickers = jsonChildNode.getJSONArray("stickers");
                        Log.d(TAG, "onListLoaded: " + stickers.length());
                        for (int j = 0; j < stickers.length(); j++) {
                            JSONObject jsonStickersChildNode = stickers.getJSONObject(j);
                            mStickers.add(new Sticker(
                                    getLastBitFromUrl(jsonStickersChildNode.getString("image_file")).replace(".png",".webp"),
                                    mEmojis
                            ));
                            mDownloadFiles.add(jsonStickersChildNode.getString("image_file"));
                        }
                        Log.d(TAG, "onListLoaded: " + mStickers.size());
                        Hawk.put(jsonChildNode.getString("identifier"), mStickers);
                        stickerPacks.get(i).setAndroidPlayStoreLink(android_play_store_link);
                        stickerPacks.get(i).setStickers(Hawk.get(jsonChildNode.getString("identifier"),new ArrayList<Sticker>()));
                        /*stickerModels.add(new StickerModel(
                                jsonChildNode.getString("name"),
                                mStickers.get(0).imageFileName,
                                mStickers.get(1).imageFileName,
                                mStickers.get(2).imageFileName,
                                mStickers.get(2).imageFileName,
                                mDownloadFiles
                        ));*/
                        mStickers.clear();
                    }
                    Hawk.put("sticker_packs", stickerPacks);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new StickerAdapter(this, stickerPacks);
                recyclerView.setAdapter(adapter);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onListLoaded: " + stickerPacks.size());
    }

    private static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
