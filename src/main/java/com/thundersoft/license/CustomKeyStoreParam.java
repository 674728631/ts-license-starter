package com.thundersoft.license;


import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.*;

public class CustomKeyStoreParam extends AbstractKeyStoreParam {

    private final String storePath;
    private final String alias;
    private final String storePwd;
    private final String keyPwd;

    /**
     * @param clazz    要操作的对象
     * @param resource 公钥/私钥 在磁盘上的存储路径
     * @param alias    公钥/私钥 密钥别称
     * @param storePwd 访问秘钥库的密码
     * @param keyPwd   访问公钥/私钥库的密码
     */
    public CustomKeyStoreParam(Class clazz, String resource, String alias, String storePwd, String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * 复写de.schlichtherle.license.AbstractKeyStoreParam的getStream()方法<br/>
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     *
     * @return java.io.InputStream
     */
    @Override
    public InputStream getStream() throws IOException {
        final InputStream in = this.getClass().getClassLoader().getResourceAsStream(storePath);
        if (null == in)
            throw new FileNotFoundException(storePath);
        return in;
    }
}
