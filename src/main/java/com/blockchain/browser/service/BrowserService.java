package com.blockchain.browser.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.blockchain.browser.common.ContractProperties;
import com.blockchain.browser.common.ResponseEnum;
import com.blockchain.browser.common.ServerResponse;
import com.blockchain.browser.dao.ContractAbiDao;
import com.blockchain.browser.entity.ContractAbi;
import com.blockchain.browser.helper.BsnHelper;
import com.bsnbase.sdk.client.fiscobcos.FiscobcosClient;
import com.bsnbase.sdk.entity.req.fiscobcos.ReqGetTransaction;
import com.bsnbase.sdk.entity.res.fiscobcos.ResGetTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class BrowserService {
    @Autowired
    private BsnHelper bsnHelper;
    @Autowired
    private ContractAbiDao contractAbiDao;
    @Autowired
    private ContractProperties contractProperties;

    public ServerResponse getTxInfo(String Tx_Hash){
        try {
            ReqGetTransaction reqGetTxReceiptByTxHash = new ReqGetTransaction();
            reqGetTxReceiptByTxHash.setTxHash(Tx_Hash);
            ResGetTransaction txInfoByTxHash = FiscobcosClient.getTxInfoByTxHash(reqGetTxReceiptByTxHash);
            System.out.println(JSONObject.toJSONString(txInfoByTxHash, new SerializerFeature[]{SerializerFeature.PrettyFormat}));
            JSONObject result = (JSONObject) JSON.toJSON(txInfoByTxHash);
            ContractAbi contractAbi = contractAbiDao.findByContractaddress(txInfoByTxHash.getTo());
            if(contractAbi == null){
                return ServerResponse.getInstance().failed().responseEnum(ResponseEnum.ABI_NOT_EXIST).data(txInfoByTxHash);
            }
            result.put("abi",contractAbi.getContractabi());
            result.put("name",contractAbi.getContractname());
            JSONObject args = bsnHelper.decodeAbi(contractAbi.getContractabi(),txInfoByTxHash.getInput());
            result.put("method",args);
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.GET_SUCCESS).data(result);
        } catch (Exception var3) {
            var3.printStackTrace();
            return ServerResponse.getInstance().failed().responseEnum(ResponseEnum.GET_FAILED).data(var3.getMessage());
        }

    }

    public ServerResponse getAllAbi(){
        try {
            List<ContractAbi> abis = contractAbiDao.findAllContractAbi();
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.GET_SUCCESS).data(abis);
        } catch (Exception var3) {
            var3.printStackTrace();
            return ServerResponse.getInstance().failed().responseEnum(ResponseEnum.GET_FAILED).data(var3.getMessage());
        }
    }

    public ServerResponse insertAbi(String name,String abi,String address){
        try {
            ContractAbi contractAbi = new ContractAbi();
            contractAbi.setContractname(name);
            contractAbi.setContractabi(abi);
            contractAbi.setContractaddress(address);
            ContractAbi res = contractAbiDao.save(contractAbi);
            return ServerResponse.getInstance().ok().responseEnum(ResponseEnum.SAVE_SUCCESS).data(res);
        } catch (Exception var3) {
            var3.printStackTrace();
            return ServerResponse.getInstance().failed().responseEnum(ResponseEnum.SAVE_FAILED).data(var3.getMessage());
        }
    }

    public ServerResponse initial(){
        ContractAbi contractAbi = new ContractAbi();
        contractAbi.setContractaddress(contractProperties.getAuthority_addr());
        contractAbi.setContractabi(contractProperties.getAuthority_abi());
        contractAbi.setContractname(contractProperties.getAuthority_name());
        contractAbiDao.save(contractAbi);

        contractAbi.setContractaddress(contractProperties.getEvidence_addr());
        contractAbi.setContractabi(contractProperties.getEvidence_abi());
        contractAbi.setContractname(contractProperties.getEvidence_name());
        contractAbiDao.save(contractAbi);

        contractAbi.setContractaddress(contractProperties.getTort_addr());
        contractAbi.setContractabi(contractProperties.getTort_abi());
        contractAbi.setContractname(contractProperties.getTort_name());
        contractAbiDao.save(contractAbi);

        return getAllAbi();
    }





}
