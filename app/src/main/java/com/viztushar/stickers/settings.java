package com.viztushar.stickers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import xute.storyview.StoryModel;
import xute.storyview.StoryView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class settings extends AppCompatActivity {

    StoryView storyView;
    StoryView storyView1;
    StoryView storyView2;
    StoryView storyView3;
    StoryView storyView4;
    private AdView mAdView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);

        storyView = findViewById(R.id.storyView); // find the XML view using findViewById
        storyView.resetStoryVisits(); // reset the storyview

        storyView1 = findViewById(R.id.storyView1); // find the XML view using findViewById
        storyView1.resetStoryVisits(); // reset the storyview

        storyView2 = findViewById(R.id.storyView2); // find the XML view using findViewById
        storyView2.resetStoryVisits();// reset the storyview

        storyView3 = findViewById(R.id.storyView3); // find the XML view using findViewById
        storyView3.resetStoryVisits();// reset the storyview

        storyView4 = findViewById(R.id.storyView4); // find the XML view using findViewById
        storyView4.resetStoryVisits();// reset the storyview

        ArrayList<StoryModel> StoriesList = new ArrayList<>();  // create a Array list of Stories
        StoriesList.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/4UHf/CQKbZbTiz","Coder Mert","Turkey"));
        storyView.setImageUris(StoriesList);  // finally set the stories to storyview

        ArrayList<StoryModel> StoriesList1 = new ArrayList<>();  // create a Array list of Stories
        StoriesList1.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/FBJi/Hdk79sSzS","Yasin Tohan","Turkey"));
        storyView1.setImageUris(StoriesList1);  // finally set the stories to storyview

        ArrayList<StoryModel> StoriesList2 = new ArrayList<>();  // create a Array list of Stories
        StoriesList2.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/7gH7/S9JPxT1Wy","Hayabusa","Turkey"));
        StoriesList2.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/F3iD/Xkm2FD1RJ","Hayabusa","Turkey"));
        StoriesList2.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/KqSX/XT6tJvRbx","Hayabusa","Turkey"));
        storyView2.setImageUris(StoriesList2);  // finally set the stories to storyview


        ArrayList<StoryModel> StoriesList3 = new ArrayList<>();  // create a Array list of Stories
        StoriesList3.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/6ETL/XAgbSVyT7","Tanish Raj","India"));
        StoriesList3.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/5up4/Vv9d8BuYg","Tanish Raj","India"));
        storyView3.setImageUris(StoriesList3);  // finally set the stories to storyview


        ArrayList<StoryModel> StoriesList4 = new ArrayList<>();  // create a Array list of Stories
        StoriesList4.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/JXrK/djAGt2p3U","Sticker Bot","Everywhere"));
        StoriesList4.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/3Rni/gpkvqQoo2","Sticker Bot","Everywhere"));
        StoriesList4.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/V78E/7idLVzUXe","Sticker Bot","Everywhere"));
        StoriesList4.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/2gnw/yh9Uz7wSd","Sticker Bot","Everywhere"));
        StoriesList4.add(new StoryModel("https://thumb.cloud.mail.ru/weblink/thumb/xw1/8MhD/q4eLER8Y1","Sticker Bot","Everywhere"));
        storyView4.setImageUris(StoriesList4);  // finally set the stories to storyview



    }
}