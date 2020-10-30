package com.e.weather01_1128;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    private String url0="https://www.youtube.com/playlist?list=PLxYAZMQrczb1HbuNnc0GQteNpUayeW8yP",
            url1="https://www.youtube.com/playlist?list=PLxYAZMQrczb0Hhw1TNRt7xBQT4Ugk2uAh",
            url2="https://www.youtube.com/playlist?list=PLxYAZMQrczb3knFM1GWENZugYJZAKPuyj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle("일기예보 유튜브 보기");
        ImageView btn_today = (ImageView) findViewById(R.id.go_today);
        ImageView btn_dust = (ImageView) findViewById(R.id.go_dust);
        ImageView btn_tom = (ImageView) findViewById(R.id.go_tomorrow);

        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(Intent.ACTION_VIEW, Uri.parse(url0));
                startActivity(intent0);
            }
        });

        btn_tom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                startActivity(intent1);
            }
        });

        btn_dust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                startActivity(intent2);
            }
        });

    }
}
