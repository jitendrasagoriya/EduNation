package com.examnation.eduapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.examnation.eduapp.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "edunation.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Chapter.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Chapter.TABLE_NAME_CHAPTER);
        // Create tables again
        onCreate(db);
    }

    public void dropTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Chapter.TABLE_NAME_CHAPTER);
        // Create tables again
        db.execSQL(Chapter.CREATE_TABLE);
    }

    public long insertChapter(Chapter chapter) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Chapter.COLUMN_ID, chapter.getId());
        values.put(Chapter.COLUMN_NAME, chapter.getName());
        values.put(Chapter.COLUMN_SUBJECT_ID, chapter.getSubjectId());
        values.put(Chapter.COLUMN_CLASS, chapter.getClassz());
        // insert row
        long id = db.insert(Chapter.TABLE_NAME_CHAPTER, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public Chapter getChapter(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Chapter.TABLE_NAME_CHAPTER,
                new String[]{Chapter.COLUMN_ID, Chapter.COLUMN_NAME, Chapter.COLUMN_QUESTION_COUNT, Chapter.COLUMN_VIDEO_COUNT
                        , Chapter.COLUMN_CONCEPT_COUNT,Chapter.COLUMN_SEQUENCE},
                Chapter.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Chapter note = new Chapter(
                cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_QUESTION_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_CONCEPT_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_VIDEO_COUNT)),
                cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_SUBJECT_ID)),
                cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_CLASS)),
                cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_SEQUENCE)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<Chapter> getAllChapters(Long subjectId ) {
        List<Chapter> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Chapter.TABLE_NAME_CHAPTER,
                new String[]{Chapter.COLUMN_ID, Chapter.COLUMN_NAME, Chapter.COLUMN_QUESTION_COUNT, Chapter.COLUMN_VIDEO_COUNT
                        , Chapter.COLUMN_CONCEPT_COUNT},
                Chapter.COLUMN_SUBJECT_ID + "=?",

                new String[]{String.valueOf(subjectId)}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chapter note = new Chapter(
                        cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_QUESTION_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_CONCEPT_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_VIDEO_COUNT)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<Chapter> getAllChapters(Long subjectId,String classz ) {
        List<Chapter> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Chapter.TABLE_NAME_CHAPTER + " WHERE  " +
                Chapter.COLUMN_SUBJECT_ID + " =? AND "+Chapter.COLUMN_CLASS + " =?  ORDER BY "
                +Chapter.COLUMN_ID + " ASC";

        Cursor cursor = db.rawQuery( selectQuery,
                new String[]{String.valueOf(subjectId),String.valueOf(classz)} );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chapter note = new Chapter(
                        cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_QUESTION_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_CONCEPT_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_VIDEO_COUNT)),
                        cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_SUBJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_CLASS)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_SEQUENCE)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<Chapter> getAllChapters() {
        List<Chapter> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Chapter.TABLE_NAME_CHAPTER + " ORDER BY " +
                Chapter.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chapter note = new Chapter(
                        cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_QUESTION_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_CONCEPT_COUNT)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_VIDEO_COUNT)),
                        cursor.getLong(cursor.getColumnIndex(Chapter.COLUMN_SUBJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(Chapter.COLUMN_CLASS)),
                        cursor.getInt(cursor.getColumnIndex(Chapter.COLUMN_SEQUENCE)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getChapterCount() {
        String countQuery = "SELECT  * FROM " + Chapter.TABLE_NAME_CHAPTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
}
