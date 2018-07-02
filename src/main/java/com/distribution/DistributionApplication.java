package com.distribution;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * spring_boot 启动类
 *
 * @author huchunliang
 */
@EnableSwagger2Doc
@SpringBootApplication
public class DistributionApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DistributionApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DistributionApplication.class);
    }
}
