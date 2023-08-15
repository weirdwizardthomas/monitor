package com.example.monitor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private final AccessTokenFilter accessTokenFilter;

    @Autowired
    public FilterConfiguration(AccessTokenFilter accessTokenFilter) {
        this.accessTokenFilter = accessTokenFilter;
    }

    @Bean
    public FilterRegistrationBean<AccessTokenFilter> filterRegistrationBean() {
        FilterRegistrationBean<AccessTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(accessTokenFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

