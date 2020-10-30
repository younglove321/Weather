package com.e.weather01_1128;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


public class WeatherMainActivity extends AppCompatActivity {

    private String htmlPageUrl = "https://weather.naver.com/rgn/townWetr.nhn?naverRgnCd=09170555"; //파싱할 홈페이지의 URL주소

    //TextView
    private TextView textviewHtmlDocument; //이제 텍스트뷰를 여러개 만들면 됨!!
    private TextView tv_time_announce, tv_now_weather, tv_now_time,
    tv_cmp_y, tv_now_rain, tv_now_dust, tv_now_ozone, tv_today_date, tv_tomorrow_date,
    tv_td_temp0, tv_td_temp1, tv_td_info0, tv_td_info1,
            tv_tom_temp0, tv_tom_temp1, tv_tom_info0, tv_tom_info1,
            tv_afterh0,tv_afterh0_info, tv_afterh1, tv_afterh1_info,
            tv_ment, tv_test;


    //String
    private  String time_announce="", now_weather="", now_time = "",
            cmp_y="", now_rain="", now_dust="", now_ozone="",
            today_date="", tomorrow_date="", ment="", all="";

    private String htmlContentInStringFormat="";
    private String today_tomorrow_temp[]={"","","",""};
    private String today_tomorrow_info[]={"","","",""};
    private String img_icon_src[]={"","","",""}; // 날씨 아이콘 이미지
    public String today_img_src="";
    private String today_img_src_last="";

    //예보
    private String afterh_time[]={"",""};
    private String afterh_content[]={"",""};
    private String afterh_img[]={"",""};

    private ImageView iv_today_img, iv_today_am_img, iv_today_pm_img,iv_tom_am_img, iv_tom_pm_img,
            iv_after_img0,iv_after_img1;

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathermain);

        setTitle("눈송이가 알려주는 청파동 날씨");

        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        //텍스트뷰
        tv_time_announce= findViewById(R.id.tv_time_announce); // 발표 시간

        // 오늘 날씨 출력하는 파란부분
        tv_now_weather = (TextView)findViewById(R.id.tv_now_weather);
        tv_now_time = (TextView)findViewById(R.id.tv_now_time);

        tv_test=(TextView)findViewById(R.id.tv_test);
        tv_cmp_y= (TextView)findViewById(R.id.tv_cmp_y);
        tv_now_rain= (TextView)findViewById(R.id.tv_now_rain);
        tv_now_dust= (TextView)findViewById(R.id.tv_now_dust);
        tv_now_ozone= (TextView)findViewById(R.id.tv_now_ozone);
        tv_today_date=(TextView)findViewById(R.id.tv_today_date);
        tv_tomorrow_date=(TextView)findViewById(R.id.tv_tomorrow_date);

        tv_td_info0=(TextView)findViewById(R.id.tv_td_info0);
        tv_td_info1=(TextView)findViewById(R.id.tv_td_info1);
        tv_td_temp0=(TextView)findViewById(R.id.tv_td_temp0);
        tv_td_temp1=(TextView)findViewById(R.id.tv_td_temp1);

        tv_tom_info0=(TextView)findViewById(R.id.tv_tom_info0);
        tv_tom_info1=(TextView)findViewById(R.id.tv_tom_info1);
        tv_tom_temp0=(TextView)findViewById(R.id.tv_tom_temp0);
        tv_tom_temp1=(TextView)findViewById(R.id.tv_tom_temp1);

        tv_afterh0=(TextView)findViewById(R.id.tv_afterh0);
        tv_afterh1=(TextView)findViewById(R.id.tv_afterh1);
        tv_afterh0_info=(TextView)findViewById(R.id.tv_afterh0_info);
        tv_afterh1_info=(TextView)findViewById(R.id.tv_afterh1_info);

        tv_ment=(TextView)findViewById(R.id.tv_ment);

        //이미지뷰
        iv_today_img=(ImageView)findViewById(R.id.iv_today_img); //오늘 날씨 사진 이미지
        iv_today_am_img=(ImageView)findViewById(R.id.iv_today_am_img);
        iv_today_pm_img=(ImageView)findViewById(R.id.iv_today_pm_img);
        iv_tom_am_img=(ImageView)findViewById(R.id.iv_tom_am_img);
        iv_tom_pm_img=(ImageView)findViewById(R.id.iv_tom_pm_img);
        iv_after_img0=(ImageView)findViewById(R.id.iv_after_img0);
        iv_after_img1=(ImageView)findViewById(R.id.iv_after_img1);



                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();


        System.out.println("오늘 날씨 이미지 oncreate src: " + today_img_src);


        Button week_btn = (Button)findViewById(R.id.week_btn);
        Button video_btn = (Button)findViewById(R.id.video_btn);

        week_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WeekActivity.class);
                startActivity(intent);
            }
        });

        video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),VideoActivity.class);
                startActivity(intent);
            }
        });

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

                //현재 날씨 사진
                Elements today_img = doc.select("div.w_now2 img");
                today_img_src += today_img.attr("src") ;

                System.out.println("오늘 날씨 이미지 src: " + today_img_src);


                //날씨 아이콘
                Elements img_icon = doc.select("p.icon img");
                for(int i=0 ; i<4 ; i++){
                    Element icon= img_icon.get(i);
                    img_icon_src[i] += icon.attr("src") ;


                }



                //현재 날씨
                Elements std_time = doc.select("div.c_tit2 p.date2"); //기준 측정시간
                Elements w_now_time = doc.select("div.w_now2 div.fl h5"); // 00시 현재
                Elements w_now_info = doc.select("div.w_now2 div.fl em"); //0도 구름많음(현재) / 미세먼지 / 오존
                Elements w_now_sky= doc.select("div.w_now2 div.fl em strong"); // 현재 하늘 상태
              //  Elements cmp_yesterday =doc.select("div.w_now2 span.temp strong");//어제랑 온도 비교
            //    Elements w_now_rain =doc.select("div.w_now2 div.fl strong");// 강수확률
                Elements w_now_all =doc.select("div.w_now2 div.fl p:not(span)");// 어제랑비교, 강수확률, 미세먼지, 오존

                Elements titles= doc.select("div.cell li.info");

                //오늘 , 내일 반일 예보
                Elements today_and_tomorrow_date = doc.select("table.tbl_weather.tbl_today3 span");   //오늘, 내일 날짜
                Elements today_and_tomorrow_nm = doc.select("table.tbl_weather.tbl_today3 li.nm"); //온도
                Elements today_and_tomorrow_info = doc.select("table.tbl_weather.tbl_today3 li.info"); //하늘, 강수



                System.out.println("-------------------------------------------------------------");
                for(Element e: std_time){
                    System.out.println("기준 측정 시간: " + e.text());
                    time_announce +=  e.text().trim() + "";
                    String humi = e.text();
                }
                for(Element e: w_now_time){ //시간
                    System.out.println( "현재 시간 (시) : "+ e.text());
                    now_time +=  e.text().trim() + "";
                    String humi = e.text();
                }

                Element testp = w_now_all.get(0);

                System.out.println("test: " + testp.text());
                all += testp.text().trim() + ""; //내용 적는 곳
//                System.out.println("현재 강수확률0: " + rain0.text());
//                System.out.println("현재 강수확률1: " + rain1.text());
//                System.out.println("현재 강수확률2: " + rain.text());
//                System.out.println("현재 강수확률3: " + rain3.text());
//                System.out.println("현재 강수확률4: " + rain4.text());
//                now_rain += "강수확률 " +rain.text().trim() + "%";
//                int rain_int= Integer.parseInt(rain.text());
//                System.out.println("비!!!!");
//                if (rain_int < 50){
//                    System.out.println("비 가능성 낮음");
//                    ment += "비가 올 가능성이 낮아요\n";
//                }
//                else if ( (50< rain_int) && (rain_int < 70)) {
//                    System.out.println("비 가능성 보통");
//                    ment += "비가 올 가능성이 보통이에요\n";
//                }
//                else {
//                    System.out.println("비가 올 수도 있으니 우산을 챙기세요!");
//                    ment += "비가 올 수도 있으니 우산을 챙기세요!\n";
//                }


                Element e1 = w_now_info.get(0); //몇도, 구름
                System.out.println("온도, 하늘: " + e1.text());
                now_weather += e1.text().trim() + "";

                Element now_sky = w_now_sky.get(0); //지금 하늘
                String sky= now_sky.text();
                System.out.println("sky: "+ sky);
                if(sky.equals("맑음")){
                    System.out.println("날씨가 맑아요! 신나는 하루");
                    ment += "날씨가 맑아요! 신나는 하루\n";
                }
                else if(sky.equals("구름조금")){
                    System.out.println("구름이 있지만 좋은 하루 되세요!");
                    ment += "구름이 있지만 좋은 하루 되세요!\n";
                }
                else if(sky.equals("구름많음")){
                    System.out.println("구름이 많아도 오늘 하루 힘 내세요!");
                    ment += "구름이 많아도 오늘 하루 힘 내세요! \n";
                }
                else if(sky.equals("흐림")){
                    System.out.println("하늘은 흐리지만 마음은 맑게!");
                    ment += "하늘은 흐리지만 마음은 맑게! \n";
                }
                else if(sky.equals("소나기")){
                    System.out.println("소나기가 내려요! 잊지마세요");
                    ment += "소나기가 내려요! 잊지마세요 \n";
                }
                else if(sky.equals("흐리고 비")){
                    System.out.println("비가 와요! 우산 챙기세요");
                    ment += "비가 와요! 우산 챙기세요 \n";
                }

                Element e2 = w_now_info.get(1); //미세먼지
                System.out.println("미세먼지: " + e2.text());
               now_dust += "미세먼지  " + e2.text().trim() + "";
                String dust= e2.text();
                if(dust.equals("좋음")) {
                    System.out.println("미세먼지 좋음");
                    ment += "미세먼지 없는 깨끗한 하늘!\n";
                }

                else if(dust.equals("보통")) {
                    System.out.println("미세먼지 보통");
                    ment += "미세먼지 지수가 보통이에요\n";
                }
                else if(dust.equals("나쁨")) {
                    System.out.println("미세먼지 나쁨");
                    ment += "미세먼지 지수 나쁨! 마스크 꼭 끼고 나가세요~!\n";
                }







                Element e3 = w_now_info.get(2); //오존
                System.out.println("오존: " + e3.text());
                now_ozone += "오존  " +e3.text().trim() + "";
                String ozone= e3.text();
                System.out.println("ozone: "+ ozone);
                if(ozone.equals("좋음")) {
                    System.out.println("오존 지수가 낮아 야외활동하기 좋아요");
                    ment += "오존 지수가 낮아 야외활동하기 좋아요\n";
                }

                else if(ozone.equals("보통")) {
                    System.out.println("오존 지수가 보통이에요");
                    ment += "오존 지수가 보통이에요\n";
                }
                else if(ozone.equals("나쁨")) {
                    System.out.println("오존 지수가 높아 위험해요! 야외활동을 삼가세요");
                    ment += "오존 지수가 높아 위험해요! 야외활동을 삼가세요\n";
                }

                 ///////////////////////////////////////////
                //시간 예보
                Elements forecast_time = doc.select("li.after_h h6"); //한시간 후, 두시간 후 예보시간
                Elements forecast_content = doc.select("li.after_h div.inner p"); //예보 내용
                Elements forecast_img = doc.select("li.after_h div.inner img");


                for(int i=0 ; i<2 ; i++){
                    Element f_time= forecast_time.get(i);
                    Element f_con= forecast_content.get(i);
                    Element f_img= forecast_img.get(i);

                    afterh_time[i] +=  f_time.text().trim();
                    afterh_content[i] +=  f_con.text().trim();
                    afterh_img[i] += f_img.attr("src") ;

                    System.out.println( "예보 시간 : " + afterh_time[i]);
                    System.out.println( "예보 내용 : " + afterh_content[i]);
                    System.out.println( "예보 사진 : " +  afterh_img[i]);


                }

                //////////////////////////////////////////

                Element e_today_date = today_and_tomorrow_date.get(0); //오늘 날짜
                System.out.println("오늘 날짜: " + e_today_date.text());
                today_date += "오늘 : " +e_today_date.text().trim() + "";



                Element e_tomorrow_date = today_and_tomorrow_date.get(1); //내일 날짜
                System.out.println("내일 날짜: " + e_tomorrow_date.text());
                tomorrow_date += "내일 : " +e_tomorrow_date.text().trim() + "";


                for(int i=0 , j=0 ; (i<4 && j<4)  ; i++){
                Element temp= today_and_tomorrow_nm.get(i);
                Element info = today_and_tomorrow_info.get(j);

                    System.out.println( "온도 : " + temp.text());
                    today_tomorrow_temp[i] +=  temp.text().trim() + "";

                    System.out.println( "정보 : " + info.text());
                    today_tomorrow_info[i] += info.text().trim() + "";

                    j++;

                    }


                System.out.println("-------------------------------------------------------------");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            textviewHtmlDocument.setText(htmlContentInStringFormat);
            tv_time_announce.setText(time_announce);
            tv_now_time.setText(now_time);
            tv_now_weather.setText(now_weather);

            tv_test.setText(all);

            tv_cmp_y.setText(cmp_y);
            tv_now_dust.setText(now_dust);
            tv_now_rain.setText(now_rain);
            tv_now_ozone.setText(now_ozone);
            System.out.println("오늘 날씨 이미지 최종 src: " + today_img_src);

            tv_today_date.setText(today_date);
            tv_tomorrow_date.setText(tomorrow_date);


            tv_td_temp0.setText(today_tomorrow_temp[0]);
            tv_td_temp1.setText(today_tomorrow_temp[1]);

            tv_td_info0.setText(today_tomorrow_info[0]);
            tv_td_info1.setText(today_tomorrow_info[1]);

            tv_tom_temp0.setText(today_tomorrow_temp[2]);
            tv_tom_temp1.setText(today_tomorrow_temp[3]);

            tv_tom_info0.setText(today_tomorrow_info[2]);
            tv_tom_info1.setText(today_tomorrow_info[3]);

            tv_afterh0.setText(afterh_time[0]);
            tv_afterh1.setText(afterh_time[1]);
            tv_afterh0_info.setText(afterh_content[0]);
            tv_afterh1_info.setText(afterh_content[1]);

            System.out.println("ment:  "+ment);
            tv_ment.setText(ment);

            //이미지 로드
            Glide.with(WeatherMainActivity.this).load(today_img_src).into(iv_today_img);
            Glide.with(WeatherMainActivity.this).load(img_icon_src[0]).into(iv_today_am_img);
            Glide.with(WeatherMainActivity.this).load(img_icon_src[1]).into(iv_today_pm_img);
            Glide.with(WeatherMainActivity.this).load(img_icon_src[2]).into(iv_tom_am_img);
            Glide.with(WeatherMainActivity.this).load(img_icon_src[3]).into(iv_tom_pm_img);
            Glide.with(WeatherMainActivity.this).load(afterh_img[0]).into(iv_after_img0); //예보
            Glide.with(WeatherMainActivity.this).load(afterh_img[1]).into(iv_after_img1);


        }
    }


}



