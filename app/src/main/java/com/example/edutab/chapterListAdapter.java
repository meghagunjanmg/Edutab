package com.example.edutab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class chapterListAdapter extends RecyclerView.Adapter<chapterListAdapter.viewholder> {
    ArrayList<String> chaptersList;
    Context context;
    ArrayList<Integer> chapterno;
    prefmanager prefmanager;

    public chapterListAdapter(ArrayList<String> planetList, Context context) {
        prefmanager = new prefmanager(context);
        this.chaptersList = planetList;
        this.context = context;
        chapterno = new ArrayList<>();
        for (int i=1;i<=chaptersList.size();i++)
        {
            chapterno.add(i);
        }
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapters_list,parent,false);
        viewholder viewHolder=new viewholder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.no.setText(chapterno.get(position)+" ");
        holder.name.setText(chaptersList.get(position));

        if(position%2==0){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFB4B4"));
        }
        else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#a5d6a7"));
        }
    }

    @Override
    public int getItemCount() {
        return chaptersList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        protected TextView  name,no ;
        protected CardView cardView ;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            no = (TextView) itemView.findViewById(R.id.no);
            name= (TextView) itemView.findViewById(R.id.name);

            cardView= (CardView) itemView.findViewById(R.id.card);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if (name.getText() == "Integer") {
                        Intent i = new Intent(context, IntLang.class);
                        context.startActivity(i);
                    }
                    //else Toast.makeText(context,"you have clicked "+name.getText() ,Toast.LENGTH_LONG).show();
                    if (name.getText() == "Environment")
                    {
                        Intent i2 = new Intent(context, GeoLang.class);
                        context.startActivity(i2);
                    }
                    else Toast.makeText(context,"you have clicked "+name.getText() ,Toast.LENGTH_LONG).show();

                   */

                  activity_chapters_list.speakOut(name.getText().toString());

                    Intent i = new Intent(context, activity_action.class);
                    context.startActivity(i);
                    Bungee.swipeLeft(context);

                    String chapter = String.valueOf(name.getText());
                    String newName = chapter.replace(" ", "_").replace(",", "").replace(":", "").replace("?", "");
                    prefmanager.setKeyPrimaryChapters(newName.toLowerCase());
                }
            });

        }

    }
}

