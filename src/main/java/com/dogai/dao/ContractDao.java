package com.dogai.dao;

import com.dogai.dto.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractDao extends JpaRepository<Contract, Long> {

    Contract findById(long id);
}
