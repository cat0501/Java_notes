package com.baizhi.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                //扫描那个接口的包
                .apis(RequestHandlerSelectors.basePackage("com.baizhi.swagger.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("标题: springboot 整合 Swagger 使用")
                        .description("SpringBoot整合Swagger，详细信息......")
                        .version("1.1")
                        .contact(new Contact("xiaochen","http://www.baizhiedu.xin","xiaochen@163.com"))
                        .license("The Baizhi License")
                        .licenseUrl("http://www.baizhiedu.com")
                        .build());
    }
}