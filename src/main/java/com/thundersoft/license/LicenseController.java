package com.thundersoft.license;


import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Controller
public class LicenseController {

    private final static Logger log = LoggerFactory.getLogger(LicenseController.class);

    private final LicenseVerifier licenseVerifier;

    private final AES aes;

    // 偏移量
    byte[] iv = "6htnlellmh2qslgt".getBytes(StandardCharsets.UTF_8);
    // 密钥
    byte[] key = "6htnlellmh2qslgt".getBytes(StandardCharsets.UTF_8);

    public LicenseController(LicenseVerifier licenseVerifier) {
        this.licenseVerifier = licenseVerifier;
        aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
    }

    /**
     * 获取服务器cpu、ip、mac等信息，并对其进行加密返回
     */
    @RequestMapping(
            value = {"/license/getServerInfo"},
            method = {RequestMethod.GET},
            produces = {"application/json", "application/hal+json"}
    )
    @ResponseBody
    public ResponseEntity<Object> getDocumentation() {
        LicenseCheckModel serverInfos = new CustomLicenseManager().getServerInfos();
        // key
        String key = aes.encryptBase64(JsonSerializer.serialize(serverInfos));
        serverInfos.setKey(key);
        // validity
        long validity = licenseVerifier.validity();
        serverInfos.setValidity(validity);
        return new ResponseEntity<>(serverInfos, HttpStatus.OK);
    }

    /**
     * 根据license 加密key 生成本地文件
     */
    @RequestMapping(
            value = {"/license/activate"},
            method = {RequestMethod.GET},
            produces = {"application/json", "application/hal+json"}
    )
    @ResponseBody
    public ResponseEntity<Object> activate(String license) {
        try {
            // 解密
            String parameter = aes.decryptStr(license);
            // 转成json
            LicenseCreatorParam param = JsonSerializer.deserialize(parameter, LicenseCreatorParam.class);
            LicenseCreator licenseCreator = new LicenseCreator(param, licenseVerifier.getVerifyProperties());

            if (licenseCreator.generateLicense()) {
                // 重新加载license
                licenseVerifier.install();
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return new ResponseEntity<>("license激活码错误", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
