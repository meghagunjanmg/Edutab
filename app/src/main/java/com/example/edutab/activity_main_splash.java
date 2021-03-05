package com.example.edutab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import spencerstudios.com.bungeelib.Bungee;


public class activity_main_splash extends AppCompatActivity implements
        TextToSpeech.OnInitListener{
    TextView title,subtitle,footer;

    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);
        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        footer = findViewById(R.id.footer);
        tts = new TextToSpeech(this, this);

        getWindow().getDecorView().setSystemUiVisibility(flags);




      /*  Typeface typeFace = Typeface.createFromAsset(getAssets(), "KolnMesseDeutz.otf");
        title.setTypeface(typeFace);
        subtitle.setTypeface(typeFace);
        footer.setTypeface(typeFace);

       */

      animation();
    }

    private void animation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(title, "Alpha", 0, 1);
        anim.setDuration(3000);
        anim.start();
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(subtitle, "Alpha", 0, 1);
        anim2.setDuration(3000);
        anim2.start();

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(footer, "ScaleY", 0, 1);
        anim4.setDuration(3000);
        anim4.start();


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(), activity_subjects_lists.class);
                    startActivity(intent);

                } catch (Exception ex) {

                }
            }
        };
        thread.start();
        Bungee.swipeLeft(activity_main_splash.this);

        // get policy manager
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        // get this app package name
        ComponentName mDPM = new ComponentName(this, MyAdmin.class);
        //startLockTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (myDevicePolicyManager.isDeviceOwnerApp(this.getPackageName())) {
                // get this app package name
                String[] packages = {this.getPackageName()};
                // mDPM is the admin package, and allow the specified packages to lock task
                //myDevicePolicyManager.setLockTaskPackages(mDPM, packages);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startLockTask();
                }
            } else {
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(flags);
        animation();
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                result = tts.setLanguage(Locale.forLanguageTag("hin"));
            }

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("");
            }

        } else { Log.e("TTS", "Initilization Failed!");}
        speakOut("Welcome to class sixth edu tab");
    }

    @Override
    public void onDestroy() {
// Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}