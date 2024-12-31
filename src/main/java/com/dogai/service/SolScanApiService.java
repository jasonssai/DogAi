package com.dogai.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class SolScanApiService {

    private static final String API_TOKEN = "xxx";

    /**
     * 请求TokenHolderList
     */
    public JSONObject getTokenHolderList(String tokenAddress, int page, int limit) {
        System.out.println("cur request page num：" + page);
        String url = "xxx" + tokenAddress + "&page=" + page + "&page_size=" + limit;
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("token", API_TOKEN)
                .addHeader("Content-Type", "application/json")
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                String bodyResult = response.body().string();
                if (StringUtils.hasText(bodyResult)) {
                    return JSON.parseObject(bodyResult);
                }
            }
            throw new RuntimeException("SOL API HolderList Request Failed");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public JSONObject getTxResult(String txHash) {
        String url = "xxx" + txHash;
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("token", API_TOKEN)
                .addHeader("Content-Type", "application/json")
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                String bodyResult = response.body().string();
                if (StringUtils.hasText(bodyResult)) {
                    return JSON.parseObject(bodyResult);
                }
            }
            throw new RuntimeException("SOL API Get Tx Hash Result Failed");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
