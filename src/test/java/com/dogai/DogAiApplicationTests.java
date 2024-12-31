package com.dogai;

import com.dogai.dao.TokenHolderDao;
import com.dogai.service.LogoService;
import com.dogai.service.SolScanApiService;
import com.dogai.service.TokenHolderUpdateService;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DogAiApplicationTests {

    @Autowired
    private LogoService logoService;

    @Autowired
    private SolScanApiService solScanApiService;

    @Autowired
    private TokenHolderDao tokenHolderDao;

    @Autowired
    private TokenHolderUpdateService tokenHolderUpdateService;

    @Test
    void calcDogBodyParams() {
        logoService.calcDogBodyParams("xxxx");
    }

    @Test
    void getTokenHolderList() {
        JSONObject resultJO = solScanApiService.getTokenHolderList("xxxx", 1, 10);
        System.out.println(resultJO);
    }

    @Test
    void deleteAllRecord() {
        tokenHolderDao.deleteAllRows();
    }

    @Test
    void contextLoads() {
        tokenHolderUpdateService.updateTokenHolder("xxx");
    }
}
