package com.example.edutab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by nigelhenshaw on 25/06/2015.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private File imagesFile;

    public ImageAdapter(File folderFile) {
        imagesFile = folderFile;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File imageFile = imagesFile.listFiles()[position];
        final Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        if(imageBitmap!=null)
        holder.getImageView().setImageBitmap(imageBitmap);

        else {
            holder.getImageView().setImageResource(R.drawable.logo);
        }
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CamaraIntentActivity.zoom_image.setVisibility(View.VISIBLE);
                CamaraIntentActivity.zoom_image.setImageBitmap(imageBitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesFile.listFiles().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private LinearLayout linear;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image);
            linear = (LinearLayout) view.findViewById(R.id.linear);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}