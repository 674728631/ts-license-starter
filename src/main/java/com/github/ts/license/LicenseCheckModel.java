package com.github.ts.license;


import java.util.List;

/**
 * 设备信息实体类
 */
public class LicenseCheckModel {

    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;
    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;
    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;
    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;
    /**
     * 有效期时间
     */
    private Long validity;
    /**
     * macAddress+cpuSerial 加密key
     */
    private String key;

    public List<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(List<String> ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<String> getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(List<String> macAddress) {
        this.macAddress = macAddress;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getMainBoardSerial() {
        return mainBoardSerial;
    }

    public void setMainBoardSerial(String mainBoardSerial) {
        this.mainBoardSerial = mainBoardSerial;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
