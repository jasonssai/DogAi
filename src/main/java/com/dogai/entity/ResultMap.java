package com.dogai.entity;


import com.dogai.constants.ResponseCode;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultMap extends HashMap<String, Object> {

    public ResultMap() {
        put("state", true);
        put("code", ResponseCode.SUCCESS);
        put("msg", "success");
    }

    public static ResultMap error(int code, String msg) {
        ResultMap r = new ResultMap();
        r.put("state", false);
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultMap error(int code) {
        ResultMap r = new ResultMap();
        r.put("state", false);
        r.put("code", code);
        r.put("msg", ResponseCode.messageMap.get(code));
        return r;
    }

    public static ResultMap ok(String msg) {
        ResultMap r = new ResultMap();
        r.put("code", ResponseCode.SUCCESS);
        r.put("msg", msg);
        return r;
    }

    public static ResultMap data(Object obj) {

        JSONObject resultJson = new JSONObject();
        resultJson.put("data", obj);

        ResultMap r = new ResultMap();
        r.putAll(resultJson);
        return r;
    }

    public static ResultMap ok(Map<String, Object> par) {
        ResultMap r = new ResultMap();
        r.putAll(par);
        return r;
    }

    public static ResultMap ok() {
        return new ResultMap().put("code", ResponseCode.SUCCESS);
    }

    public ResultMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
