package com.blockchain.browser.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bsnbase.sdk.client.fiscobcos.FiscobcosClient;
import com.bsnbase.sdk.client.fiscobcos.service.UserService;
import com.bsnbase.sdk.entity.config.Config;
import com.bsnbase.sdk.entity.req.fiscobcos.ReqKeyEscrow;
import com.bsnbase.sdk.entity.req.fiscobcos.ReqUserRegister;
import com.bsnbase.sdk.entity.res.fiscobcos.ResKeyEscrow;
import com.bsnbase.sdk.util.exception.GlobalException;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.tx.txdecode.InputAndOutputResult;
import org.fisco.bcos.web3j.tx.txdecode.ResultEntity;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "bsn")
public class BsnHelper {

    public String appCode;
    public String userCode;
    public String api;
    public Resource prk;
    public Resource puk;
    public String mspDir;
    public String userid;

    //初始化config。
    @PostConstruct
    public void initConfig() throws IOException {
        try {
            Config config = new Config();
            config.setAppCode(appCode);
            config.setUserCode(userCode);
            config.setApi(api);
            String storage_path = System.getProperty("user.dir")+"/config/";
            config.setMspDir(storage_path+mspDir);
            config.setPrk(readResource(prk));
            config.setPuk(readResource(puk));
            config.setTestServerIdn(true);
            config.initConfig(config);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public String readResource(Resource resource) {
        String txt = "";
        try {
            //获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
            InputStream stream = resource.getInputStream();
            StringBuilder buffer = new StringBuilder();
            byte[] bytes = new byte[1024];
            try {
                for (int n; (n = stream.read(bytes)) != -1; ) {
                    buffer.append(new String(bytes, 0, n));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt;
    }

    //解析abi
    public JSONObject decodeAbi(String abi,String input) throws Exception{
        String bin = "";
        EncryptType encryptType = new EncryptType(1);
        TransactionDecoder txDecodeSampleDecoder = TransactionDecoderFactory.buildTransactionDecoder(abi, bin);
        String jsonResult = txDecodeSampleDecoder.decodeInputReturnJson(input);
        InputAndOutputResult objectResult = txDecodeSampleDecoder.decodeInputReturnObject(input);
//        System.out.println("json => \n" + jsonResult);
//        System.out.println("object => \n" + objectResult);
        String function  = objectResult.getFunction();
        String methodId = objectResult.getMethodID();
        List<ResultEntity> resultEntities=  objectResult.getResult();

        JSONObject result = new JSONObject();
        result.put("funcName",function);//函数名
        result.put("methodId",methodId);//methodID
        JSONArray array = new JSONArray();
        for(ResultEntity resultEntity:resultEntities){
            JSONObject temp = new JSONObject();
            temp.put("argName",resultEntity.getName());//函数参数名
            temp.put("argType",resultEntity.getType());//函数参数类型
            temp.put("argValue",resultEntity.getData());//函数的数据
            array.add(temp);
        }
        result.put("args",array);
        return result;
    }

    //调用用户注册接口
    public void userRegister() {

        ReqUserRegister register = new ReqUserRegister();
        register.setUserId("test22");
        try {
            UserService.userRegister(register);
        } catch (GlobalException g) {
            g.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    //调用发送交易接口
    public ResKeyEscrow sendTransaction(String userId, String contractName, String funcName, String funcParam) {
        try {
            ReqKeyEscrow reqKeyEscrow = new ReqKeyEscrow();
            reqKeyEscrow.setUserId(userId);
            reqKeyEscrow.setContractName(contractName);
            reqKeyEscrow.setFuncName(funcName);
            reqKeyEscrow.setFuncParam(funcParam);
            return FiscobcosClient.reqChainCode(reqKeyEscrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getApi() {
        return api;
    }

    public String getAppCode() {
        return appCode;
    }

    public String getMspDir() {
        return mspDir;
    }

    public String getUserid() {
        return userid;
    }

    public Resource getPrk() {
        return prk;
    }

    public Resource getPuk() {
        return puk;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public void setMspDir(String mspDir) {
        this.mspDir = mspDir;
    }

    public void setPrk(Resource prk) {
        this.prk = prk;
    }

    public void setPuk(Resource puk) {
        this.puk = puk;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
