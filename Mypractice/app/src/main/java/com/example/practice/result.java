package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.odsay.odsayandroidsdk.ODsayService;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

//출발지와 도착지의 위도경도를 받아서 예측 소요시간(=이동시간 + 대기시간) 알려주기,,.????


public class result extends AppCompatActivity {

    TextView textView;
    TextView setting1;
    TextView setting2;
    Intent intent;
    String str_time; //소요시간

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    //대중교통환승경로 조회 서비스 API key
    private final String key = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_base);
        textView = findViewById(R.id.textView4);
        setting1 = findViewById(R.id.textview_setting1);
        setting2 = findViewById(R.id.textview_setting2);


        intent = getIntent();
        String str_1 = intent.getStringExtra("출발지"); //정류장명
        String str_2 = intent.getStringExtra("도착지");

        Double db_sx = intent.getDoubleExtra("출발지위도", 0);
        Double db_sy = intent.getDoubleExtra("출발지경도", 0);

        Double db_ex = intent.getDoubleExtra("도착지위도", 0);
        Double db_ey = intent.getDoubleExtra("도착지경도", 0);

        setting1.setText(str_1);setting2.setText(str_2);

        textView.setText("출발지정보\n" + db_sx + "\n" + db_sy + "\n도착지정보\n" + db_ex + "\n" + db_ey);
        init(str_1,str_2,db_sx,db_sy,db_ex,db_ey);


//
//        // 싱글톤 생성, Key 값을 활용하여 객체 생성
//        ODsayService odsayService = ODsayService.init(getApplicationContext(), key);
//        // 서버 연결 제한 시간(단위(초), default : 5초)
//        odsayService.setReadTimeout(5000);
//        // 데이터 획득 제한 시간(단위(초), default : 5초)
//        odsayService.setConnectionTimeout(5000);
//
//        // 콜백 함수 구현
//        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
//            // 호출 성공 시 실행
//            @Override
//            public void onSuccess(ODsayData odsayData, API api) {
//                try {
//                    // API Value 는 API 호출 메소드 명을 따라갑니다.
//                    if (api == API.BUS_STATION_INFO) {
//                        String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
//                        Log.d("Station name : %", stationName);
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            // 호출 실패 시 실행
//            @Override
//            public void onError(int i, String s, API api) {
//                if (api == API.BUS_STATION_INFO) {}
//            }
//        };
//        // API 호출
//        odsayService.requestBusStationInfo("107475", onResultCallbackListener());

    }

    private void init(final String str_1, final String str_2, final Double db_sx, final Double db_sy, final Double db_ex, final Double db_ey) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(tag, "순서 3 : 쓰레드 내부");
                searchPubTransPath(str_1,str_2,db_sx,db_sy,db_ex,db_ey);
                //순서 4 : 파싱

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(tag, "runOnUi쓰레드 내부");
                    }
                });
            }
        }).start();
    }

    public String searchPubTransPath(String one, String two, Double a, Double b, Double c, Double d) //버스이용 경로 조회 파싱
    {

        Log.d(tag, "순서 4 : 파싱");
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus"//요청 URL
                + "?ServiceKey=" + key
                + "&startX=" + b
                + "&startY=" + a
                + "&endX=" + d
                + "&endY=" + c;

        try {
            Log.d(tag, "들어옵니까???");
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결
            Log.d(tag, queryUrl);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag = null;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(tag, "파싱시작");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if(tag.equals("itemList"));// 첫번째 검색결과
//                        else if (tag.equals("fname"))  //출발 정류장이름과 다르면
//                        {
//                            String p_fname = String.valueOf(xpp.next());
//                            if(p_fname != one){break;}
//                        }
//                        else if (tag.equals("tname"))  //도착 정류장이름과 다르면
//                        {
//                            String p_fname2 = String.valueOf(xpp.next());
//                            if(p_fname2 != two){break;}
//                        }
                        else if (tag.equals("routeId"))  //8100번과 m4102번 버스가 아니라면
                        {
                            xpp.next();
                            String p_routeId = xpp.getText();
                            if(p_routeId != "234001159" || p_routeId != "234000878")
                            {break;}
                            //왜 마지막 값으로 받아오는거지ㅡㅡ
                        }
                        else if (tag.equals("time")){
                            xpp.next();
                            //buffer.append(xpp.getText());
                            str_time = xpp.getText();
                        } // 첫번째 검색결과
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("itemList")) ;// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            Log.d(tag, "에러발생");
        }
        Log.d(tag, "이동 시간은 : " + str_time);
        Log.d(tag, "파싱종료");

        return buffer.toString();
    }
}