package com.github.ts.license;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "license")
public class LicenseVerifyProperties {

    /**
     * 证书subject
     */
    private String subject = "ThunderSoft";
    /**
     * 访问公钥库的密码
     */
    private String storePass = "Thundersoft@88";
    /**
     * 证书生成路径
     */
    private String licensePath = "/config/license.lic";
    /**
     * 公钥别称
     */
    private String publicAlias = "publicCert";
    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath = "license/publicKeys.keystore";
    /**
     * 密钥别称
     */
    private String privateAlias = "privateCert";
    /**
     * 密钥库存储路径
     */
    private String privateKeysStorePath = "license/privateKeys.keystore";
    /**
     * 密钥密码
     */
    private String keyPass = "Thundersoft@88";

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPublicAlias() {
        return publicAlias;
    }

    public void setPublicAlias(String publicAlias) {
        this.publicAlias = publicAlias;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public String getPublicKeysStorePath() {
        return publicKeysStorePath;
    }

    public void setPublicKeysStorePath(String publicKeysStorePath) {
        this.publicKeysStorePath = publicKeysStorePath;
    }

    public String getPrivateKeysStorePath() {
        return privateKeysStorePath;
    }

    public void setPrivateKeysStorePath(String privateKeysStorePath) {
        this.privateKeysStorePath = privateKeysStorePath;
    }

    public String getPrivateAlias() {
        return privateAlias;
    }

    public void setPrivateAlias(String privateAlias) {
        this.privateAlias = privateAlias;
    }

    public String getKeyPass() {
        return keyPass;
    }

    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    }
}
