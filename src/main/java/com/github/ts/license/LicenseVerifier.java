package com.github.ts.license;

import cn.hutool.core.date.DateUtil;
import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.prefs.Preferences;

/**
 * 证书认证者
 */
public class LicenseVerifier {

    private final static Logger log = LoggerFactory.getLogger(LicenseVerifier.class);

    private final LicenseVerifyProperties verifyProperties;

    private final LicenseManager licenseManager;

    public static AtomicBoolean ACTIVATE = new AtomicBoolean(false);

    private LicenseContent content;

    public LicenseVerifier(LicenseVerifyProperties verifyProperties) {
        this.verifyProperties = verifyProperties;
        licenseManager = new CustomLicenseManager(initLicenseParam(verifyProperties));
        ACTIVATE.set(install());
    }

    /**
     * 安装License证书
     */
    public synchronized boolean install() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //1. 安装证书
        try {
            content = licenseManager.install(new File(verifyProperties.getLicensePath()));
            log.info("证书安装成功，证书有效期：{} - {}", format.format(content.getNotBefore()), format.format(content.getNotAfter()));
            return true;
        } catch (Exception e) {
            log.error("证书安装失败！", e);
        }
        return false;
    }

    /**
     * 校验License证书
     *
     * @return boolean
     */
    public boolean verify() {
        //2. 校验证书
        try {
            licenseManager.verify();
            return true;
        } catch (Exception e) {
            log.error("证书校验失败！", e);
            return false;
        }
    }

    public long validity() {
        if (null == content) {
            return 365;
        }
        return DateUtil.betweenDay(content.getNotBefore(), content.getNotAfter(), false);
    }

    /**
     * 初始化证书校验参数
     *
     * @param param License校验类需要的参数
     */
    private LicenseParam initLicenseParam(LicenseVerifyProperties param) {
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerifier.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerifier.class
                , param.getPublicKeysStorePath()
                , param.getPublicAlias()
                , param.getStorePass()
                , null);

        return new DefaultLicenseParam(param.getSubject(), preferences, publicStoreParam, cipherParam);
    }

    public LicenseVerifyProperties getVerifyProperties() {
        return verifyProperties;
    }
}
