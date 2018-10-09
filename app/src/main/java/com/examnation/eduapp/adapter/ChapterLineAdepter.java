package com.examnation.eduapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.examnation.eduapp.PlayYoutubeActivity;
import com.examnation.eduapp.R;
import com.examnation.eduapp.StandalonePlayerDemoActivity;
import com.examnation.eduapp.VideoListActivity;
import com.examnation.eduapp.VideoListDemoActivity;
import com.examnation.eduapp.VideoWallDemoActivity;
import com.examnation.eduapp.YouTubeAPIDemoActivity;
import com.examnation.eduapp.model.Chapter;

import java.util.List;
import java.util.Locale;

public class ChapterLineAdepter extends RecyclerView.Adapter<ChapterLineHolder> {

    private static final String TAG = "ChapterLineAdepter";

    private final List<Chapter> chapterList;

    private Context context;

    public ChapterLineAdepter(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ChapterLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context =  parent.getContext();
        return new ChapterLineHolder(LayoutInflater.from(context)
                .inflate(R.layout.chapter_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterLineHolder holder, final int position) {
        holder.title.setText(String.format(Locale.getDefault(), "%s",
                chapterList.get(position).getName()
        ));

        holder.watchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + chapterList.get(position));
                Toast.makeText(context, chapterList.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,VideoListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("ID", chapterList.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList != null ? chapterList.size() : 0;
    }
}