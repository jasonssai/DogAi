package com.dogai.service;

import com.dogai.entity.BodyPart;
import com.dogai.entity.DogBodyHexParam;
import com.dogai.entity.DogBodyShapeParam;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.math.BigInteger;

@Service
public class LogoService {

    @Value("${shape.mount.head}")
    private int shapeMountHead;

    @Value("${shape.mount.ears}")
    private int shapeMountEars;

    @Value("${shape.mount.eyes}")
    private int shapeMountEyes;

    @Value("${shape.mount.nose-mouth}")
    private int shapeMountNoseMouth;

    @Value("${shape.mount.corner}")
    private int shapeMountCorner;

    @Value("${shape.mount.color}")
    private int shapeMountColor;

    public DogBodyShapeParam calcDogBodyParams(String address) {
        DogBodyHexParam dogBodyHexParam = createDogBodyParam(address);
        return calcShape(dogBodyHexParam);
    }

    private DogBodyShapeParam calcShape(DogBodyHexParam dogBodyHexParam) {

        int offset = shapeMountHead / 3;

        DogBodyShapeParam dogBodyShapeParam = new DogBodyShapeParam();

        BodyPart bodyPartHead = getShape(dogBodyHexParam.getHeadHex(), shapeMountHead);

        int headNum = bodyPartHead.getNum();

        BodyPart bodyPartEyes = getShape(dogBodyHexParam.getEarsHex(), shapeMountEyes);
        BodyPart bodyPartNoseAndMouth = getShape(dogBodyHexParam.getNoseAndMouthHex(), shapeMountNoseMouth);
        BodyPart bodyPartEars = getShape(dogBodyHexParam.getEarsHex(), shapeMountEars);

        if (headNum > 0 && headNum <= offset) {
        } else if (headNum > offset && headNum <= offset * 2) {
            bodyPartEyes.setNum(bodyPartEyes.getNum() + shapeMountEyes);
            bodyPartNoseAndMouth.setNum(bodyPartNoseAndMouth.getNum() + shapeMountNoseMouth);
        } else if (headNum > offset * 2 && headNum <= offset * 3) {
            bodyPartEyes.setNum(bodyPartEyes.getNum() + shapeMountEyes * 2);
            bodyPartNoseAndMouth.setNum(bodyPartNoseAndMouth.getNum() + shapeMountNoseMouth * 2);
        } else {
//            System.out.println("dogBodyHexParam not correct：" + dogBodyHexParam.getHeadHex());
            throw new RuntimeException("dogBodyHexParam not correct：" + dogBodyHexParam.getHeadHex());
        }

        dogBodyShapeParam.setHeadShape(bodyPartHead);
        dogBodyShapeParam.setEyesShape(bodyPartEyes);
        dogBodyShapeParam.setNoseAndMouthShape(bodyPartNoseAndMouth);

        dogBodyShapeParam.setEarsShape(bodyPartEars);
        dogBodyShapeParam.setCorner(getShape(dogBodyHexParam.getCornerHex(), shapeMountCorner).getNum());

//        System.out.println("dogBodyShapeParam：" + JSON.toJSONString(dogBodyShapeParam));
        return dogBodyShapeParam;
    }

    private BodyPart getShape(String hex, int shapeMount) {
        BigInteger paramInt = new BigInteger(hex, 16);
        int shapeNum = paramInt.divide(BigInteger.valueOf(shapeMount)).mod(BigInteger.valueOf(shapeMount)).intValue() + 1;

        String rHex = hex.substring(0, 2);
        int rInt = Integer.parseInt(rHex, 16);
        String gHex = hex.substring(2, 4);
        int gInt = Integer.parseInt(gHex, 16);
        String bHex = hex.substring(4, 6);
        int bInt = Integer.parseInt(bHex, 16);

        int colorNum = (rInt + gInt + bInt) % shapeMountColor;

        return new BodyPart(shapeNum, "c" + (colorNum + 1));
    }

    private DogBodyHexParam createDogBodyParam(String address) {
        DogBodyHexParam dogBodyHexParam = new DogBodyHexParam();

        String addressHex = HexUtils.toHexString(Base64Utils.decodeFromString(address));
//        System.out.println("addressHex.length()：" + addressHex.length());

        dogBodyHexParam.setAddressHex(addressHex);
        dogBodyHexParam.setEarsHex(addressHex.substring(0, 12));
        dogBodyHexParam.setEyesHex(addressHex.substring(12, 24));
        dogBodyHexParam.setHeadHex(addressHex.substring(24, 36));
        dogBodyHexParam.setNoseAndMouthHex(addressHex.substring(36, 48));
        dogBodyHexParam.setCornerHex(addressHex.substring(48));

//        System.out.println("dogBodyHexParam：" + dogBodyHexParam);

        return dogBodyHexParam;
    }
}
