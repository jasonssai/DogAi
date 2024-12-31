package com.dogai.controller;

import com.dogai.entity.ResultMap;
import com.dogai.service.NftService;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "NFT Resource API")
@RestController
@RequestMapping(value = "/v1/nft")
public class NftController {

    private final NftService nftService;

    public NftController(NftService nftService) {
        this.nftService = nftService;
    }


    @ApiOperation("create nft resource")
    @PostMapping(value = "/resource/create")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "image file", required = true, dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "address", value = "user address", required = true, dataTypeClass = String.class),
    })
    public ResultMap nftResourceCreate(@RequestParam MultipartFile file, @RequestParam String address) {
        System.out.println("nftResourceCreate address：" + address);
        JSONObject resultJO = nftService.nftResourceCreate(file, address);
        if (resultJO == null) {
            return ResultMap.error(201, "nft resource create fail");
        }
        return ResultMap.data(resultJO);
    }

    @ApiOperation("create nft resource with image url")
    @PostMapping(value = "/resource/create/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "user address", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "url", value = "image url", required = true, dataTypeClass = String.class),
    })
    public ResultMap nftResourceCreateWithImageUrl(@RequestParam String address, @RequestParam String url) {
        JSONObject resultJO = nftService.nftResourceCreate(address, url);
        if (resultJO == null) {
            return ResultMap.error(201, "nft resource create fail");
        }
        return ResultMap.data(resultJO);
    }

    @ApiOperation("upload nft create record")
    @PostMapping(value = "/create/record/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "user address", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "txHash", value = "transaction hash", required = true, dataTypeClass = String.class),
    })
    public ResultMap uploadNftCreateRecord(@RequestParam String address, @RequestParam String txHash) {
        return ResultMap.data(nftService.uploadNftCreateRecord(address, txHash));
    }

    @ApiOperation("if address can mint")
    @GetMapping(value = "/mint/address/avlid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "user address", required = true, dataTypeClass = String.class),
    })
    public ResultMap isAddressValid(@RequestParam String address) {
        return nftService.isAddressValid(address);
    }

    @ApiOperation("get mint token address")
    @GetMapping(value = "/mint/token/address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "user address, -107：nft minted fail, -108：nft is minting, -106：not mint yet", required = true, dataTypeClass = String.class),
    })
    public ResultMap getMintTokenAddress(@RequestParam String address) {
        return nftService.getMintTokenAddress(address);
    }
}
