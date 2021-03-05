package com.example.edutab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.opencsv.CSVReader;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activity_video extends AppCompatActivity {
    VideoView videoView;
    String path;
    prefmanager prefmanager;
    List<videoModel> url = new ArrayList<>();
    TextView title;
    ImageView banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefmanager = new prefmanager(this);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
       // if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);}
        this.setContentView(R.layout.activity_video);
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        Log.e("paths","video    "+path);

        readcsv();

        title = findViewById(R.id.title);
        banner = findViewById(R.id.banner);

        title.setText(prefmanager.getKeyPrimaryChapters().replace("_"," ")+"");
        setText();

        RecyclerView video_recycler = findViewById(R.id.video_recycler);
        video_recycler.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter adapter = new videoAdapter(url,this);
        video_recycler.setAdapter(adapter);


    }


    public void readcsv() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                Log.e("Video ", line[0] + "**" + line[1]+"******"+line[2]);
                url.add(new videoModel(line[1],line[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void setText() {
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

}

