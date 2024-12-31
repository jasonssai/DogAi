package com.dogai.schedule;

import com.dogai.dao.NftMintRecordDao;
import com.dogai.dto.NftMintRecord;
import com.dogai.service.SolScanApiService;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleNftMintTxResultUpdate {

    private final NftMintRecordDao nftMintRecordDao;

    private final SolScanApiService solScanApiService;

    boolean isUpdate = false;

    public ScheduleNftMintTxResultUpdate(NftMintRecordDao nftMintRecordDao, SolScanApiService solScanApiService) {
        this.nftMintRecordDao = nftMintRecordDao;
        this.solScanApiService = solScanApiService;
    }

    @Scheduled(fixedRateString = "10000")
    public void nftMintTxResultUpdate() {
        if (isUpdate) {
            System.out.println("nft mint tx result update last mission not finish, cancel");
            return;
        }

        isUpdate = true;
        List<NftMintRecord> nftMintRecords = nftMintRecordDao.findAllByStatus(2);
        for (NftMintRecord nftMintRecord :
                nftMintRecords) {
            try {
                JSONObject txResult = solScanApiService.getTxResult(nftMintRecord.getTxHash());
                if (txResult.getBoolean("success")) {
                    int status = txResult.getJSONObject("data").getIntValue("status");

                    JSONObject tokensJO = txResult.getJSONObject("metadata").getJSONObject("tokens");
                    if (tokensJO.size() > 0) {
                        nftMintRecord.setTokenAddress(tokensJO.keySet().iterator().next());
                    }
                    nftMintRecord.setStatus(status);
                } else {
                    long overTime = 1000 * 30;
                    long timeDiff = System.currentTimeMillis() - nftMintRecord.getCreatedTime().getTime();
                    System.out.println("tx over time：" + nftMintRecord.getTxHash() + "，time diff：" + timeDiff);
                    if (timeDiff > overTime) {
                        nftMintRecord.setStatus(0);
                    }
                }

                nftMintRecordDao.save(nftMintRecord);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("get tx result error：" + nftMintRecord.getTxHash());
            }
        }
        isUpdate = false;
    }
}
