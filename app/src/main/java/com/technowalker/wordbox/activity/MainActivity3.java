package com.technowalker.wordbox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.technowalker.wordbox.R;
import com.technowalker.wordbox.objects.Word;

public class MainActivity3 extends AppCompatActivity {
    SQLiteDatabase database;
    EditText wordnameText;
    EditText meaningText;
    EditText langnameText;
    EditText propText;

    Button save_word_button, change_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        wordnameText = findViewById(R.id.wordnameText);
        meaningText = findViewById(R.id.wordmeanText);
        langnameText = findViewById(R.id.lang_name_text);
        save_word_button = findViewById(R.id.savebutton);
        change_button = findViewById(R.id.changebutton);
        propText = findViewById(R.id.wordProp_Text);


        database = this.openOrCreateDatabase("Languages", MODE_PRIVATE, null);

        Intent intent = getIntent();
        String userinfo = intent.getStringExtra("userinfo");


        if (userinfo.matches("add_new_word")) {
            change_button.setVisibility(View.INVISIBLE);


        } else if (userinfo.matches("add_new_lang")) {
            meaningText.setVisibility(View.INVISIBLE);
            wordnameText.setVisibility(View.INVISIBLE);
            change_button.setVisibility(View.INVISIBLE);
            save_word_button.setVisibility(View.INVISIBLE);

        } else if (userinfo.matches("update_word")) {
            meaningText.setVisibility(View.INVISIBLE);

            save_word_button.setVisibility(View.INVISIBLE);
            langnameText.setVisibility(View.INVISIBLE);
            propText.setVisibility(View.VISIBLE);
            Intent intent1 = getIntent();
            String worddata = intent1.getStringExtra("worddata");


            wordnameText.setHint(worddata);


        } else if (userinfo.matches("update_mean")) {

            save_word_button.setVisibility(View.INVISIBLE);
            langnameText.setVisibility(View.VISIBLE);
            meaningText.setVisibility(View.VISIBLE);
            wordnameText.setVisibility(View.VISIBLE);

            Intent intent2 = getIntent();
            Word word = (Word) intent2.getSerializableExtra("word");
            String meandata = word.getMeanText();
            String propdata = word.getPropText();
            String worddata = word.getWordText();
            String langdata = word.getLangText();

            meaningText.setHint(meandata);
            langnameText.setHint(langdata);
            propText.setHint(propdata);
            wordnameText.setHint(worddata);


        }


    } //onCreate Sonu suslu parantezi

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(this, MainActivity2.class);
        startActivity(intent2);
        super.onBackPressed();
    }

    public void save_new_word(View view) {


        String wnt = wordnameText.getText().toString();
        String mnt = meaningText.getText().toString();
        String lnt = langnameText.getText().toString();
        String pnt = propText.getText().toString();
        try {

            database.execSQL("CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY, wordname VARCHAR, meaning VARCHAR, langname VARCHAR, prop VARCHAR)");
            String sqlString = "INSERT INTO words (wordname, meaning, langname, prop ) VALUES(?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, wnt);
            sqLiteStatement.bindString(2, mnt);
            sqLiteStatement.bindString(3, lnt);
            sqLiteStatement.bindString(4, pnt);

            sqLiteStatement.execute();

        } catch (Exception e) {

        }
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);

    }

    public void change_word(View view) {
        try {
            Intent intent2 = getIntent();
            Word word = (Word) intent2.getSerializableExtra("word");
            String worddata = word.getWordText();
            String meandata = word.getMeanText();
            String langdata = word.getLangText();
            String propdata = word.getPropText();


            database.execSQL("CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY, wordname VARCHAR, meaning VARCHAR, langname VARCHAR, prop VARCHAR)");


            String nwt = wordnameText.getText().toString();
            String nmt = meaningText.getText().toString();
            String lnt = langnameText.getText().toString();
            String pnt = propText.getText().toString();

            if (!nwt.isEmpty()) {
                database.execSQL("UPDATE words SET wordname =" + "'" + nwt + "'" + "WHERE wordname = " + "'" + worddata + "'");
                System.out.println("word name change");
            }
            if (!nmt.isEmpty()) {
                database.execSQL("UPDATE words SET meaning =" + "'" + nmt + "'" + "WHERE meaning = " + "'" + meandata + "'");
                System.out.println("mean change");
            }
            if (!lnt.isEmpty()) {
                database.execSQL("UPDATE words SET langname =" + "'" + lnt + "'" + "WHERE langname = " + "'" + langdata + "'");
                System.out.println("lang name change");
            }
            if (!pnt.isEmpty()) {
                database.execSQL("UPDATE words SET prop =" + "'" + pnt + "'" + "WHERE prop = " + "'" + propdata + "'");
                System.out.println("prop change");
            }





        } catch (Exception e) {

        }
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);


    }

    public void save_lang(View view) {
        String lnt = langnameText.getText().toString();
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS languages(id INTEGER PRIMARY KEY, langname VARCHAR)");
            String sqlString = "INSERT INTO languages (langname) VALUES (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, lnt);
            sqLiteStatement.execute();
        } catch (Exception e) {
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);


    }

}