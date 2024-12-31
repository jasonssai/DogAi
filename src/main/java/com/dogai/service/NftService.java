package com.dogai.service;

import com.dogai.dao.NftMintRecordDao;
import com.dogai.dao.TokenHolderDao;
import com.dogai.dto.NftMintRecord;
import com.dogai.dto.TokenHolder;
import com.dogai.entity.ResultMap;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class NftService {

    @Value("${upload.dir.image}")
    private String uploadDirImage;

    @Value("${upload.dir.json}")
    private String uploadDirJson;

    @Value("${uri.prefix}")
    private String uriPrefix;

    @Value("${token.mint-hold-limit}")
    private long mintHoldLimit;

    private final NftMintRecordDao nftMintRecordDao;

    private final TokenHolderDao tokenHolderDao;

    private final TokenHolderService tokenHolderService;

    public NftService(NftMintRecordDao nftMintRecordDao, TokenHolderDao tokenHolderDao, TokenHolderService tokenHolderService) {
        this.nftMintRecordDao = nftMintRecordDao;
        this.tokenHolderDao = tokenHolderDao;
        this.tokenHolderService = tokenHolderService;
    }

    public JSONObject nftResourceCreate(MultipartFile file, String address) {
        JSONObject bodyParamJO = tokenHolderService.createBodyParam(address);
        String nftNumber = createNftNumber(bodyParamJO);
        nftNumber = nftNumber.replaceAll("c", "");
        JSONObject nftResourceJO = createJson(address, bodyParamJO, nftNumber);

        if (file.isEmpty()) {
            System.out.println("file is Empty");
            return null;
        }

        try {
            saveImage(nftNumber, file);
            saveJsonFile(nftNumber, nftResourceJO.toJSONString());

            JSONObject resultJO = new JSONObject();
            resultJO.put("uri_json", uriPrefix + File.separator + "json" + File.separator + nftNumber + ".json");
            resultJO.put("uri_image", uriPrefix + File.separator + "img" + File.separator + nftNumber + ".png");
            resultJO.put("nft_number", nftNumber);
            resultJO.put("address", address);
            resultJO.put("name", "DogAi#" + nftNumber);
            resultJO.put("symbol", "DogAi");

            return resultJO;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("nft resource create fail");
            return null;
        }
    }

    private void saveImage(String nftNumber, MultipartFile file) throws IOException {
        File destFile = new File(uploadDirImage + File.separator + nftNumber + ".png");
        if (!destFile.exists()) {
            boolean isCreateSuccess = destFile.createNewFile();
            if (isCreateSuccess) {
                file.transferTo(Paths.get(uploadDirImage + File.separator + nftNumber + ".png"));
            } else {
                System.out.println("nft resource create fail：" + nftNumber);
                throw new IOException("nft image resource create fail");
            }
        }
    }

    private void saveJsonFile(String nftNumber, String jsonContent) throws IOException {
        File file = new File(uploadDirJson + File.separator + nftNumber + ".json");
        if (!file.exists()) {
            FileWriter writer = new FileWriter(file);
            writer.write(jsonContent);
            writer.close();
        }
    }

    public JSONObject createJson(String address, JSONObject bodyParamJO, String nftNumber) {
        JSONObject nftJson = new JSONObject();
        nftJson.put("name", "DogAi#" + nftNumber);
        nftJson.put("symbol", "DogAi");
        nftJson.put("description", "Based on the wallet address, a unique and one-of-a-kind puppy is mapped using AI algorithms");
        nftJson.put("seller_fee_basis_points", 500);
        nftJson.put("external_url", "https://dogai.space");
        nftJson.put("image", uriPrefix + File.separator + "img" + File.separator + nftNumber + ".png");

        nftJson.put("attributes", createAttributes(bodyParamJO));

        JSONObject propertyJO = new JSONObject();
        propertyJO.put("creators", createCreators(address));
        propertyJO.put("category", "image");
        propertyJO.put("files", createFiles(nftNumber, null));
        propertyJO.put("collection", createCollectionJO());

        nftJson.put("properties", propertyJO);

        System.out.println(nftJson);
        return nftJson;
    }

    private Object createCollectionJO() {
        JSONObject collectionJO = new JSONObject();
        collectionJO.put("name", "DogAi");
        collectionJO.put("family", "DogAi");
        return collectionJO;
    }

    private String createNftNumber(JSONObject bodyParamJO) {
        String headShape = bodyParamJO.getString("he");
        String earShape = bodyParamJO.getString("ea");
        String eyeShape = bodyParamJO.getString("ey");
        String noseAndMouth = bodyParamJO.getString("n_m");
        String number = headShape + earShape + eyeShape + noseAndMouth;
        return number.replaceAll("_", "");
    }

    private JSONArray createFiles(String nftNumber, String imageUrl) {
        JSONArray filesJA = new JSONArray();
        JSONObject fileJO = new JSONObject();
        fileJO.put("type", "image/png");
        if (StringUtils.hasText(imageUrl)) {
            fileJO.put("url", imageUrl);
        } else {
            fileJO.put("uri", uriPrefix + File.separator + "img" + File.separator + nftNumber + ".png");
        }

        return filesJA.fluentAdd(fileJO);
    }

    private JSONArray createCreators(String address) {
        JSONArray creatorsJA = new JSONArray();
        JSONObject creatorJO = new JSONObject();
        creatorJO.put("address", address);
        creatorJO.put("share", 100);

        creatorsJA.add(creatorJO);
        return creatorsJA;
    }

    private JSONArray createAttributes(JSONObject bodyParam) {

        JSONArray attributeJA = new JSONArray();
        JSONObject attributeHeadJO = new JSONObject();
        attributeHeadJO.put("trait_type", "head");
        attributeHeadJO.put("value", bodyParam.getString("he"));

        JSONObject attributeEarsJO = new JSONObject();
        attributeEarsJO.put("trait_type", "ears");
        attributeEarsJO.put("value", bodyParam.getString("ea"));

        JSONObject attributeEyesJO = new JSONObject();
        attributeEyesJO.put("trait_type", "eyes");
        attributeEyesJO.put("value", bodyParam.getString("ey"));

        JSONObject attributeNoseAndMouthJO = new JSONObject();
        attributeNoseAndMouthJO.put("trait_type", "nose_mouth");
        attributeNoseAndMouthJO.put("value", bodyParam.getString("n_m"));

        attributeJA.add(attributeHeadJO);
        attributeJA.add(attributeEarsJO);
        attributeJA.add(attributeEyesJO);
        attributeJA.add(attributeNoseAndMouthJO);
        return attributeJA;
    }

    public ResultMap isAddressValid(String address) {
        NftMintRecord nftMintRecord = nftMintRecordDao.findByAddressAndStatusNot(address, 0);
        if (nftMintRecord == null) {
            if (isHasEnoughToken(address)) {
                return ResultMap.ok();
            }
            // not enough token
            return ResultMap.error(-104, "wallet have not enough token");
        } else {
            return ResultMap.error(-105, nftMintRecord.getTokenAddress());
        }
    }

    /**
     * 判断token拥有数量是否超过至指定数量
     */
    public boolean isHasEnoughToken(String address) {
        List<TokenHolder> tokenHolders = tokenHolderDao.findAllByTokenAccountOwnerAddress(address);
        BigInteger total = BigInteger.ZERO;
        for (TokenHolder tokenHolder : tokenHolders) {
            total = total.add(tokenHolder.getAmount());
        }
        System.out.println(address + " have " + total + " token");
        return total.compareTo(BigInteger.valueOf(mintHoldLimit)) > 0;
    }

    public ResultMap uploadNftCreateRecord(String address, String txHash) {
        NftMintRecord nftMintRecord = nftMintRecordDao.findByAddressAndTxHash(address, txHash);
        if (nftMintRecord == null) {
            nftMintRecord = new NftMintRecord();
            nftMintRecord.setAddress(address);
            nftMintRecord.setTxHash(txHash);
            nftMintRecord.setCreatedTime(new Date());
            nftMintRecordDao.save(nftMintRecord);
        }
        return ResultMap.ok();
    }

    public ResultMap getMintTokenAddress(String address) {
        NftMintRecord nftMintRecord = nftMintRecordDao.findTopByAddressOrderByCreatedTimeDesc(address);
        if (nftMintRecord == null) {
            return ResultMap.error(-106, "not mint yet");
        }

        nftMintRecord = nftMintRecordDao.findByAddressAndStatus(address, 2);
        if (nftMintRecord != null) {
            return ResultMap.error(-108, "nft is minting");
        }

        nftMintRecord = nftMintRecordDao.findByAddressAndStatus(address, 1);
        if (nftMintRecord != null) {
            return ResultMap.data(nftMintRecord.getTokenAddress());
        }

        return ResultMap.error(-107, "nft minted fail");
    }

    public JSONObject nftResourceCreate(String address, String imageUrl) {
        JSONObject bodyParamJO = tokenHolderService.createBodyParam(address);
        String nftNumber = createNftNumber(bodyParamJO);
        JSONObject nftResourceJO = createJsonWithImageUrl(address, bodyParamJO, nftNumber, imageUrl);

        try {
            saveJsonFile(nftNumber, nftResourceJO.toJSONString());

            JSONObject resultJO = new JSONObject();
            resultJO.put("uri_json", uriPrefix + File.separator + "json" + File.separator + nftNumber + ".json");
            resultJO.put("uri_image", imageUrl);
            resultJO.put("nft_number", nftNumber);
            resultJO.put("address", address);
            resultJO.put("name", "DogAi#" + nftNumber);
            resultJO.put("symbol", "DogAi");

            return resultJO;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("nft resource create fail");
            return null;
        }
    }

    private JSONObject createJsonWithImageUrl(String address, JSONObject bodyParamJO, String nftNumber, String imageUrl) {
        JSONObject nftJson = new JSONObject();
        nftJson.put("name", "DogAi#" + nftNumber);
        nftJson.put("symbol", "DogAi");
        nftJson.put("description", "Based on the wallet address, a unique and one-of-a-kind puppy is mapped using AI algorithms");
        nftJson.put("seller_fee_basis_points", 500);
        nftJson.put("external_url", "https://dogai.space");
        nftJson.put("image", imageUrl);

        nftJson.put("attributes", createAttributes(bodyParamJO));

        JSONObject propertyJO = new JSONObject();
        propertyJO.put("creators", createCreators(address));
        propertyJO.put("category", "image");
        propertyJO.put("files", createFiles(nftNumber, imageUrl));
        propertyJO.put("collection", createCollectionJO());

        nftJson.put("properties", propertyJO);

        System.out.println(nftJson);
        return nftJson;
    }
}
