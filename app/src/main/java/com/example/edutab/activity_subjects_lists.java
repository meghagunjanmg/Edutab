package com.example.edutab;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.edutab.notepad.activities.home.HomeActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import spencerstudios.com.bungeelib.Bungee;

public class activity_subjects_lists extends AppCompatActivity implements
        TextToSpeech.OnInitListener, NavigationView.OnNavigationItemSelectedListener {


    private AppBarConfiguration mAppBarConfiguration;
    Button eng_grammar,gk,geo, maths, his, pol, sci, eng, sans, hin;
    ImageView eng_gram_image,gk_image,math_image, geo_image, his_image, pol_image, sci_image, eng_image, sans_image, hin_image;
    FrameLayout geo_frame;
    Integer count = 0;
    Boolean flag = true;
    prefmanager prefmanager;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 500;
    Toolbar toolbar;
    TextView title,showTitle;
    ImageView imageView;
    private TextToSpeech tts;
    String text;

    int id;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_lists);
        id = getIntent().getIntExtra("notes",0);

        tts = new TextToSpeech(this, this);
        prefmanager = new prefmanager(this);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        maths = findViewById(R.id.maths);
        geo = findViewById(R.id.geo);
        his = findViewById(R.id.his);
        pol = findViewById(R.id.pol);
        sci = findViewById(R.id.sci);
        eng = findViewById(R.id.eng);
        sans = findViewById(R.id.sans);
        hin = findViewById(R.id.hin);
        gk = findViewById(R.id.gk);
        eng_grammar = findViewById(R.id.eng_grammar);
        hin_image = findViewById(R.id.hin_image);
        sans_image = findViewById(R.id.sans_image);
        eng_image = findViewById(R.id.eng_image);
        sci_image = findViewById(R.id.sci_image);
        pol_image = findViewById(R.id.pol_image);
        his_image = findViewById(R.id.his_image);
        math_image = findViewById(R.id.math_image);
        geo_image = findViewById(R.id.geo_image);
        gk_image = findViewById(R.id.gk_image);
        eng_gram_image = findViewById(R.id.eng_gram_image);

        setText();

       // startAnim();


        imageView = findViewById(R.id.banner);
        imageView.setImageResource(R.drawable.ic_subject_banner);

        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                     /*    ObjectAnimator objectanimator = ObjectAnimator.ofFloat(math_image, "x", 0);
                                           objectanimator.setDuration(1000);
                                           objectanimator.start();
/
                                      */
                speakOut(maths.getText().toString());
                prefmanager.setKeyPrimarySubject("maths");
                TranslateAnimation animation = new TranslateAnimation(0, (math_image.getWidth() - maths.getWidth()) - maths.getX(), 0, 0);
                animation.setDuration(1000); // animation duration
                math_image.startAnimation(animation);


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
            }
        });

        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*float v1 =(((geo_image.getWidth() + geo_image.getX()))*2 + geo_image.getX());
            float v2 = geo.getWidth()-235;
            Log.d("values",v2 +" "+ v1);
            ObjectAnimator objectanimator = ObjectAnimator.ofFloat(geo_image, "x", v1);
            objectanimator.setDuration(1000);
            objectanimator.start();

             */
                speakOut(geo.getText().toString());
                prefmanager.setKeyPrimarySubject("geo");
                TranslateAnimation animation = new TranslateAnimation(0, (geo.getWidth() - geo_image.getWidth()) + geo_image.getX(), 0, 0); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
                animation.setDuration(1000); // animation duration
                geo_image.startAnimation(animation);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
                geo_image.invalidate();
            }
        });

        his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                     /*    ObjectAnimator objectanimator = ObjectAnimator.ofFloat(math_image, "x", 0);
                                           objectanimator.setDuration(1000);
                                           objectanimator.start();

                                      */
                speakOut(his.getText().toString());
                prefmanager.setKeyPrimarySubject("his");
                TranslateAnimation animation = new TranslateAnimation(0, (his_image.getWidth() - his.getWidth()) - his.getX(), 0, 0);
                animation.setDuration(1000); // animation duration
                his_image.startAnimation(animation);


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
            }
        });

        pol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*float v1 =(((geo_image.getWidth() + geo_image.getX()))*2 + geo_image.getX());
            float v2 = geo.getWidth()-235;
            Log.d("values",v2 +" "+ v1);
            ObjectAnimator objectanimator = ObjectAnimator.ofFloat(geo_image, "x", v1);
            objectanimator.setDuration(1000);
            objectanimator.start();

             */
                speakOut(pol.getText().toString());
                prefmanager.setKeyPrimarySubject("pol");
                TranslateAnimation animation = new TranslateAnimation(0, (pol.getWidth() - pol_image.getWidth()) + pol_image.getX(), 0, 0); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
                animation.setDuration(1000); // animation duration
                pol_image.startAnimation(animation);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
                pol_image.invalidate();
            }
        });
        sci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                     /*    ObjectAnimator objectanimator = ObjectAnimator.ofFloat(math_image, "x", 0);
                                           objectanimator.setDuration(1000);
                                           objectanimator.start();

                                      */
                speakOut(sci.getText().toString());
                prefmanager.setKeyPrimarySubject("sci");
                TranslateAnimation animation = new TranslateAnimation(0, (sci_image.getWidth() - sci.getWidth()) - sci.getX(), 0, 0);
                animation.setDuration(1000); // animation duration
                sci_image.startAnimation(animation);


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
            }
        });

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*float v1 =(((geo_image.getWidth() + geo_image.getX()))*2 + geo_image.getX());
            float v2 = geo.getWidth()-235;
            Log.d("values",v2 +" "+ v1);
            ObjectAnimator objectanimator = ObjectAnimator.ofFloat(geo_image, "x", v1);
            objectanimator.setDuration(1000);
            objectanimator.start();

             */
                speakOut(eng.getText().toString());
                prefmanager.setKeyPrimarySubject("eng");
                TranslateAnimation animation = new TranslateAnimation(0, (eng.getWidth() - eng_image.getWidth()) + eng_image.getX(), 0, 0); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
                animation.setDuration(1000); // animation duration
                eng_image.startAnimation(animation);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
                eng_image.invalidate();
            }
        });

        sans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                     /*    ObjectAnimator objectanimator = ObjectAnimator.ofFloat(math_image, "x", 0);
                                           objectanimator.setDuration(1000);
                                           objectanimator.start();

                                      */
                speakOut(sans.getText().toString());
                prefmanager.setKeyPrimarySubject("sans");
                TranslateAnimation animation = new TranslateAnimation(0, (sans_image.getWidth() - sans.getWidth()) - sans.getX(), 0, 0);
                animation.setDuration(1000); // animation duration
                sans_image.startAnimation(animation);


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
            }
        });

        hin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*float v1 =(((geo_image.getWidth() + geo_image.getX()))*2 + geo_image.getX());
            float v2 = geo.getWidth()-235;
            Log.d("values",v2 +" "+ v1);
            ObjectAnimator objectanimator = ObjectAnimator.ofFloat(geo_image, "x", v1);
            objectanimator.setDuration(1000);
            objectanimator.start();

             */
                speakOut(hin.getText().toString());
                prefmanager.setKeyPrimarySubject("hin");
                TranslateAnimation animation = new TranslateAnimation(0, (hin.getWidth() - hin_image.getWidth()) + hin_image.getX(), 0, 0); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
                animation.setDuration(1000); // animation duration
                hin_image.startAnimation(animation);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
                hin_image.invalidate();
            }
        });


        gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                     /*    ObjectAnimator objectanimator = ObjectAnimator.ofFloat(math_image, "x", 0);
                                           objectanimator.setDuration(1000);
                                           objectanimator.start();

                                      */
                speakOut(gk.getText().toString());
                prefmanager.setKeyPrimarySubject("gk");
                TranslateAnimation animation = new TranslateAnimation(0, (gk_image.getWidth() - gk.getWidth()) - gk.getX(), 0, 0);
                animation.setDuration(1000); // animation duration
                gk_image.startAnimation(animation);


                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
            }
        });
        eng_grammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*float v1 =(((geo_image.getWidth() + geo_image.getX()))*2 + geo_image.getX());
            float v2 = geo.getWidth()-235;
            Log.d("values",v2 +" "+ v1);
            ObjectAnimator objectanimator = ObjectAnimator.ofFloat(geo_image, "x", v1);
            objectanimator.setDuration(1000);
            objectanimator.start();

             */
                speakOut(eng_grammar.getText().toString());
                prefmanager.setKeyPrimarySubject("eng_grammar");
                TranslateAnimation animation = new TranslateAnimation(0, (eng_grammar.getWidth() - eng_gram_image.getWidth()) + eng_gram_image.getX(), 0, 0); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
                animation.setDuration(1000); // animation duration
                eng_gram_image.startAnimation(animation);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(700);
                            Intent i = new Intent(getApplicationContext(), activity_chapters_list.class);
                            i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                            startActivity(i);
                        } catch (Exception ex) {

                        }
                    }
                };
                thread.start();
                eng_gram_image.invalidate();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dictionary, R.id.camera, R.id.gallery, R.id.calc,R.id.lang,R.id.notes)
                .setDrawerLayout(drawer)
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        toggle.getDrawerArrowDrawable().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(activity_subjects_lists.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select one language")
                .setCancelable(false)
                .setPositiveButton("English", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prefmanager.setKeyPrimaryLocale("en");
                        setText();
                    }
                })
                .setNegativeButton("हिन्दी", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        prefmanager.setKeyPrimaryLocale("hi");
                        setText();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();




        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        if (permission2 != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void startAnim() {

        @SuppressLint("ResourceType")
        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.button_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        hin_image.startAnimation(rotation);
        sans_image.startAnimation(rotation);
        eng_image.startAnimation(rotation);
        sci_image.startAnimation(rotation);
        pol_image.startAnimation(rotation);
        his_image.startAnimation(rotation);
        math_image.startAnimation(rotation);
        geo_image.startAnimation(rotation);
        gk_image.startAnimation(rotation);
        eng_gram_image.startAnimation(rotation);
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                500);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 500: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i("1", "Permission has been denied by user");

                } else {

                    Log.i("1", "Permission has been granted by user");

                }
                return;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(this, activity_main_splash.class);
        startActivity(in);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.camera: {
                speakOut("camera");
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                Bungee.swipeLeft(activity_subjects_lists.this);

            }
            break;

            case R.id.gallery: {
                speakOut("gallery");
                GALLERY();
            }
            break;
            case R.id.dictionary: {
                speakOut("dictionary");
                Intent intent = new Intent(this, dictionary_activity.class);
                startActivity(intent);
                Bungee.swipeLeft(activity_subjects_lists.this);

            }
            break;
            case R.id.notes: {
                speakOut("Notes");
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                Bungee.swipeLeft(activity_subjects_lists.this);

            }
            break;

            case R.id.demo: {
                speakOut("demonstration");
                Intent i = new Intent(this,activity_play_video.class);
                i.putExtra("path","demo");
                startActivity(i);
                Bungee.swipeLeft(activity_subjects_lists.this);

            }
            break;

            case R.id.meet: {
                speakOut("online classes");
                String url = "https://meet.google.com/";

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Chrome is probably not installed
                    // Try with the default browser
                    i.setPackage(null);
                    startActivity(i);
                }
                Bungee.swipeLeft(activity_subjects_lists.this);
            }
            break;

            case R.id.lang: {
                speakOut("Change Language");
                Thread thread = new Thread();
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                thread.start();
              startActivity(new Intent(getApplicationContext(),activity_main_splash.class));
              finish();

            }break;

            case R.id.calc: {
                speakOut("calculator");
                Intent intent = new Intent(this, activity_calculator.class);
                startActivity(intent);
                Bungee.swipeLeft(activity_subjects_lists.this);
            }
            break;
        }
        return true;
    }

    private void GALLERY() {


        Intent i = new Intent(this,CamaraIntentActivity.class);
        startActivity(i);
        Bungee.swipeLeft(activity_subjects_lists.this);

    }

    private void setText() {
        if (prefmanager.getKeyPrimaryLocale().equals("hi")) {
            maths.setText("गणित");
            geo.setText("भूगोल");
            his.setText("इतिहास");
            pol.setText("राजनीति विज्ञान");
            sci.setText("विज्ञान");
            eng.setText("अंग्रेज़ी");
            sans.setText("संस्कृत");
            hin.setText("हिन्दी");
            gk.setText("सामान्य ज्ञान");
            eng_grammar.setText("अंग्रेज़ी व्याकरण");
            title.setText("विषय");
        }
        if (prefmanager.getKeyPrimaryLocale().equals("en")) {
            maths.setText("Maths");
            geo.setText("Geography");
            his.setText("History");
            pol.setText("Political Science");
            sci.setText("Science");
            eng.setText("English");
            sans.setText("Sanskrit");
            hin.setText("Hindi");
            gk.setText("General Knowledge");
            eng_grammar.setText("English Grammar");
            title.setText("Subjects");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setText();
       // startAnim();
    }



    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("hi","IN"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("");
            }

        } else { Log.e("TTS", "Initilization Failed!");}

        speakOut("Select one language");

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
 