package com.example.edutab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

public class activity_pdf extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener , OnErrorListener {
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String path;
    prefmanager prefmanager;
    OnErrorListener  onErrorListener;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        prefmanager = new prefmanager(this);
        pdfView = (PDFView) findViewById(R.id.pdfview);
        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path");
        Log.d("path", path);
        displayFromAsset(path);

        toolbar = findViewById(R.id.toolbar);

    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromUri(Uri.parse(path))
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .onError(this)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

        pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard=(ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                String valueText = pdfView.getTableOfContents().get(0).getTitle();
                clipboard.setText(valueText);
                Toast.makeText(getApplicationContext(),"Copied to clipboard "+valueText, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));

    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
        Log.d("meta",meta.getTitle());
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }

        }
    }

    @Override
    public void onError(Throwable t) {
        pdfView.setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_pdf.this);
        builder.setMessage("Sorry! Pdf is not available... \nTry Again Later")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(activity_pdf.this, activity_chapters_list.class);
                        i.putExtra("subject", prefmanager.getKeyPrimarySubject());
                        startActivity(i);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
