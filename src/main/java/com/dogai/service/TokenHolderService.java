package com.dogai.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dogai.dao.ContractDao;
import com.dogai.dao.TokenHolderDao;
import com.dogai.dto.Contract;
import com.dogai.dto.TokenHolder;
import com.dogai.entity.DogBodyShapeParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class TokenHolderService {

    private final TokenHolderDao tokenHolderDao;

    final
    ContractDao contractDao;

    private final LogoService logoService;

    @Value("${token.supply}")
    private BigDecimal tokenSupplyBD;

    public TokenHolderService(TokenHolderDao tokenHolderDao, ContractDao contractDao, LogoService logoService) {
        this.tokenHolderDao = tokenHolderDao;
        this.contractDao = contractDao;
        this.logoService = logoService;
    }

    public JSONObject list(Integer page, Integer limit) {
        JSONObject listResultJO = new JSONObject();
        Contract contract = contractDao.findById(1);
        if (contract != null) {
            List<TokenHolder> tokenHolderList;
            Sort sort = Sort.by(Sort.Direction.ASC, "tokenAmountRank");
            if (page != null && limit != null) {
                Pageable pageable = PageRequest.of(page - 1, limit, sort);
                Page<TokenHolder> tokenHolderPage = tokenHolderDao.findAll(pageable);
                tokenHolderList = tokenHolderPage.toList();
            } else {
                tokenHolderList = tokenHolderDao.findAll(sort);
            }
            listResultJO.put("total", tokenHolderDao.count());
            listResultJO.put("supply", tokenSupplyBD);
            listResultJO.put("page", page);
            listResultJO.put("contract", contract.getTokenAddress());
            listResultJO.put("list", createDogLogo(tokenHolderList, tokenSupplyBD));

            return listResultJO;
        }
        return listResultJO;
    }

    private JSONArray createDogLogo(List<TokenHolder> tokenHolderList, BigDecimal tokenSupplyBD) {
        JSONArray holderJA = new JSONArray();
        for (TokenHolder tokenHolder : tokenHolderList) {
            JSONObject holderJO = new JSONObject();
            String address = tokenHolder.getTokenAccountOwnerAddress();
//            String address = tokenHolder.getTokenAccountAddress();
            BigInteger amount = tokenHolder.getAmount();
            int rank = tokenHolder.getTokenAmountRank();

            holderJO.put("o", address);
            BigDecimal amountBD = new BigDecimal(amount);
            holderJO.put("v", amountBD.divide(BigDecimal.TEN.pow(tokenHolder.getTokenDecimals()), 6, BigDecimal.ROUND_HALF_UP));
            holderJO.put("r", rank);
            holderJO.put("p", amountBD.divide(tokenSupplyBD).divide(BigDecimal.TEN.pow(tokenHolder.getTokenDecimals())).multiply(BigDecimal.TEN.pow(2)).toPlainString());

            holderJO.put("body", createBodyParam(address));

            holderJA.add(holderJO);
        }

        return holderJA;
    }

    public JSONObject createBodyParam(String address) {
        DogBodyShapeParam dogBodyParam = logoService.calcDogBodyParams(address);
        JSONObject dogBodyJO = new JSONObject();
        dogBodyJO.put("he", dogBodyParam.getHeadShape().getNum() + "_" + dogBodyParam.getHeadShape().getBodyPartColor());
        dogBodyJO.put("ea", dogBodyParam.getEarsShape().getNum() + "_" + dogBodyParam.getEarsShape().getBodyPartColor());
        dogBodyJO.put("ey", dogBodyParam.getEyesShape().getNum());
        dogBodyJO.put("n_m", dogBodyParam.getNoseAndMouthShape().getNum());
        dogBodyJO.put("co", dogBodyParam.getCorner());
        return dogBodyJO;
    }
}
