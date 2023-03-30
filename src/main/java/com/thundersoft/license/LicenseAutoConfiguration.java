package com.thundersoft.license;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@ConditionalOnProperty(
        name = {"license.enable"},
        havingValue = "true",
        matchIfMissing = true
)
@Configuration(
        proxyBeanMethods = false
)
@EnableConfigurationProperties({LicenseVerifyProperties.class})
public class LicenseAutoConfiguration {

    @Bean
    public LicenseVerifier licenseVerifier(LicenseVerifyProperties properties) {
        return new LicenseVerifier(properties);
    }

    @Bean
    public FilterRegistrationBean<Filter> myFilter(LicenseVerifier licenseVerifier) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LicenseFilter(licenseVerifier));
        registration.addUrlPatterns("/*");
        registration.setName("licenseFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    @ConditionalOnClass(
            name = {"javax.servlet.http.HttpServletRequest"}
    )
    public LicenseController licenseController(LicenseVerifier licenseVerifier) {
        return new LicenseController(licenseVerifier);
    }

}
