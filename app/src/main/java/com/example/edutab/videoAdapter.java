package com.example.edutab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class videoAdapter extends RecyclerView.Adapter<videoAdapter.viewholder> {
    List<videoModel> videoList;
    Context context;
    private String videoId;

    public videoAdapter(List<videoModel> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        viewholder viewHolder=new viewholder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        try {
            Log.e("VideoImage==",videoList.get(position).getUrl());
           // videoId=extractYoutubeId("https://www.youtube.com/watch?v=2NiXgfMp9Mw&");
            videoId=extractYoutubeId(videoList.get(position).getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String img_url="http://img.youtube.com/vi/"+videoId+"/0.jpg"; // this is link which will give u thumnail image of that video

        // picasso jar file download image for u and set image in imagview

        Glide.with(context)
                .load(img_url)
                .placeholder(R.drawable.logo)
                .into(holder.imvVideoPlaceHolder);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,activity_play_video.class);
                i.putExtra("path",videoList.get(position).getFilename());
                context.startActivity(i);
                Bungee.swipeLeft(context);

            }
        });

        holder.filename.setText(videoList.get(position).getFilename()+"");
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }
    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
    ImageView imvVideoPlaceHolder;
    LinearLayout layout;
    TextView filename;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imvVideoPlaceHolder = itemView.findViewById(R.id.imvVideoPlaceHolder);
            layout = itemView.findViewById(R.id.layout);
            filename = itemView.findViewById(R.id.filename);
        }
    }
}

