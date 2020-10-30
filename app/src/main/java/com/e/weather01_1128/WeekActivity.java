package com.e.weather01_1128;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;



public class WeekActivity extends AppCompatActivity {

    private String htmlPageUrl = "https://weather.naver.com/rgn/townWetr.nhn?naverRgnCd=09170555"; //파싱할 홈페이지의 URL주소

    //TextView
    private TextView tv_date0,tv_date1,tv_date2,tv_date3,tv_date4,
            tv_day1_temp0, tv_day1_temp1, tv_day1_info0, tv_day1_info1,
            tv_day2_temp0, tv_day2_temp1, tv_day2_info0, tv_day2_info1,
            tv_day3_temp0, tv_day3_temp1, tv_day3_info0, tv_day3_info1,
            tv_day4_temp0, tv_day4_temp1, tv_day4_info0, tv_day4_info1,
            tv_day5_temp0, tv_day5_temp1, tv_day5_info0, tv_day5_info1;



    private String week_temp[]={"","","","","","","","","",""};
    private String week_info[]={"","","","","","","","","",""};
    private String week_date[]={"","","","",""};
    private String week_img[]={"","","","","","","","","",""}; // 날씨 아이콘 이미지


    private ImageView iv_day1_am_img, iv_day1_pm_img,
    iv_day2_am_img, iv_day2_pm_img ,iv_day3_am_img, iv_day3_pm_img,
    iv_day4_am_img, iv_day4_pm_img , iv_day5_am_img, iv_day5_pm_img;
    Context context;

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        setTitle("청파동 주간 날씨");


        //텍스트뷰
        // 날짜 출력
        tv_date0=(TextView)findViewById(R.id.tv_date0);
        tv_date1=(TextView)findViewById(R.id.tv_date1);
        tv_date2=(TextView)findViewById(R.id.tv_date2);
        tv_date3=(TextView)findViewById(R.id.tv_date3);
        tv_date4=(TextView)findViewById(R.id.tv_date4);

        tv_day1_info0=(TextView)findViewById(R.id.tv_day1_info0);
        tv_day1_info1=(TextView)findViewById(R.id.tv_day1_info1);
        tv_day1_temp0=(TextView)findViewById(R.id.tv_day1_temp0);
        tv_day1_temp1=(TextView)findViewById(R.id.tv_day1_temp1);

        tv_day2_info0=(TextView)findViewById(R.id.tv_day2_info0);
        tv_day2_info1=(TextView)findViewById(R.id.tv_day2_info1);
        tv_day2_temp0=(TextView)findViewById(R.id.tv_day2_temp0);
        tv_day2_temp1=(TextView)findViewById(R.id.tv_day2_temp1);

        tv_day3_info0=(TextView)findViewById(R.id.tv_day3_info0);
        tv_day3_info1=(TextView)findViewById(R.id.tv_day3_info1);
        tv_day3_temp0=(TextView)findViewById(R.id.tv_day3_temp0);
        tv_day3_temp1=(TextView)findViewById(R.id.tv_day3_temp1);

        tv_day4_info0=(TextView)findViewById(R.id.tv_day4_info0);
        tv_day4_info1=(TextView)findViewById(R.id.tv_day4_info1);
        tv_day4_temp0=(TextView)findViewById(R.id.tv_day4_temp0);
        tv_day4_temp1=(TextView)findViewById(R.id.tv_day4_temp1);

        tv_day5_info0=(TextView)findViewById(R.id.tv_day5_info0);
        tv_day5_info1=(TextView)findViewById(R.id.tv_day5_info1);
        tv_day5_temp0=(TextView)findViewById(R.id.tv_day5_temp0);
        tv_day5_temp1=(TextView)findViewById(R.id.tv_day5_temp1);


        //이미지뷰
        iv_day1_am_img=(ImageView)findViewById(R.id.iv_day1_am_img);
        iv_day1_pm_img=(ImageView)findViewById(R.id.iv_day1_pm_img);
        iv_day2_am_img=(ImageView)findViewById(R.id.iv_day2_am_img);
        iv_day2_pm_img=(ImageView)findViewById(R.id.iv_day2_pm_img);
        iv_day3_am_img=(ImageView)findViewById(R.id.iv_day3_am_img);
        iv_day3_pm_img=(ImageView)findViewById(R.id.iv_day3_pm_img);
        iv_day4_am_img=(ImageView)findViewById(R.id.iv_day4_am_img);
        iv_day4_pm_img=(ImageView)findViewById(R.id.iv_day4_pm_img);
        iv_day5_am_img=(ImageView)findViewById(R.id.iv_day5_am_img);
        iv_day5_pm_img=(ImageView)findViewById(R.id.iv_day5_pm_img);


        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    public class JsoupAsyncTask extends AsyncTask<Void, Void, Void> { //private

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();

                //시간 예보
                Elements w_date = doc.select("table.tbl_weather.tbl_today4 th"); //주간날짜
                Elements w_temp = doc.select("div.cell li.nm"); //온도
                Elements w_info = doc.select("div.cell li.info"); //정보
                Elements w_img = doc.select("div.cell img"); //아이콘


                for(int i=0 ; i<5 ; i++){
                    Element date= w_date.get(i);
                    week_date[i] += date.text().trim();
                    System.out.println( "날짜 : " + week_date[i]);

                }


                for(int i=0 ; i<10 ; i++){
                    Element img= w_img.get(i+4);
                    Element temp= w_temp.get(i+4);
                    Element info= w_info.get(i+4);

                    week_img[i] += img.attr("src") ;
                    week_temp[i] +=  temp.text().trim();
                    week_info[i] +=  info.text().trim();

                    System.out.println("이미지: "+ week_img[i]);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            tv_date0.setText(week_date[0]);
            tv_date1.setText(week_date[1]);
            tv_date2.setText(week_date[2]);
            tv_date3.setText(week_date[3]);
            tv_date4.setText(week_date[4]);


            tv_day1_temp0.setText(week_temp[0]);
            tv_day1_temp1.setText(week_temp[1]);

            tv_day2_temp0.setText(week_temp[2]);
            tv_day2_temp1.setText(week_temp[3]);

            tv_day3_temp0.setText(week_temp[4]);
            tv_day3_temp1.setText(week_temp[5]);

            tv_day4_temp0.setText(week_temp[6]);
            tv_day4_temp1.setText(week_temp[7]);

            tv_day5_temp0.setText(week_temp[8]);
            tv_day5_temp1.setText(week_temp[9]);

/////////////////////////////////////////////////////////

            tv_day1_info0.setText(week_info[0]);
            tv_day1_info1.setText(week_info[1]);

            tv_day2_info0.setText(week_info[2]);
            tv_day2_info1.setText(week_info[3]);

            tv_day3_info0.setText(week_info[4]);
            tv_day3_info1.setText(week_info[5]);

            tv_day4_info0.setText(week_info[6]);
            tv_day4_info1.setText(week_info[7]);

            tv_day5_info0.setText(week_info[8]);
            tv_day5_info1.setText(week_info[9]);


            //이미지 로드
            Glide.with(WeekActivity.this).load(week_img[0]).into(iv_day1_am_img);
            Glide.with(WeekActivity.this).load(week_img[1]).into(iv_day1_pm_img);
            Glide.with(WeekActivity.this).load(week_img[2]).into(iv_day2_am_img);
            Glide.with(WeekActivity.this).load(week_img[3]).into(iv_day2_pm_img);
            Glide.with(WeekActivity.this).load(week_img[4]).into(iv_day3_am_img);
            Glide.with(WeekActivity.this).load(week_img[5]).into(iv_day3_pm_img);
            Glide.with(WeekActivity.this).load(week_img[6]).into(iv_day4_am_img);
            Glide.with(WeekActivity.this).load(week_img[7]).into(iv_day4_pm_img);
            Glide.with(WeekActivity.this).load(week_img[8]).into(iv_day5_am_img);
            Glide.with(WeekActivity.this).load(week_img[9]).into(iv_day5_pm_img);

        }
    }


}



