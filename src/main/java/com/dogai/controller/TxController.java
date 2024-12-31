package com.dogai.controller;

import com.dogai.entity.ResultMap;
import com.dogai.service.TxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Solana Tx API")
@RestController
@RequestMapping(value = "/v1/tx")
public class TxController {

    private final TxService txService;

    public TxController(TxService txService) {
        this.txService = txService;
    }

    @ApiOperation("Get Tx Result")
    @GetMapping(value = "/hash/result")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "txHash", value = "Transaction Hash，1 - success， 0 - failed，-1 - tx not found", required = true, dataTypeClass = String.class),
    })
    public ResultMap upload(@RequestParam String txHash) {
        return ResultMap.data(txService.getTxResult(txHash));
    }
}
