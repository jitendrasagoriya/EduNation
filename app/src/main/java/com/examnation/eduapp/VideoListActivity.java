package com.examnation.eduapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.examnation.eduapp.adapter.ChapterLineAdepter;
import com.examnation.eduapp.adapter.VideoLineAdeptor;
import com.examnation.eduapp.database.DatabaseHelper;
import com.examnation.eduapp.http.HttpManager;
import com.examnation.eduapp.model.Chapter;
import com.examnation.eduapp.model.VideoModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private VideoLineAdeptor videoLineAdeptor;
    private ProgressDialog progress;
    List<VideoModel> videoModels = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    Long id = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        progress=new ProgressDialog(this);
        progress.setTitle("Processing...");
        progress.setMessage("Please wait.");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setIndeterminate(true);

        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");

        GetVideoListAsynTask videoListAsynTask = new GetVideoListAsynTask();
        videoListAsynTask.execute();


    }

    private void setupRecycler(List<VideoModel> video) {

        progress.dismiss();

        mRecyclerView = (RecyclerView)findViewById(R.id.video_list_layout_manager);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        videoLineAdeptor = new VideoLineAdeptor(video);
        mRecyclerView.setAdapter(videoLineAdeptor);

        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    public class GetVideoListAsynTask extends AsyncTask<String,String,List<VideoModel>>{

        @Override
        protected void onPreExecute() {
             progress.show();
        }

        @Override
        protected void onPostExecute(List<VideoModel> videoModels) {
            progress.dismiss();
            setupRecycler( videoModels);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<VideoModel> doInBackground(String... strings) {

          String result =  HttpManager.getData(DeveloperKey.BASE_URL+"video/chapter/"+id);

            try {
                VideoModel video;
                JSONObject obj = new JSONObject(result);
                JSONArray jsonArray = obj.getJSONArray("content");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    video = new VideoModel();
                    video.setName(jsonObject.getString("name"));
                    video.setId(jsonObject.getLong("id"));
                    video.setLink(jsonObject.getString("link"));
                    video.setDec(jsonObject.getString("description"));
                    videoModels.add(video);
                }
                return  videoModels;
            }catch (JSONException jsonException){
                jsonException.printStackTrace();
            }finally {

            }
            return  videoModels;
        }
    }
}
