package com.blockchain.browser.dao;

import com.blockchain.browser.entity.ContractAbi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractAbiDao extends JpaRepository<ContractAbi,Integer> {

    List<ContractAbi> findByContractname(String contractname);

    @Query(name = "findAllContractAbi",nativeQuery = true,value = "select * from contract_abi")
    List<ContractAbi> findAllContractAbi();

    ContractAbi findByContractaddress(String contractaddress);

}
