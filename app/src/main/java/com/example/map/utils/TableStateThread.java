package com.example.map.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class TableStateThread implements Runnable{

    private boolean res;
    private int table_id;

    public void setTableID(int table_id){
        this.table_id = table_id;
    }

    public boolean getRes(){
        return res;
    }
    public void run(){
        JSONObject table_info = new JSONObject();
        try {
            table_info = new JSONObject(GetPostUtil.sendPost(
                    "http://10.0.2.2:8081/table/query?tableId=" + table_id, null));
            res = table_info.getBoolean("obj");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
