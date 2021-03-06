package com.example.thefaco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//정류소 Id 조회하는 용도//
public class MainActivity extends AppCompatActivity
{

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key = "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D";
    private final String endPoint = "http://openapi.gbis.go.kr/ws/rest/busstationservice";     //버스도착정보목록조회 앞 주소


    //xml 변수
    private EditText xmlBusNum;
    private EditText xmlStationNum;
    private TextView xmlShowInfo;

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    //xml 값 입력 변수
    private String busNum;  //버스 번호
    private String stationName = ""; //정류장 Name
    private StringBuffer buffer;

    private String stationId;
    private String stationrealName;
    private String regionName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //상태바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_1);
        
        //xml 아이디 얻어오기
        getXmlId();
        buffer = new StringBuffer(); //문자열 변경,추가시 사용
    }

    //검색하기 버튼
    public void search(View view)
    {
        //사용자한테 출발정류장, 도착정류장 알아오기
        busNum = xmlBusNum.getText().toString(); //사용자가 입력한 버스번호를 문자열로 변환
        stationName = xmlStationNum.getText().toString(); //사용자가 입력한 버스정류장 번호를 문자열로 변환
        stationId = regionName = stationrealName = null;
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");

        //입력값 검사 함수 만들기
        if(examineData())
        {
            return; //true 를 반환할 경우 값이 잘못됨
        }
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusStationList(stationName);
                //오퍼레이션 2
                //getBusStationList(stationName);

                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        buffer.append("정류소 아이디 : " + stationId + " \n");
                        buffer.append("정류소명 : " + stationrealName + "\n");
                        buffer.append("지역명 : " + regionName + "\n");
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }


    //정류장 Id조회
    private void getBusStationList(String station)
    {
        String stationUrl = endPoint + "?serviceKey=" + key + "&keyword=" + station;
        Log.d(TAG, "정류장명 -> 정류장Id : " + stationUrl);

        try
        {
            setUrlNParser(stationUrl);
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT: //xml 문서가 시작할 때
                        break;
                    case XmlPullParser.START_TAG:       //xml 문서의 태그의 첫부분 만날시
                        tag = xpp.getName();    //태그이름 얻어오기
                        if(tag.equals("busStationList"));  //첫번째 검색 결과
                        else if(tag.equals("stationId"))
                        {
                            xpp.next();
                            stationId = xpp.getText();
                        }
                        else if(tag.equals("stationName"))
                        {
                            xpp.next();
                            stationrealName = xpp.getText();
                        }
                        else if(tag.equals("regionName"))
                        {
                            xpp.next();
                            regionName = xpp.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:            //xml 문서의 텍스트 만날시
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("busStationList")); //첫번째 검색결과 종료
                        break;
                }
                eventType = xpp.next();
            }
        }catch (Exception e){e.printStackTrace();}
    }


    //Url, xmlPullParser 객체 생성 및 초기화
    private void setUrlNParser(String quary)
    {
        try
        {
            url = new URL(quary);   //문자열로 된 요청 url 을 URL객체로 생성
            is = url.openStream();  //입력스트림 객체 is 를 연다

            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();  //XmlPullParserFactory를 이용해 XmlPullParser 객체 생성
            xpp.setInput(new InputStreamReader(is, "UTF-8"));   //xml 입력받기

            xpp.next(); //공백을 기준으로 입력을 받는다.
            eventType = xpp.getEventType();
        }catch(Exception e){}
    }

    private boolean examineData()
    {
        //사용자가 하나 이상의 값을 입력하지 않은 경우
        if(busNum.equals("") || stationName.equals(""))
        {
            //Toast = 사용자에게 짧은 메시지 형식으로 정보를 전달하는 팝업
            Toast User_null = Toast.makeText(this, "값을 입력해 주세요!", Toast.LENGTH_SHORT);
            User_null.show();
            return true;
        }

        //입력값은 반드시 숫자
        String UserNum = "([0-9])"; //정규표현식 사용
        Pattern pattern_symbol = Pattern.compile(UserNum);

        //버스 번호 유효성 검사 pattern 객체를 matcher 메소드를 호출하여 사용
        Matcher matcher_busNum = pattern_symbol.matcher(busNum);
        if(matcher_busNum.find() == false)
        {
            Toast User_num = Toast.makeText(this, "버스 번호를 다시 입력해주세요 !", Toast.LENGTH_SHORT);
            User_num.show();
            return true;
        }

        //정류장 번호 유효성 검사
        Matcher matcher_stationNum = pattern_symbol.matcher(stationName);
        if(matcher_stationNum.find() == false)
        {
            Toast User_stationNum = Toast.makeText(this, "정류장 번호를 다시 입력해주세요!", Toast.LENGTH_SHORT);
            User_stationNum.show();
            return true;
        }

       return false; //사용자의 값이 정상일때 false 반환
    }

    //activitymain.xml 레이아웃 에서 설정된 뷰들을 가져온다
    private void getXmlId()
    {
        //view 의 id 를 R 클래스에서 받아옴
        xmlBusNum = findViewById(R.id.busNum);
        xmlStationNum = findViewById(R.id.mystationName);
        xmlShowInfo = findViewById(R.id.showInfo);
    }


}
