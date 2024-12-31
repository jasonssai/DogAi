package com.dogai.service;

import com.dogai.dao.TokenHolderDao;
import com.dogai.dto.TokenHolder;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenHolderUpdateService {

    @Autowired
    private SolScanApiService solScanApiService;

    @Autowired
    private TokenHolderDao tokenHolderDao;

    @Transactional
    public void updateTokenHolder(String tokenAddress) {
        Map<String, TokenHolder> tokenMap = new HashMap<>();
        int page = 1;
        int limit = 40;
        JSONObject resultJO = solScanApiService.getTokenHolderList(tokenAddress, page, limit);
        boolean success = resultJO.getBoolean("success");
        if (!success) {
            return;
        }

        JSONObject dataJO = resultJO.getJSONObject("data");
        int total = dataJO.getInteger("total");
        int pageCount = total / limit + 1;
//        System.out.println("total:" + total);
//        System.out.println("pageCount:" + pageCount);
        if (total > 0) {
            JSONArray itemJA = dataJO.getJSONArray("items");
            List<TokenHolder> tokenHolderListTemp = parseItems(tokenAddress, itemJA);

            for (TokenHolder tokenHolder :
                    tokenHolderListTemp) {
                tokenMap.put(tokenHolder.getTokenAccountAddress(), tokenHolder);
            }
        }

        if (total > limit) {
            runRestList(tokenAddress, pageCount, limit, tokenMap);
        }

//        System.out.println("total map size:" + tokenMap.size());

        tokenHolderDao.deleteAllRows();
        tokenHolderDao.saveAll(tokenMap.values());
    }

    private void runRestList(String tokenAddress, int pageCount, int limit, Map<String, TokenHolder> tokenMap) {
        for (int i = 2; i <= pageCount; i++) {
            JSONObject resultJO = solScanApiService.getTokenHolderList(tokenAddress, i, limit);
            boolean success = resultJO.getBoolean("success");
            if (!success) {
                return;
            }

            JSONObject dataJO = resultJO.getJSONObject("data");
            JSONArray itemJA = dataJO.getJSONArray("items");
            List<TokenHolder> tokenHolderListTemp = parseItems(tokenAddress, itemJA);
            for (TokenHolder tokenHolder :
                    tokenHolderListTemp) {
                tokenMap.put(tokenHolder.getTokenAccountAddress(), tokenHolder);
            }
        }
    }

    private List<TokenHolder> parseItems(String tokenAddress, JSONArray itemJA) {
        List<TokenHolder> tokenHolderList = new ArrayList<>();
        for (int i = 0; i < itemJA.size(); i++) {
            JSONObject itemJO = itemJA.getJSONObject(i);
            String tokenAccountAddress = itemJO.getString("address");
            String tokenAccountOwnerAddress = itemJO.getString("owner");
            BigInteger amountBI = itemJO.getBigInteger("amount");
            int decimals = itemJO.getIntValue("decimals");
            int tokenAmountRank = itemJO.getInteger("rank");

            TokenHolder tokenHolder = new TokenHolder();
            tokenHolder.setTokenAccountAddress(tokenAccountAddress);
            tokenHolder.setTokenAccountOwnerAddress(tokenAccountOwnerAddress);
            tokenHolder.setAmount(amountBI);
            tokenHolder.setTokenDecimals(decimals);
            tokenHolder.setTokenAmountRank(tokenAmountRank);
            tokenHolder.setTokenAddress(tokenAddress);

            tokenHolderList.add(tokenHolder);
        }
        return tokenHolderList;
    }
}
