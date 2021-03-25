package com.blockchain.browser.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "contract")
public class ContractProperties {
        public String tort_addr;
        public String tort_name;
        public String tort_abi;

        public String evidence_addr;
        public String evidence_name;
        public String evidence_abi;

        public String authority_addr;
        public String authority_name;
        public String authority_abi;

    public String getAuthority_abi() {
        return authority_abi;
    }

    public String getAuthority_addr() {
        return authority_addr;
    }

    public String getAuthority_name() {
        return authority_name;
    }

    public String getEvidence_abi() {
        return evidence_abi;
    }

    public String getEvidence_addr() {
        return evidence_addr;
    }

    public String getEvidence_name() {
        return evidence_name;
    }

    public String getTort_abi() {
        return tort_abi;
    }

    public String getTort_addr() {
        return tort_addr;
    }

    public String getTort_name() {
        return tort_name;
    }

    public void setAuthority_abi(String authority_abi) {
        this.authority_abi = authority_abi;
    }

    public void setAuthority_addr(String authority_addr) {
        this.authority_addr = authority_addr;
    }

    public void setAuthority_name(String authority_name) {
        this.authority_name = authority_name;
    }

    public void setEvidence_abi(String evidence_abi) {
        this.evidence_abi = evidence_abi;
    }

    public void setEvidence_addr(String evidence_addr) {
        this.evidence_addr = evidence_addr;
    }

    public void setEvidence_name(String evidence_name) {
        this.evidence_name = evidence_name;
    }

    public void setTort_abi(String tort_abi) {
        this.tort_abi = tort_abi;
    }

    public void setTort_addr(String tort_addr) {
        this.tort_addr = tort_addr;
    }

    public void setTort_name(String tort_name) {
        this.tort_name = tort_name;
    }
}
