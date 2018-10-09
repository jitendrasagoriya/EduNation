package com.examnation.eduapp.http;

import android.net.http.AndroidHttpClient;

import com.squareup.okhttp.OkHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {

    public static String getData(String urlString){
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet httpGet = new HttpGet(urlString);
        HttpResponse response;
        try{
            response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            httpClient.close();
        }
        return null;
    }
}
