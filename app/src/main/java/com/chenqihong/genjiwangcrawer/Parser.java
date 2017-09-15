package com.chenqihong.genjiwangcrawer;

import android.util.Log;

import java.io.*;

/**
 * Created by chenqihong on 2017/9/15.
 */

public class Parser {
    public static String TYPE_1_NAME_TAG = "<a class=\"f14 list-info-title\"";
    public static String TYPE_1_URL_TAG = "href=\"";
    public static String TYPE_1_URL_FINISH_TAG = "\" gjalog=";
    public static String TYPE_1_NAME_TAG_2 = "@position=title\">";
    public static String TYPE_1_NAME_FINISH_TAG = "</a>";
    public static String TYPE_1_PHONE_TAG = "@phone=";
    public static String TYPE_1_PHONE_FINISH_TAG = "@";

    public static String TYPE_2_NAME_TAG = "<a class=\"f14 list-info-title js_wuba_stas\"";
    public static String TYPE_2_NAME_TAG_2 = ">";
    public static String TYPE_2_NAME_FINISH_TAG = "</a";
    public static String TYPE_2_PHONE_TAG = "<span style=\"display: none;\" class=\"J_tel_phone_span\">";
    public static String TYPE_2_PHONE_FINISH_TAG = "</span>";

    public static void parse(String raw, String mainUrl){
        if(raw.contains(TYPE_1_NAME_TAG)){
            String[] type1Names = raw.split(TYPE_1_NAME_TAG);
            for(int i = 1; i < type1Names.length; i++){
                String url = type1Names[i].split(TYPE_1_URL_TAG)[1].split(TYPE_1_URL_FINISH_TAG)[0];
                String phoneRaw = null;
                if(2 > type1Names[i].split(TYPE_1_NAME_TAG_2).length){
                    Log.e("blackList", "error" + type1Names[i]);
                    return;
                }

                String name = type1Names[i].split(TYPE_1_NAME_TAG_2)[1].split(TYPE_1_NAME_FINISH_TAG)[0];
                if(url.contains("http://") || url.contains("http://")) {
                    phoneRaw = Crawer.syncGet(url);
                }else{
                    phoneRaw = Crawer.syncGet("http://" + mainUrl + url);
                }

                if(2 > phoneRaw.split(TYPE_1_PHONE_TAG).length){
                    Log.e("blackList", "error" + phoneRaw);
                    return;
                }
                String phone = phoneRaw.split(TYPE_1_PHONE_TAG)[1].split(TYPE_1_PHONE_FINISH_TAG)[0];
                Log.e("blackList", name + "," + phone + "\n");
                Writer.write(name + "," + phone + "\n");
            }
        }

        if (raw.contains(TYPE_2_NAME_TAG)) {
            String[] type2Names = raw.split(TYPE_2_NAME_TAG);
            for(int i=1; i < type2Names.length; i++){
                String name = type2Names[i].split(TYPE_2_NAME_TAG_2)[1].split(TYPE_2_NAME_FINISH_TAG)[0];
                String phone = type2Names[i].split(TYPE_2_PHONE_TAG)[1].split(TYPE_2_PHONE_FINISH_TAG)[0];
                Log.e("blackList", name + "," + phone + "\n");
                Writer.write(name + "," + phone + "\n");
            }
        }
    }
}
