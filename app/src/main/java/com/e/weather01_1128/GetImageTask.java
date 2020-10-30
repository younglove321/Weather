package com.e.weather01_1128;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
class GetImageTask extends AsyncTask<String, Void, Bitmap> {
    private String numberType;
    private ImageView today_img;

    @Override protected Bitmap doInBackground(String... params) {

        numberType = params[1];
        Bitmap bitmap = null;
        try { URL url = new URL(
                "https://weather.naver.com/rgn/townWetr.nhn?naverRgnCd=09170555" + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); conn.setDoOutput(true); conn.connect();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace(); }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        today_img=(ImageView) today_img.findViewById(R.id.iv_today_img); //오늘 날씨 사진 이미지
        if( numberType.equals("number1") ) {
        today_img.setImageBitmap(bitmap); }

    }
}

