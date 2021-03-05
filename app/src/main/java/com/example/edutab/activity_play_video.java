package com.example.edutab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class activity_play_video extends AppCompatActivity {
    VideoView videoView;
    String path;
    prefmanager prefmanager;
    private MediaController mController;
    private Uri uriYouTube;
    String path123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        prefmanager = new prefmanager(this);

        path123 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/"+prefmanager.getKeyPrimaryLocale()+"/"+prefmanager.getKeyPrimarySubject()+"/"+prefmanager.getKeyPrimaryChapters()+"/"+path+".mp4";

        if(path.equals("demo")){
            path123 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/educontent/"+"demo.mp4";
        }

        videoView = (VideoView)this.findViewById(R.id.videoView);
       // String videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        videoView.setVideoURI(Uri.fromFile(new File(path123)));
        Log.e("names",path+"       "+path123+"  "+Uri.fromFile(new File(path123)));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    /*

        WebView mWebView = findViewById(R.id.mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.loadUrl(path);
        mWebView.setWebChromeClient(new WebChromeClient());

     */

}