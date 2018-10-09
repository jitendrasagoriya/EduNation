package com.examnation.eduapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.examnation.eduapp.database.DatabaseHelper;
import com.examnation.eduapp.model.Chapter;

public class DataBaseReciver extends BroadcastReceiver {

    private SQLiteDatabase database;

    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Chapter chapter = new Chapter();
        chapter.setId(1l);
        chapter.setName("JITENDTA SAGOIEYA");
        databaseHelper.insertChapter(chapter);
    }
}
