package com.examnation.eduapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.examnation.eduapp.R;

public class ChapterLineHolder extends RecyclerView.ViewHolder  {

    public TextView title;
    public TextView watchView;

    public ChapterLineHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.chapterName);
        watchView = (TextView) itemView.findViewById(R.id.watchVideo);
    }

}
