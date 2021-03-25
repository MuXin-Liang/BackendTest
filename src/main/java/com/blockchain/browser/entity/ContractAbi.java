package com.blockchain.browser.entity;

import javax.persistence.*;

@Entity
@Table(name = "contract_abi")
public class ContractAbi {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;   //自增

    @Id
    private String contractname;
    @Lob @Basic(fetch = FetchType.LAZY) @Column(columnDefinition = "text")
    private String contractabi;
    private String contractaddress;


    public String getContractabi() {
        return contractabi;
    }

    public String getContractname() {
        return contractname;
    }

    public String getContractaddress() {
        return contractaddress;
    }

    public void setContractaddress(String contractaddress) {
        this.contractaddress = contractaddress;
    }

    public void setContractabi(String contractabi) {
        this.contractabi = contractabi;
    }

    public void setContractname(String contractname) {
        this.contractname = contractname;
    }
}
