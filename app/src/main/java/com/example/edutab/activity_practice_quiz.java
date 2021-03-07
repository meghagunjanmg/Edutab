package com.example.edutab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import spencerstudios.com.bungeelib.Bungee;

public class activity_practice_quiz extends AppCompatActivity  implements
        TextToSpeech.OnInitListener {
    CardView cardView;
    String correctanstocheck;
    String path;
    Button submit, quit;
    TextView ques,title;
    RadioGroup radioGroup;
    RadioButton op1, op2, op3;
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> correctans = new ArrayList<>();
    ArrayList<String> invalid1 = new ArrayList<>();
    ArrayList<String> invalid2 = new ArrayList<>();
    int flag = 0;
    int correct = 0;
    int wrong = 0;
    quizmodel model;
    prefmanager prefmanager;
    String randomques = "",ans="",opt1="",opt2="";
    Toolbar toolbar;
    int totalQues;
    TextView titles;
    ImageView banner;
    private TextToSpeech tts;
    String text;

    ImageButton speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        cardView = findViewById(R.id.card);
        prefmanager = new prefmanager(this);
        submit = findViewById(R.id.submit);
        quit = findViewById(R.id.quit);
        ques = findViewById(R.id.ques);
        speak = findViewById(R.id.speak);
        radioGroup = findViewById(R.id.radio_group);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        model = new quizmodel();
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        tts = new TextToSpeech(this, this);


        title = findViewById(R.id.title);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        titles = findViewById(R.id.title);
        banner = findViewById(R.id.banner);
        titles.setText(prefmanager.getKeyPrimaryChapters().replace("_"," ")+"");
        setText();

        //String csvFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/maths/integer/integer_english.csv";

        if (path.endsWith(".csv"))
            readcsv();
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity_practice_quiz.this);
            builder.setMessage("Sorry! Quiz is not available... \nTry Again Later")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent i = new Intent(activity_practice_quiz.this, activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                            Bungee.swipeLeft(activity_practice_quiz.this);

                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }


        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    text=questions.get(0);
                }
                speakOut();
            }
        });
    }

    private void setText() {

        if(prefmanager.getKeyPrimaryLocale().equals("hi")) {
            title.setText("प्रश्नोत्तरी");

            submit.setText("सबमिट");
            quit.setText("छोड़ना");
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", flag);
        outState.putInt("correct", correct);
        outState.putInt("wrong", wrong);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("count");
        savedInstanceState.getInt("correct");
        savedInstanceState.getInt("wrong");
    }

    public void readcsv() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                System.out.println(line[0] + " " + line[1]);

                questions.add(line[1]);
                correctans.add(line[2]);
                invalid1.add(line[3]);
                invalid2.add(line[4]);

                model.setQuestion(questions);
                model.setCorrectans(correctans);
                model.setInvalid1(invalid1);
                model.setInvalid2(invalid2);
            }
            totalQues = questions.size();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Random r = new Random();
            int i = r.nextInt(3);

                switch (i){
                    case 0 :{
                        ques.setText(questions.get(0));
                        op1.setText(correctans.get(0));
                        op2.setText(invalid1.get(0));
                        op3.setText(invalid2.get(0));
                        correctanstocheck = correctans.get(0);
                    }break;
                    case 1:{
                        ques.setText(questions.get(0));
                        op1.setText(invalid1.get(0));
                        op2.setText(correctans.get(0));
                        op3.setText(invalid2.get(0));
                        correctanstocheck = correctans.get(0);
                    }break;
                    case 2:{
                        ques.setText(questions.get(0));
                        op1.setText(invalid1.get(0));
                        op2.setText(invalid2.get(0));
                        op3.setText(correctans.get(0));
                        correctanstocheck = correctans.get(0);
                    }
                }

                cardView.setVisibility(View.VISIBLE);
            } catch(Exception e){
                e.printStackTrace();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity_practice_quiz.this);
                builder.setMessage("Sorry! Quiz is not available... \nTry Again Later")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(activity_practice_quiz.this, activity_chapters_list.class);
                                i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                                startActivity(i);
                                Bungee.swipeLeft(getApplicationContext());

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            text="Sorry! Quiz is not available... \nTry Again Later";
            }


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton uans = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                    try {
                        String ansText = uans.getText().toString();

                        if (ansText.equals(correctanstocheck)) {
                            correct++;
                            flag++;
                            text=questions.get(flag);
                        } else {
                            wrong++;
                            flag++;
                            text=questions.get(flag);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (uans == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity_practice_quiz.this);
                            builder.setMessage("select one option")

                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            text="Select one option";
                            speakOut();
                        }
                    }
                    if (flag < questions.size()) {
                        radioGroup.clearCheck();

                        Random r = new Random();
                        int i = r.nextInt(3);

                        switch (i) {
                            case 0: {
                                ques.setText(questions.get(flag));
                                op1.setText(correctans.get(flag));
                                op2.setText(invalid1.get(flag));
                                op3.setText(invalid2.get(flag));
                                correctanstocheck = correctans.get(flag);
                            }
                            break;
                            case 1: {
                                ques.setText(questions.get(flag));
                                op1.setText(invalid1.get(flag));
                                op2.setText(correctans.get(flag));
                                op3.setText(invalid2.get(flag));
                                correctanstocheck = correctans.get(flag);
                            }
                            break;
                            case 2: {
                                ques.setText(questions.get(flag));
                                op1.setText(invalid1.get(flag));
                                op2.setText(invalid2.get(flag));
                                op3.setText(correctans.get(flag));
                                correctanstocheck = correctans.get(flag);
                            }
                        }
                    } else if (flag == questions.size()) {
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity_practice_quiz.this);
                        dialogBuilder.setCancelable(false);
                        dialogBuilder.setMessage("You have scored " + correct + " / "+totalQues);
                        text="You have scored " + correct + " out of "+totalQues;
                        speakOut();
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(activity_practice_quiz.this, activity_chapters_list.class);
                                i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                                startActivity(i);
                                Bungee.swipeLeft(activity_practice_quiz.this);

                                finish();
                            }
                        });
                        AlertDialog alert = dialogBuilder.create();
                        alert.show();
                    }
                }
            });
            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    text="Quit";
                    speakOut();
                    Intent i = new Intent(activity_practice_quiz.this, activity_chapters_list.class);
                    i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                    startActivity(i);
                    Bungee.swipeLeft(activity_practice_quiz.this);
                }
            });
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
        if(!tts.isSpeaking()) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}