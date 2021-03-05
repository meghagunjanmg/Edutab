package com.example.edutab;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter_x extends RecyclerView.Adapter<ImageAdapter_x.ViewHolder> {
    ArrayList<Uri> imageList;
    Context context;
    private float mScaleFactor = 1.0f;

    public ImageAdapter_x(ArrayList<Uri> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        ImageAdapter_x.ViewHolder viewHolder=new ImageAdapter_x.ViewHolder(v);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(imageList.get(position))
                .into(image);

     //   image.setImageURI(Uri.parse(imageList.get(position)));

        Log.e("IMAGELIST",imageList.get(position)+"");

        final int i = position;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_gallery.zoom_image.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(imageList.get(i))
                        .into(activity_gallery.zoom_image);

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    ImageView image;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

        }
    }
}
