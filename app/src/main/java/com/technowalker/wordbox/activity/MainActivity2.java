package com.technowalker.wordbox.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Configuration;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.onesignal.OneSignal;
import com.technowalker.wordbox.R;
import com.technowalker.wordbox.objects.Word;
import com.technowalker.wordbox.adapter.WordAdapter;
import com.technowalker.wordbox.notification.WordPuzzleWorker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity implements Configuration.Provider {
    private static final String ONESIGNAL_APP_ID = "c8990d92-35aa-4a13-889c-2b51b7f90f3a";
    private static final String CHANNEL_ID = "ReminderChannel";

    SQLiteDatabase database;
    ListView listView2;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    ArrayList<Word> wordList = new ArrayList<>();
    WordAdapter wordAdapter;
    public static final int notificationId = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        //  reklam  isleri
        //  banner id play = ca-app-pub-1817548267038810/2874938563
        // interstitial ad for play = ca-app-pub-1817548267038810/1481788232
       // createNotificationChannel();
       // workRequestNotify();
      //  createNotificationChannel();
        /* Reklam iptal

        Reklam XML----------------------------
        <com.google.android.gms.ads.AdView
        android:id="@+id/adView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        app:adSize="SMART_BANNER"

        app:adUnitId="ca-app-pub-1817548267038810/2874938563"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

    </com.google.android.gms.ads.AdView>
    Reklam XML-------------------------------

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1817548267038810/1481788232");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

         */

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        listView2 = findViewById(R.id.wordList);
        database = this.openOrCreateDatabase("Languages", MODE_PRIVATE, null);
        wordAdapter = new WordAdapter(this, wordList);
        try {

            database.execSQL("CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY, wordname VARCHAR, meaning VARCHAR, langname VARCHAR, prop VARCHAR)");
            //  database.execSQL("INSERT INTO words (wordname, meaning, langname) VALUES('book','kitap','English')");


            Cursor cursor = database.rawQuery("SELECT * FROM words", null);
            int wordnameIx = cursor.getColumnIndex("wordname");
            int meaningIx = cursor.getColumnIndex("meaning");
            int langnameIx = cursor.getColumnIndex("langname");
            int propIx = cursor.getColumnIndex("prop");


            while (cursor.moveToNext()) {
                String wordnameFD = cursor.getString(wordnameIx);
                String meaningFD = cursor.getString(meaningIx);
                String langnameFD = cursor.getString(langnameIx);
                String propFD = cursor.getString(propIx);


                Word word = new Word(wordnameFD, meaningFD, langnameFD, propFD);

                wordList.add(word);


            }


            wordAdapter.notifyDataSetChanged();

            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();

        }
        listView2.setAdapter(wordAdapter);



        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("word", wordList.get(position));
                intent.putExtra("userinfo", "update_mean");
                startActivity(intent);
            }
        });


    } // onCreate Sonu

    private long backPressedTime;
    private Toast backToast;

    @Override
    public void onBackPressed() {

        if (backPressedTime +2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.finishAffinity();
            System.exit(0);
            return;
        }else {
            String pressExit = getApplicationContext().getResources().getString(R.string.pressExit);
            backToast=Toast.makeText(getApplicationContext(),pressExit,Toast.LENGTH_LONG);
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();

    }

    public void workRequestNotify() {

        WorkRequest workRequest = new PeriodicWorkRequest.Builder(WordPuzzleWorker.class, 15, TimeUnit.MINUTES).build();
        WorkManager.getInstance(this).enqueue(workRequest);

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_word, menu);
        menuInflater.inflate(R.menu.solve_puzzle,menu);
        menuInflater.inflate(R.menu.settings_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_word_item) {
            Intent intent = new Intent(this, MainActivity3.class);
            intent.putExtra("userinfo", "add_new_word");
            startActivity(intent);
            /*
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }

             */
        }
        if (item.getItemId() == R.id.solve_puzzle) {
            Intent intent = new Intent(this,MainActivity4.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.settings_menu){
            Intent intent = new Intent(this, SettingsActivity.class);
           startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return  new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();
    }
}