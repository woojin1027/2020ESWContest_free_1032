package com.example.thefaco;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class pathSetting_end extends BaseActivity2 implements TextWatcher{
    public String selected_item;
    public String mytest2;
    public String mytest_name2;

    public static Context context;

    ListView list_new;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> data_hashmap;
    EditText searchBox;
    ImageView icon_search;
    Button nearby_stop;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actList.add(this);
        setContentView(R.layout.pathsetting_end);

        searchBox = (EditText) findViewById(R.id.searchbox2);  //검색창
        list_new = findViewById(R.id.list_new2);  //정류장데이터
        icon_search = (ImageView) findViewById(R.id.icon_search2);  //돋보기 아이콘
        nearby_stop = (Button) findViewById(R.id.nearby_stop2);

        data = new ArrayList<HashMap<String, String>>();

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "단국대.치과병원");
        data_hashmap.put("정류소번호", "47682");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "단국대정문");
        data_hashmap.put("정류소번호", "47717");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "꽃메마을.새에덴교회");
        data_hashmap.put("정류소번호", "29277");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "보정동주민센터");
        data_hashmap.put("정류소번호", "29249");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "오리역");
        data_hashmap.put("정류소번호", "07058");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "미금역.청솔마을.2001아울렛");
        data_hashmap.put("정류소번호", "07333");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "정자역");
        data_hashmap.put("정류소번호", "07624");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "분당구청입구.수내교");
        data_hashmap.put("정류소번호", "07115");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "순천향대학병원");
        data_hashmap.put("정류소번호", "03163");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남대문세무서.국가인권위원회");
        data_hashmap.put("정류소번호", "02287");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "종로2가사거리");
        data_hashmap.put("정류소번호", "01001");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "을지로입구역.광교");
        data_hashmap.put("정류소번호", "02246");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "북창동.남대문시장");
        data_hashmap.put("정류소번호", "02283");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서울역버스환승센터6");
        data_hashmap.put("정류소번호", "02006");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "숭례문");
        data_hashmap.put("정류소번호", "02121");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "명동국민은행앞");
        data_hashmap.put("정류소번호", "02253");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남대문세무서.서울백병원");
        data_hashmap.put("정류소번호", "02001");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "순천향대학병원");
        data_hashmap.put("정류소번호", "03164");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "분당구청입구.수내교");
        data_hashmap.put("정류소번호", "07114");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "정자역");
        data_hashmap.put("정류소번호", "07049");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "미금역.청솔마을.2001아울렛");
        data_hashmap.put("정류소번호", "07331");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "오리역");
        data_hashmap.put("정류소번호", "07057");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "보정동주민센터");
        data_hashmap.put("정류소번호", "29873");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "꽃메마을2단지");
        data_hashmap.put("정류소번호", "29282");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "단국대.치과병원");
        data_hashmap.put("정류소번호", "47683");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서현역");
        data_hashmap.put("정류소번호", "07302");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서현역.AK플라자");
        data_hashmap.put("정류소번호", "07169");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "이매촌한신.서현역.AK프라자");
        data_hashmap.put("정류소번호", "07561");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "이매촌한신.서현역.AK프라자");
        data_hashmap.put("정류소번호", "07168");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남대문시장앞.이회영활동터");
        data_hashmap.put("정류소번호", "02219");
        data.add(data_hashmap);

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(), data,
                android.R.layout.simple_list_item_2,
                new String[]{"정류장명", "정류소번호"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        list_new.setAdapter(adapter);



        list_new.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);


        //엔터키막기
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        //주변 정류장 클릭시 이벤트
        nearby_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pathSetting_end.this, map_around_busstop2.class);
                startActivity(intent);
            }
        });


        //돋보기 클릭 시 -> 리스트 띄우고 -> 리스트 누르면 지도 띄우기
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_new.setVisibility(View.VISIBLE);
                toastshow(v, "검색 결과");
            }
        });


        list_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = (HashMap<String,String>)list_new.getItemAtPosition(position);
                mytest2 = map.get("정류소번호"); //07333같은 5글자의 정류소 고유 숫자
                mytest_name2 = map.get("정류장명");

                //출발지 정보 얻어옴
                intent = getIntent();
                Double db_1 = intent.getDoubleExtra("출발지위도",0);
                Double db_2 = intent.getDoubleExtra("출발지경도",0);
                String str_1 = intent.getStringExtra("출발지");

                Intent intent1 = new Intent(pathSetting_end.this, pathset_mapshow_end.class);
                intent1.putExtra("출발지", str_1);
                intent1.putExtra("출발지위도", db_1);
                intent1.putExtra("출발지경도", db_2);
                startActivity(intent1);
            }
        });
        context = this;
    }


    public void toastshow(View view, String string) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate( R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(this);
        text.setText(string);
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show(); }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        list_new.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        list_new.setVisibility(View.INVISIBLE);
        list_new.setFilterText(searchBox.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (searchBox.getText().length() == 0) {
            list_new.clearTextFilter();
        }
    }
}