package com.dogai.dao;

import com.dogai.dto.NftMintRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NftMintRecordDao extends JpaRepository<NftMintRecord, Long> {

    NftMintRecord findByAddressAndStatusNot(String address, int status);

    NftMintRecord findTopByAddressOrderByCreatedTimeDesc(String address);

    NftMintRecord findByAddressAndStatus(String address, int status);

    NftMintRecord findByAddressAndTxHash(String address, String txHash);

    List<NftMintRecord> findAllByStatus(int status);
}
