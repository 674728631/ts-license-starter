package com.thundersoft.license;


import java.io.Serializable;
import java.util.Date;

public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = 2832129012982731724L;
    /**
     * 证书生成路径
     */
    private String licensePath = "/config/license.lic";
    /**
     * 证书失效时间
     */
    private Date effectiveTime;
    /**
     * 证书失效时间
     */
    private Date expiryTime;
    /**
     * 额外的服务器硬件校验信息
     */
    private LicenseCheckModel licenseCheckModel;

    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public LicenseCheckModel getLicenseCheckModel() {
        return licenseCheckModel;
    }

    public void setLicenseCheckModel(LicenseCheckModel licenseCheckModel) {
        this.licenseCheckModel = licenseCheckModel;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}
