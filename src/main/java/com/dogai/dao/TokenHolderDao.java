package com.dogai.dao;

import com.dogai.dto.TokenHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TokenHolderDao extends JpaRepository<TokenHolder, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM TokenHolder")
    void deleteAllRows();

    List<TokenHolder> findAllByTokenAccountOwnerAddress(String address);
}
