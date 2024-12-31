package com.dogai.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TxService {

    private final SolScanApiService solScanApiService;

    public TxService(SolScanApiService solScanApiService) {
        this.solScanApiService = solScanApiService;
    }

    public int getTxResult(String txHash) {
        try {
            JSONObject txResult = solScanApiService.getTxResult(txHash);
            if (txResult.getBoolean("success")) {
                return txResult.getJSONObject("data").getIntValue("status");
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("get tx result error：" + txHash);
            return -1;
        }
    }
}
