package com.technowalker.wordbox.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.technowalker.wordbox.R;
import com.technowalker.wordbox.activity.MainActivity4;

public class WordPuzzleWorker extends Worker {
    public WordPuzzleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        bildirim();


        return null;
    }



    public void bildirim() {
         Integer notificationId = 888;
         String CHANNEL_ID = "ReminderChannel";

        Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
      //
        String see_puzzle = getApplicationContext().getResources().getString(R.string.see_puzzle);
        String solve_puzzle = getApplicationContext().getResources().getString(R.string.solve_puzzle);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(solve_puzzle)
                .setContentText(see_puzzle)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(see_puzzle))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }




}
