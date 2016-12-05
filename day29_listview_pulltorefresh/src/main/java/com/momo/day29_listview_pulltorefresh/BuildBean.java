package com.momo.day29_listview_pulltorefresh;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016-11-17.
 */
public class BuildBean {

    public String fname;
    public String fcover;
    public String faddress;
    public String price_pre;
    public String price_value;
    public String price_unit;

    public BuildBean() {
        super();
    }


    public BuildBean(JSONObject obj) {
        super();
        fname = obj.optString("fname");
        fcover = obj.optString("fcover");
        faddress = obj.optString("faddress");
        price_pre = obj.optString("price_pre");
        price_value = obj.optString("price_value");
        price_unit = obj.optString("price_unit");


    }
}
