package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //必须存在
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
//必须存在 扫描的API controller包
@ComponentScan(basePackages = {"com.example.demo.controller"})
public class SwaggerConfig {
    @Bean
    public Docket doctet(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("dk", "http://8.136.83.37", "939216851@qq.com");
        return new ApiInfoBuilder()
                .title("程序设计队伍管理系统API接口")
                .description("API接口")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}
