package com.technowalker.wordbox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.technowalker.wordbox.R;
import com.technowalker.wordbox.activity.MainActivity2;

import java.util.Random;

public class MainActivity4 extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    TextView correctTextView, textView1;
    Button checkButton;
    EditText editTextAnswer;
    SQLiteDatabase database;
    int id, idX, intword, intmean;
    String wordnameFD, meaningFD, wordnameAnswer, meaningAnswer, propFD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        /* Reklam iptal
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1817548267038810/1481788232");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

         */


        correctTextView = findViewById(R.id.correctTextView);
        textView1 = findViewById(R.id.textView1);
        checkButton = findViewById(R.id.checkButton);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        database = this.openOrCreateDatabase("Languages", MODE_PRIVATE, null);
        lastEntry();
        randomWord();
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity2.class);
        startActivity(intent2);
        super.onBackPressed();
    }

    public void randomWord() {

        Random random1 = new Random();
        int int1 = random1.nextInt(7);
        System.out.println(int1 +"  inte 1");

        if (int1 >= 3) {
            // try and catch içinde word select et
            try {
                Random random2 = new Random();
                intword = random2.nextInt(idX);
                if (intword==0){
                    intword=+1;
                }
                System.out.println(intword +  "int word");
                System.out.println(idX + "idx");
                Cursor cursor = database.rawQuery("SELECT * FROM words WHERE id =" + intword, null);

                int nameIx = cursor.getColumnIndex("wordname");
                int meanIx = cursor.getColumnIndex("meaning");
                int propIx  = cursor.getColumnIndex("prop");
                if (cursor.moveToNext()) {
                    wordnameFD = cursor.getString(nameIx);
                    wordnameAnswer = cursor.getString(meanIx);
                    propFD  = cursor.getString(propIx);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //try and catch içinde word mean select et
            try {
                Random random3 = new Random();
                intmean = random3.nextInt(idX);
                if (intmean==0){
                    intmean=+1;
                }
                System.out.println(intmean  +  "int mean");
                Cursor cursor = database.rawQuery("SELECT * FROM words WHERE id =" + intmean, null);
                int nameIx = cursor.getColumnIndex("wordname");
                int meanIx = cursor.getColumnIndex("meaning");
                int propIx  = cursor.getColumnIndex("prop");
                if (cursor.moveToNext()) {
                    meaningFD = cursor.getString(meanIx);
                    meaningAnswer = cursor.getString(nameIx);
                    propFD  = cursor.getString(propIx);

                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if (meaningFD != null) {
            textView1.setText(meaningFD + " "+ propFD);
        } else {
            textView1.setText(wordnameFD + " "+ propFD);
        }
    }

    public void lastEntry() {

        Cursor cursor = database.rawQuery("SELECT * FROM words", null);

        if (cursor.moveToLast()) {
            id = cursor.getInt(cursor.getColumnIndex("wordname"));
            idX = cursor.getInt(id);
            System.out.println(idX);

        }
        cursor.close();


    }

    public void checkAnswer() {
        String congratz = getApplicationContext().getResources().getString(R.string.card_congratz);
        String wrongAnswer = getApplicationContext().getResources().getString(R.string.card_wrongAnswer);

        String answer = editTextAnswer.getText().toString();


        if (meaningFD != null && answer.matches(meaningAnswer)) {

            correctTextView.setText(congratz);
            editTextAnswer.setVisibility(View.VISIBLE);
        } else if (wordnameFD != null && answer.matches(wordnameAnswer)) {

            correctTextView.setText(congratz);
            editTextAnswer.setVisibility(View.VISIBLE);
        } else if (answer != meaningAnswer || answer != wordnameAnswer) {

            correctTextView.setText(wrongAnswer);

        }
/*
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

        }

 */


    }
}
