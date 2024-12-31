package com.dogai.controller;

import com.dogai.entity.ResultMap;
import com.dogai.service.TokenHolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Token Holder API")
@RestController
@RequestMapping(value = "/v1/token")
public class TokenHolderController {

    private final TokenHolderService tokenHolderService;

    public TokenHolderController(TokenHolderService tokenHolderService) {
        this.tokenHolderService = tokenHolderService;
    }


    @ApiOperation("Get token holder list")
    @GetMapping(value = "/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "pageNum，start from 1", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "limit", value = "page limit", required = true, dataTypeClass = Integer.class)
    })
    public ResultMap holderList(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        return ResultMap.data(tokenHolderService.list(page, limit));
    }
}
