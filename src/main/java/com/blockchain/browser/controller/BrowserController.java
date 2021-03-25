package com.blockchain.browser.controller;
import com.blockchain.browser.common.ServerResponse;
import com.blockchain.browser.service.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
public class BrowserController {

    @Autowired
    private BrowserService browserService;


    @RequestMapping("/transactionDetail")
    @ResponseBody
    public ServerResponse transactionDetail(@RequestParam("txHash") String txHash){
        return browserService.getTxInfo(txHash);
    }

    @RequestMapping("/getAllAbi")
    @ResponseBody
    public ServerResponse getAllAbi(){
        return browserService.getAllAbi();
    }

    @RequestMapping("/insertAbi")
    @ResponseBody
    public ServerResponse insertAbi(@RequestParam("contractName")String contractName,@RequestParam("contractAbi")String contractAbi,@RequestParam("contractAddress")String contractAddress){
        return browserService.insertAbi(contractName,contractAbi,contractAddress);
    }

    @RequestMapping("/initial")
    @ResponseBody
    public ServerResponse initial(){
        return browserService.initial();
    }


}
