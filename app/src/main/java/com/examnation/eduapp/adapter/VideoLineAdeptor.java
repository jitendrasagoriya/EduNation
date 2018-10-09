package com.examnation.eduapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.examnation.eduapp.PlayYoutubeActivity;
import com.examnation.eduapp.R;
import com.examnation.eduapp.VideoListActivity;
import com.examnation.eduapp.model.VideoModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class VideoLineAdeptor extends  RecyclerView.Adapter<VideoLineAdeptor.VideoLineHolder> {

    private static final String TAG = "VideoLineAdeptor";

    private Context context;

    List<VideoModel> videoModels;

    String cue;


    public VideoLineAdeptor(List<VideoModel> videoModels) {
        this.videoModels = videoModels;
    }

    @NonNull
    @Override
    public VideoLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context =  parent.getContext();
        return new VideoLineHolder(LayoutInflater.from(context)
                .inflate(R.layout.main_line_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoLineHolder holder, final int position) {

        String link = videoModels.get(position).getLink();
        try{
            cue = link.substring(link.indexOf("v=")+2);
        }catch (Exception e){
            cue = "5xVh-7ywKpE";
        }

        Uri uri = new Uri.Builder().path("https://i.ytimg.com/vi/"+cue+"/mqdefault.jpg").build();
        holder.title.setText( videoModels.get(position).getName());

        ImageLoadTask imageLoadTask = new ImageLoadTask("https://i.ytimg.com/vi/"+cue+"/mqdefault.jpg",holder.image);
        imageLoadTask.execute();


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + videoModels.get(position));
                Toast.makeText(context, videoModels.get(position).getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context,PlayYoutubeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("LINK", cue);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public class VideoLineHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView image;

        public VideoLineHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            image = itemView.findViewById(R.id.imageButton);
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            URL urlConnection;
            HttpURLConnection connection = null;
            try {
                urlConnection = new URL(url);
                connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}
