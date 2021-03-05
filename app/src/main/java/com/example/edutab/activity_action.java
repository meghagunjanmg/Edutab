package com.example.edutab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Locale;

import spencerstudios.com.bungeelib.Bungee;

public class activity_action extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    CardView learn,watch,practice;
    String CsvPath,PdfPath,Mp4Path;
    TextView textview,learnTV,practiceTV,watchTV;
    prefmanager prefmanager;
    Toolbar toolbar;

    private TextToSpeech tts;
    String text;

    TextView title;
    ImageView banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
        prefmanager = new prefmanager(this);
        learn = findViewById(R.id.learn);
        watch = findViewById(R.id.watch);
        practice = findViewById(R.id.practice);
        textview = findViewById(R.id.textview);
        learnTV = findViewById(R.id.learn_);
        watchTV = findViewById(R.id.watch_);
        practiceTV = findViewById(R.id.practice_);
        title = findViewById(R.id.title);
        banner = findViewById(R.id.banner);

        tts = new TextToSpeech(this, this);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setText();

        title.setText(prefmanager.getKeyPrimaryChapters().replace("_"," ")+"");

        learn.setVisibility(View.GONE);
        watch.setVisibility(View.GONE);
        practice.setVisibility(View.GONE);

        Log.d("values",prefmanager.getKeyPrimaryLocale()+" "+prefmanager.getKeyPrimarySubject()+" "+prefmanager.getKeyPrimaryChapters());


         CsvPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/"+prefmanager.getKeyPrimaryLocale()+"/"+prefmanager.getKeyPrimarySubject()+"/"+prefmanager.getKeyPrimaryChapters()+"/"+prefmanager.getKeyPrimaryChapters()+".csv";
        File file = new File(CsvPath);
        if (file.exists()) {
            practice.setVisibility(View.VISIBLE);
        }
        else {
                CsvPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/"+prefmanager.getKeyPrimaryLocale()+"/"+prefmanager.getKeyPrimarySubject()+"/"+prefmanager.getKeyPrimaryChapters()+"/"+prefmanager.getKeyPrimaryChapters()+"QA.csv";
            File file1 = new File(CsvPath);
            if (file1.exists()) {
                practice.setVisibility(View.VISIBLE);
            }
            else practice.setVisibility(View.GONE);
        }

        PdfPath =  "/sdcard/educontent/"+prefmanager.getKeyPrimaryLocale()+"/"+prefmanager.getKeyPrimarySubject()+"/"+prefmanager.getKeyPrimaryChapters()+"/"+prefmanager.getKeyPrimaryChapters()+".pdf";
        File file2 = new File(PdfPath);
        if (file2.exists()) {
            learn.setVisibility(View.VISIBLE);
        }
        else learn.setVisibility(View.GONE);


        Mp4Path =   "/sdcard/educontent/"+prefmanager.getKeyPrimaryLocale()+"/"+prefmanager.getKeyPrimarySubject()+"/"+prefmanager.getKeyPrimaryChapters()+"/"+prefmanager.getKeyPrimaryChapters()+"Vid.csv";
        File file3 = new File(Mp4Path);
        Log.e("paths","video    "+Mp4Path);
        if(file3.exists())
        watch.setVisibility(View.VISIBLE);
        else watch.setVisibility(View.GONE);


        if(learn.getVisibility() !=View.VISIBLE && watch.getVisibility()!=View.VISIBLE && practice.getVisibility()!=View.VISIBLE){
            if(prefmanager.getKeyPrimaryLocale().equals("hi")){
                textview.setText("कोई सामग्री उपलब्ध नहीं है");
            }
            else textview.setText("No content available");
        }

        learn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            text = learnTV.getText().toString();
            speakOut();

            Intent i = new Intent(activity_action.this, activity_pdf.class);
            i.putExtra("path", PdfPath);
            Log.e("paths"," "+PdfPath);
            startActivity(i);
            Bungee.swipeLeft(activity_action.this);
        }
    });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = watchTV.getText().toString();
                speakOut();

                Intent i = new Intent(activity_action.this, activity_video.class);
                i.putExtra("path", Mp4Path);
                startActivity(i);
                Bungee.swipeLeft(activity_action.this);

            }
        });
        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = practiceTV.getText().toString();
                speakOut();


                if(CsvPath.contains("QA"))
                {

                    Intent i = new Intent(activity_action.this, activity_practice_questions.class);
                    i.putExtra("path", CsvPath);
                    startActivity(i);
                    Bungee.swipeLeft(activity_action.this);

                }
                else {
                    Intent i = new Intent(activity_action.this, activity_practice_quiz.class);
                    i.putExtra("path", CsvPath);
                    startActivity(i);
                    Bungee.swipeLeft(activity_action.this);
                }
            }
        });
    }

    private void setText() {
        if(prefmanager.getKeyPrimaryLocale().equals("hi")) {
            textview.setText("कोई भी एक विकल्प चुनें");

            if (learn.getVisibility() != View.VISIBLE && watch.getVisibility() != View.VISIBLE && practice.getVisibility() != View.VISIBLE) {
                if (prefmanager.getKeyPrimaryLocale().equals("hi")) {
                    textview.setText("कोई सामग्री उपलब्ध नहीं है");
                }
            }
            learnTV.setText("पढ़ना");
            watchTV.setText("देखना");
            practiceTV.setText("अभ्यास");
        }

        if(prefmanager.getKeyPrimarySubject().equals("maths")){
            banner.setImageResource(R.drawable.ic_maths_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("geo")){
            banner.setImageResource(R.drawable.ic_geo_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("his")){
            banner.setImageResource(R.drawable.ic_history_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("pol")){
            banner.setImageResource(R.drawable.ic_pol_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("sci")){
            banner.setImageResource(R.drawable.ic_sci_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("eng")){
            banner.setImageResource(R.drawable.ic_english_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("sans")){
            banner.setImageResource(R.drawable.ic_sanskrit_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("hin")){
            banner.setImageResource(R.drawable.ic_hindi_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("gk")){
            banner.setImageResource(R.drawable.ic_gk_banner);
        }
        if(prefmanager.getKeyPrimarySubject().equals("eng_grammar")){
            banner.setImageResource(R.drawable.ic_grammer_banner);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // finish();
        setText();
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("hi","IN"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else { Log.e("TTS", "Initilization Failed!");}

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

    private void speakOut() {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
