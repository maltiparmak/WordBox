package com.technowalker.wordbox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.technowalker.wordbox.R;
import com.technowalker.wordbox.notification.WordPuzzleWorker;

import java.util.concurrent.TimeUnit;

public class SettingsActivity extends AppCompatActivity {
    Switch aSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch =findViewById(R.id.switch1);


        SharedPreferences sharedPreferences = getSharedPreferences("push word puzzle",MODE_PRIVATE);
        aSwitch.setChecked(sharedPreferences.getBoolean("value",true));



        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("push word puzzle",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    aSwitch.setChecked(true);
                  //  Toast.makeText(getApplicationContext(),"Notifications turned on by user",Toast.LENGTH_LONG).show();
                    WorkRequest workRequest = new PeriodicWorkRequest.Builder(WordPuzzleWorker.class, 6, TimeUnit.HOURS).build();
                    WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("push word puzzle",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    aSwitch.setChecked(false);
                 //   Toast.makeText(getApplicationContext(),"Notifications turned off by user",Toast.LENGTH_LONG).show();

                }
            }
        });




    }
}