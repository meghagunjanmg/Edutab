package com.example.edutab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.ortiz.touchview.TouchImageView;

import java.io.File;
import java.util.ArrayList;

public class activity_gallery extends AppCompatActivity implements TouchImageView.OnZoomFinishedListener,MediaScannerConnection.MediaScannerConnectionClient{

    static TouchImageView zoom_image;
    RecyclerView images;
    ArrayList<String> imagelist = new ArrayList<>();
    public String[] allFiles;
    private String SCAN_PATH ;
    private static final String FILE_TYPE="image/*";
    ArrayList<Uri> img = new ArrayList<>();

    private MediaScannerConnection conn;

    MutableLiveData<ArrayList<String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        data = new MutableLiveData<>();


        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/CAMERA/");
        allFiles = folder.list();
        //   uriAllFiles= new Uri[allFiles.length];
        for(int i = 0; i < allFiles.length; i++) {
            Log.d("ImagesPath" + i, allFiles[i]+allFiles.length);

            SCAN_PATH= Environment.getExternalStorageDirectory().toString()+"/DCIM/CAMERA/"+allFiles[i];
            System.out.println(" SCAN_PATH  " +SCAN_PATH);
            img.add(Uri.fromFile(new File(SCAN_PATH)));
        }
      //  startScan();
        Log.e("IMAGELIST",img.size()+"");


        images = findViewById(R.id.images);
        zoom_image = findViewById(R.id.zoom_image);
        images.setLayoutManager(new GridLayoutManager(this,2));
         images.setAdapter(new ImageAdapter_x(img,getApplicationContext()));

        if(zoom_image.isZoomed()) {
            zoom_image.resetZoomAnimated();
        }
        else {
            zoom_image.setZoomAnimated(0.9f, 0.5f, 0f, this);
        }
    }

    @Override
    public void onBackPressed() {
        if(zoom_image.getVisibility()==View.VISIBLE) zoom_image.setVisibility(View.GONE);
        else super.onBackPressed();
    }

    @Override
    public void onZoomFinished() {
    }


    private void startScan() {
        Log.d("Connected","success"+conn);
        if (conn!=null) {
            conn.disconnect();
        }
        conn = new MediaScannerConnection(this,this);
        conn.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        Log.d("onMediaScannerConnected","success"+conn);
        conn.scanFile(SCAN_PATH, FILE_TYPE);
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        try {
            Log.d("onScanCompleted", uri + "success" + conn);
            System.out.println("URI " + uri);
            if (uri != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);

            }
        } finally {
            conn.disconnect();
            conn = null;
        }
    }
}