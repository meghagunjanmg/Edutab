package com.example.edutab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.opencsv.CSVReader;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import spencerstudios.com.bungeelib.Bungee;

public class activity_practice_questions extends AppCompatActivity  implements
        TextToSpeech.OnInitListener {
    String path;
    prefmanager prefmanager;
    HashMap<String,String> QuesAns;
    int i = 0;
    Toolbar toolbar;
    TextView title;

    Button next,answer;
    private TextToSpeech tts;
    String text;
    ImageButton speak;
    TextView titles;
    ImageView banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_questions);
        prefmanager = new prefmanager(this);
        tts = new TextToSpeech(this, this);

        QuesAns = new HashMap<>();

        title = findViewById(R.id.title);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        next = findViewById(R.id.next);
        answer = findViewById(R.id.answer);
        speak = findViewById(R.id.speak);

        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");

        titles = findViewById(R.id.title);
        banner = findViewById(R.id.banner);
        titles.setText(prefmanager.getKeyPrimaryChapters().replace("_"," ")+"");
        setText();
        //String csvFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/maths/integer/integer_english.csv";

        if (path.endsWith("QA.csv"))
            readcsv();
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity_practice_questions.this);
            builder.setMessage("Sorry! Quiz is not available... \nTry Again Later")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent i = new Intent(activity_practice_questions.this, activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                            Bungee.swipeLeft(getApplicationContext());

                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }
    public void readcsv() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                Log.e("QA ", line[0] + "**" + line[1] + "***" + line[2]);
                QuesAns.put(line[1], line[2]);
            }

            Log.e("QA ", "Hash " + QuesAns.toString());

            final String[] keys =  QuesAns.keySet().toArray(new String[0]);
            final TextView ques = findViewById(R.id.ques);
            final TextView ans = findViewById(R.id.ans);
            next = findViewById(R.id.next);
            answer = findViewById(R.id.answer);
            final EasyFlipView easyFlipView = (EasyFlipView) findViewById(R.id.easyFlipView);

            final ArrayList<String> questions = new ArrayList<>();
            questions.addAll(Arrays.asList(keys));
            Log.e("QA","questions "+questions.toString());


            for(int j = i ; j < keys.length ; j++){
                try {
                    String key = keys[j];
                    if(j==0) {
                        ans.setText(QuesAns.get(key)+"");
                        ques.setText(key+"");
                        text = ques.getText().toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        i++;
                        if(i<questions.size()) {
                            if(easyFlipView.isBackSide()){
                                easyFlipView.flipTheView();
                            }
                            if(QuesAns.get(questions.get(i))==null || QuesAns.get(questions.get(i)).equalsIgnoreCase("") || questions.get(i)==null || questions.get(i).equalsIgnoreCase(""))
                            {
                                i++;
                            }
                            else {
                                ans.setText(QuesAns.get(questions.get(i)) + " ");
                                ques.setText(questions.get(i) + " ");

                                text = ques.getText().toString();
                            }
                        }

                        if(i==questions.size()){
                            if(prefmanager.getKeyPrimaryLocale().equals("hi")){
                                next.setText("समाप्त");
                            }
                          else  next.setText("Finish");
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(activity_practice_questions.this,activity_chapters_list.class);
                                    i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                                    startActivity(i);
                                    Bungee.swipeLeft(activity_practice_questions.this);

                                    finish();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        i++;
                    }
                }
            });

            if(i==questions.size()){
                next.setText("Finish");
                next.setOnClickListener(new View.OnClickListener() {
                   @Override
                        public void onClick(View v) {
                            startActivity(new Intent(activity_practice_questions.this,activity_chapters_list.class));
                       Bungee.swipeLeft(getApplicationContext());

                       finish();
                    }
                });
            }
            answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        text = ans.getText().toString();
                        if (easyFlipView.isFrontSide()) {
                            easyFlipView.flipTheView();
                        }
                        else {}
                    } catch (Exception e){e.printStackTrace();}

                }
            });
            easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
                @Override
                public void onViewFlipCompleted(EasyFlipView flipView, EasyFlipView.FlipState newCurrentSide)
                {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private void setText() {
        if(prefmanager.getKeyPrimaryLocale().equals("hi")) {
            title.setText("सवाल जवाब");
            next.setText("अगला");
            answer.setText("उत्तर");
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
        if(!tts.isSpeaking()) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}