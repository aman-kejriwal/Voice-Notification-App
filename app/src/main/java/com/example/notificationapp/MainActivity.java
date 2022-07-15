package com.example.notificationapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
public class MainActivity extends AppCompatActivity {
Button button,button1,button2;
EditText edittext;
String msg;
TextView textView;
String channel_id="My Channel";
int  Notifaction_id=100;
TextToSpeech textToSpeech;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button1=findViewById(R.id.button1);
        edittext=findViewById(R.id.edittext);
        textView=findViewById(R.id.textView);
        button2=findViewById(R.id.button2);


 //get SMS
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            textToSpeech.setLanguage(Locale.forLanguageTag("hin"));
                Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null,null,null);
                cursor.moveToFirst();
                msg=cursor.getString(13);
                textToSpeech.speak(msg,TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });
        //button
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.calculator, null);
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                        Bitmap icon = bitmapDrawable.getBitmap();
                        Notification notification = new Notification.Builder(MainActivity.this)
                                .setLargeIcon(icon).setSmallIcon(R.drawable.calculator)
                                .setContentText(msg)
                                .setSubText("Aman Kejriwal")
                                .setChannelId(channel_id)
                                .build();
                        manager.createNotificationChannel(new NotificationChannel(channel_id, "new Channel", NotificationManager.IMPORTANCE_HIGH));
                        manager.notify(Notifaction_id, notification);

//                    }
//                });
    }
    //button1
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void TextToSpeechButton(View view){
        textToSpeech.speak(msg,TextToSpeech.QUEUE_FLUSH,null,null);
    }
    //button2
@RequiresApi(api = Build.VERSION_CODES.O)
public void ReadSMS(View view){                         //we can be specific like:- sms/sent
    Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null,null,null);
    cursor.moveToFirst();
     msg=cursor.getString(13);
    textView.setText(msg);
}

}