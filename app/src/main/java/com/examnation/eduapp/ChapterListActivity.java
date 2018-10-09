package com.examnation.eduapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.examnation.eduapp.adapter.ChapterLineAdepter;
import com.examnation.eduapp.adapter.VideoLineAdapter;
import com.examnation.eduapp.database.DatabaseHelper;
import com.examnation.eduapp.model.Chapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private ChapterLineAdepter chapterLineAdepter;
    private ProgressDialog progress;
    List<Chapter> chapters = null;
    private DatabaseHelper databaseHelper;
    Long id = 0l;
    String classz = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        databaseHelper = new DatabaseHelper(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_layour_recycler);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");
        classz = extras.getString("CLASS");
        chapters =  databaseHelper.getAllChapters(id,classz);

        progress=new ProgressDialog(this);
        progress.setTitle("Processing...");
        progress.setMessage("Please wait.");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setIndeterminate(true);


        if(chapters == null || chapters.isEmpty() ){
            chapters = new ArrayList<>();
            progress.show();
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://dev-edu-app.herokuapp.com/api/chapter/subject/"+id+"/class/"+classz)
                            .build();
                    Response response = null;
                    try{
                            response = client.newCall(request).execute();
                            return  response.body().string();
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                        progress.dismiss();
                        return null;

                    }
                }

                @Override
                protected void onPostExecute(Object o) {
                    try {
                        Chapter chapter;
                        JSONObject obj = new JSONObject(o.toString());
                        JSONArray jsonArray = obj.getJSONArray("content");
                         for(int i=0;i<jsonArray.length();i++){
                             JSONObject jsonObject = jsonArray.getJSONObject(i);
                               chapter = new Chapter();
                             chapter.setName(jsonObject.getString("name"));
                             chapter.setId(jsonObject.getLong("id"));
                             chapter.setSubjectId(jsonObject.getLong("subject"));
                             chapter.setClassz(jsonObject.getString("classz"));
                             chapter.setVideoCount(jsonObject.getInt("videoCount"));
                             chapter.setQuestionCount(jsonObject.getInt("questionCount"));
                             chapter.setConceptCount(jsonObject.getInt("conceptCount"));
                             chapter.setSequence(jsonObject.getInt("sequence"));

                             databaseHelper.insertChapter(chapter);
                             chapters.add(chapter);
                         }
                        progress.dismiss();
                        setupRecycler( chapters);
                    }catch (JSONException jsonException){
                        jsonException.printStackTrace();
                        progress.dismiss();
                    }finally {

                    }
                }
            }.execute();
        }else{
            setupRecycler( chapters);
        }


    }

    private void setupRecycler(List<Chapter> chapters) {

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        chapterLineAdepter = new ChapterLineAdepter(chapters);
        mRecyclerView.setAdapter(chapterLineAdepter);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
