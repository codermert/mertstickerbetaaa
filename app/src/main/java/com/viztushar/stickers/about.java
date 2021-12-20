package com.viztushar.stickers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;



public class about extends AppCompatActivity {

    private AdView mAdView1;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest);

        setTitle("Hakkında");
        FancyAboutPage fancyAboutPage = findViewById(R.id.fancyaboutpage);
        //fancyAboutPage.setCoverTintColor(Color.BLUE); //Optional
        fancyAboutPage.setCover(R.drawable.coverimg);
        fancyAboutPage.setName("Coder Mert");
        fancyAboutPage.setDescription("Google Certified  Android Developer");
        fancyAboutPage.setAppIcon(R.drawable.cakepop);
        fancyAboutPage.setAppName("Vizyonumuz");
        fancyAboutPage.setVersionNameAsAppSubTitle("Alfa\n");
        fancyAboutPage.setAppDescription("Harika şeyler, bireyler tarafından değil, harika bir ekip tarafından yapılır. Mütevazı bir lider ve bir aykırı değer tarafından yönetilen tutkulu bir harika ekip kurduk.\n\n" +
                "Biz sadece para için çalışmayan küçük, tutkulu bir ekibiz, insanların hayatlarına dokunmak ve insanlık için olumlu bir etki yaratmak için tutkuyla StickerOPS'u yaratan yaratıcılarız. Para bizim için hiçbir zaman bir cazibe olmadı. Gece yarısı yağ yakmamızı sağlayan amaçtır. Güçlü yönlerde oynamaya inanıyoruz ve herkesin oynayacak bir rolü var ve bunu mükemmel bir şekilde sunmak için birbirimize güveniyoruz ve harika bir sürecimiz var!\n\n" +
                "Teknolojiyi, tasarladığımız, kodladığımız, test ettiğimiz bir etki sağlamak için harika bir araç olarak kullanıyoruz. Bunu gerçekleştirmek için kan, ter ve gözyaşıyla çalışan, çılgın vizyona sahip küçük çılgın bir ekibiz!\n\nTelegram Bağış: @codermert\n\n Sevgilerle \n\n Coder Mert & Yasin Tohan & Hayabusa\n\n\n\n");
        fancyAboutPage.addEmailLink("codermert@bk.ru");
        fancyAboutPage.addTwitterLink("https://twitter.com/codermert");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/codermert/");
        fancyAboutPage.addGitHubLink("https://github.com/codermert");




    }
}