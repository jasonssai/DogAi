package com.dogai.schedule;

import com.dogai.dao.ContractDao;
import com.dogai.dto.Contract;
import com.dogai.service.TokenHolderUpdateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTokenHolderUpdate {

    final
    ContractDao contractDao;

    boolean isLastFinished = true;

    private final TokenHolderUpdateService tokenHolderUpdateService;

    public ScheduleTokenHolderUpdate(TokenHolderUpdateService tokenHolderUpdateService, ContractDao contractDao) {
        this.tokenHolderUpdateService = tokenHolderUpdateService;
        this.contractDao = contractDao;
    }

    @Scheduled(fixedRateString = "${token.update-frequency}")
    public void updateTokenHolder() {
        if (isLastFinished) {
            Contract contract = contractDao.findById(1);
            if (contract != null) {
                try {
                    tokenHolderUpdateService.updateTokenHolder(contract.getTokenAddress());
                    System.out.println("token holder list update finish");
                    isLastFinished = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("token holder list update finish error");
                    isLastFinished = true;
                }
            }
        } else {
            System.out.println("last mission not finish, cancel");
        }
    }
}
