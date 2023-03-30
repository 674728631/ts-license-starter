package com.github.ts.license;

import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * 证书创建者
 */
public class LicenseCreator {


    private final static Logger log = LoggerFactory.getLogger(LicenseCreator.class);

    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER =
            new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");

    private final LicenseCreatorParam param;
    private final LicenseVerifyProperties licenseProperties;
    private final LicenseManager creatorLicenseManager;


    public LicenseCreator(LicenseCreatorParam param, LicenseVerifyProperties licenseProperties) {
        this.param = param;
        this.licenseProperties = licenseProperties;
        creatorLicenseManager = new CustomLicenseManager(initLicenseParam());
    }

    /**
     * 生成License证书
     *
     * @return boolean
     */
    public boolean generateLicense() {
        try {
            LicenseContent licenseContent = initLicenseContent();
            creatorLicenseManager.store(licenseContent, new File(licenseProperties.getLicensePath()));
            return true;
        } catch (Exception e) {
            log.error("证书生成失败：", e);
            return false;
        }
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(licenseProperties.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                , licenseProperties.getPrivateKeysStorePath()
                , licenseProperties.getPrivateAlias()
                , licenseProperties.getStorePass()
                , licenseProperties.getKeyPass());

        return new DefaultLicenseParam(licenseProperties.getSubject(), preferences, privateStoreParam, cipherParam);
    }

    /**
     * 设置证书生成正文信息
     */
    private LicenseContent initLicenseContent() {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(licenseProperties.getSubject());
        licenseContent.setIssued(new Date());
        licenseContent.setNotBefore(param.getEffectiveTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType("user");
        licenseContent.setConsumerAmount(1);
        licenseContent.setInfo("ts自动生成");

        //扩展校验服务器硬件信息
        licenseContent.setExtra(JsonSerializer.serialize(param.getLicenseCheckModel()));

        return licenseContent;
    }
}
