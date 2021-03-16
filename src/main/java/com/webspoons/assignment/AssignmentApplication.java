package com.webspoons.assignment;

import com.webspoons.assignment.configuration.WebFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<WebFilter> filterRegistrationBean(){
        FilterRegistrationBean<WebFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        WebFilter myFilter = new WebFilter();
        filterRegistrationBean.setFilter(myFilter);
        return filterRegistrationBean;
    }
}
