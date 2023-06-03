package com.gas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                //配置网站的基本信息
                .apiInfo(new ApiInfoBuilder()
                        //网站标题
                        .title("燃气卫士系统接口文档")
                        //标题后面的版本号
                        .version("v1.0")
                        .description("燃气卫士系统接口文档")
                        .build())
                .select()
                //指定接口的位置
                .apis(RequestHandlerSelectors.basePackage("com.gas.controller"))
                .build();
    }

}
