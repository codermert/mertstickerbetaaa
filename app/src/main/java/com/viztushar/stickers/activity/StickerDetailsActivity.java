package com.viztushar.stickers.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.viztushar.stickers.BuildConfig;
import com.viztushar.stickers.FbReactions;
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

import com.amrdeveloper.reactbutton.ReactButton;
import com.amrdeveloper.reactbutton.Reaction;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class StickerDetailsActivity extends AppCompatActivity {

    Context context;


    private static final int ADD_PACK = 200;
    private static final String TAG = StickerDetailsActivity.class.getSimpleName();
    StickerPack stickerPack;
    StickerDetailsAdapter adapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Sticker> stickers;
    ArrayList<String> strings;
    public static String path;

    final Context context1 = this;
    private RewardedAd mRewardedAd;
    private final String TAG1 = "--->AdMob";
    private static final String TAG2 = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_details);


        final ReactButton reactButton = findViewById(R.id.add_to_whatsapp);
        reactButton.setReactions(FbReactions.reactions);
        reactButton.setDefaultReaction(FbReactions.defaultReact);
        reactButton.setEnableReactionTooltip(true);


        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
        final Shape.DrawableShape drawableShape = new Shape.DrawableShape(drawable, true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadRewardedAd();
            }
        });






        stickerPack = getIntent().getParcelableExtra(MainActivity.EXTRA_STICKERPACK);
        toolbar = findViewById(R.id.toolbar);

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





        reactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (mRewardedAd != null) {
                        showRewardedAd();

                    } else {
                        Log.d("---AdMob", "The interstitial ad wasn't ready yet.");
                        Toast.makeText(getApplicationContext(), "Reklam yüklenmedi tekrar deneyin", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(StickerDetailsActivity.this, "Sanırım WhatsApp yüklü değil veya paketiniz orijinal değil", Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG1, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG1, "Ad was loaded.");

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG1, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG1, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG1, "Ad was dismissed.");
                                loadRewardedAd();
                                Intent intent = new Intent();
                                intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
                                intent.putExtra(EXTRA_STICKER_PACK_ID, stickerPack.identifier);
                                intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
                                intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPack.name);
                                startActivityForResult(intent, ADD_PACK);
                                final KonfettiView konfettiView = findViewById(R.id.konfettiView);
                                konfettiView.build()
                                        .addColors(Color.YELLOW, Color.DKGRAY, Color.RED)
                                        .setDirection(0.0, 359.0)
                                        .setSpeed(1f, 5f)
                                        .setFadeOutEnabled(true)
                                        .setTimeToLive(2000L)
                                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                                        .addSizes(new Size(12, 5f))
                                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                                        .streamFor(300, 5000L);
                            }
                        });
                    }
                });
    }
    private void showRewardedAd(){
        if (mRewardedAd != null) {
            Activity activityContext = StickerDetailsActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG1, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        } else {
            Log.d(TAG1, "The rewarded ad wasn't ready yet.");
        }
    }

}
