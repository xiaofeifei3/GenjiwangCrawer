package com.chenqihong.genjiwangcrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String URL = ".ganji.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CityBean> cityBeanList = City.getCityList();
                Iterator<CityBean> i = cityBeanList.iterator();
                while(i.hasNext()){
                    CityBean city = i.next();
                    for(int index = 1; index <= 100; index++){
                        String raw = Crawer.syncGet("http://" + city.getPinyin().trim().toLowerCase() + URL + "danbaobaoxian/o" + index + "/");
                        Parser.parse(raw, city.getPinyin().trim().toLowerCase() + URL);

                    }
                }
            }
        }).start();
    }
}
