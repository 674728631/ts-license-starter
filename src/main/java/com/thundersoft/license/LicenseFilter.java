package com.thundersoft.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * license拦截
 */
@Order(-1)
public class LicenseFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(LicenseFilter.class);

    private final LicenseVerifier licenseVerifier;

    public LicenseFilter(LicenseVerifier licenseVerifier) {
        this.licenseVerifier = licenseVerifier;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (log.isDebugEnabled()) {
            log.debug("拦截请求，验证License证书====>");
        }
        if (httpRequest.getMethod().equals("GET")
                && !httpRequest.getRequestURI().equals("/license/getServerInfo")
                && !"/license/activate".equals(httpRequest.getRequestURI())) {
            //校验证书是否有效
            if (!licenseVerifier.verify()) {
                response.setContentType("application/json;charset=utf-8");
                httpResponse.setStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value());
                response.getOutputStream()
                        .write("您的证书无效，请核查服务器是否取得授权或重新申请证书！".getBytes(StandardCharsets.UTF_8));
                response.flushBuffer();
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
