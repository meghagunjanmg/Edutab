package com.example.edutab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.DictionaryMainResponse;
import com.example.edutab.retrofit.Dictionary;
import com.example.edutab.retrofit.Retrofit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dictionary_activity extends AppCompatActivity  implements
        TextToSpeech.OnInitListener {

    EditText searchWord;
    Button find;
    TextView meaning;
    dictionarymodel model;
    String path;
    HashMap<String,String> map = new HashMap<>();
    String search,key,value;
    ArrayList<String> keys = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();

    prefmanager prefmanager;
    Toolbar toolbar;
    TextView title;

    private TextToSpeech tts;
    String text;
        ImageView banner;

    Translator englishGermanTranslator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_activity);


        prefmanager = new prefmanager(this);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        tts = new TextToSpeech(this, this);


        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.HINDI)
                        .build();

        englishGermanTranslator =
                Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {

                                Log.e("translation",v+"");

                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...

                                Log.e("translation",e.getLocalizedMessage()+"");
                            }
                        });



        find = findViewById(R.id.onclick);
        searchWord = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        banner = findViewById(R.id.banner);

        title.setText("Dictionary");
        banner.setImageResource(R.drawable.ic_dictinary_banner);

        model = new dictionarymodel();
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/dictionary.csv";
       readcsv();

        search = searchWord.getText().toString();



        searchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    // callDictionaryApi(searchWord.getText().toString());
                    callgoogletranslate();

                    if (keys.contains(searchWord.getText().toString().toLowerCase())) {
                        int i = keys.indexOf(searchWord.getText().toString().toLowerCase());
                        Log.d("testing",i+"");
                        meaning.setVisibility(View.VISIBLE);
                        meaning.setText(values.get(i));
                        Log.d("testing",values.get(i));
                    }
                    else {
                        meaning.setVisibility(View.VISIBLE);
                        meaning.setText("Word Not Found");
                    }



                }
                return false;
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callDictionaryApi(searchWord.getText().toString());
                callgoogletranslate();

                if (keys.contains(searchWord.getText().toString().toLowerCase())) {
                    int i = keys.indexOf(searchWord.getText().toString().toLowerCase());
                    Log.d("testing",i+"");
                    meaning.setVisibility(View.VISIBLE);
                    meaning.setText(values.get(i));
                    Log.d("testing",values.get(i));
                    text = searchWord.getText().toString() + " meaning is "+meaning.getText().toString();
                    speakOut();
                }
            else {
                    meaning.setVisibility(View.VISIBLE);
                    if (prefmanager.getKeyPrimaryLocale().equals("hi")) {
                        meaning.setText("शब्द नहीं मिला");
                    } else meaning.setText("Word Not Found");

                    text = searchWord.getText().toString() + " Word Not Found";
                    speakOut();
                }


            }
        });

        setText();
    }

    private void callgoogletranslate() {

        englishGermanTranslator.translate(searchWord.getText().toString())
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                meaning.setVisibility(View.VISIBLE);
                                meaning.setText(translatedText);
                                text = searchWord.getText().toString() + " meaning is "+meaning.getText().toString();
                                speakOut();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callDictionaryApi(searchWord.getText().toString());
                            }
                        });
    }

    private void callDictionaryApi(String word) {
        Retrofit retrofit = new Retrofit();
        Dictionary dictionary = retrofit.client().create(Dictionary.class);

        Call<List<DictionaryMainResponse>> call = dictionary.getWordMeaning(word);

        call.enqueue(new Callback<List<DictionaryMainResponse>>() {
            @Override
            public void onResponse(Call<List<DictionaryMainResponse>> call, Response<List<DictionaryMainResponse>> response) {
                Log.e("Dictionary"," "+response.raw().request().url());
                Log.e("Dictionary",response.code()+"");

                if(response.code()==200){
                        meaning.setVisibility(View.VISIBLE);
                        meaning.setText(response.body().get(0).getMeanings().get(0).getDefinitions().get(0).getDefinition().toString());
                    text = searchWord.getText().toString() + " meaning is "+meaning.getText().toString();
                    speakOut();
                    }
                    else {
                        meaning.setVisibility(View.VISIBLE);
                        if(prefmanager.getKeyPrimaryLocale().equals("hi")){
                            meaning.setText("शब्द नहीं मिला");
                        }
                        else meaning.setText("Word Not Found");

                    text = searchWord.getText().toString() + " Word Not Found";
                    speakOut();
                    }
            }

            @Override
            public void onFailure(Call<List<DictionaryMainResponse>> call, Throwable t) {
                Log.e("Dictionary",t.getLocalizedMessage());

            }
        });
    }

    private void setText() {
        if(prefmanager.getKeyPrimaryLocale().equals("hi")){

            title.setText("शब्दकोश");
            find.setText("खोज");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText();
    }

    public void readcsv() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                keys.add(line[0]);
                values.add(line[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
