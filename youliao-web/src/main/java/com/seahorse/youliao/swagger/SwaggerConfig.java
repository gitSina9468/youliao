package com.seahorse.youliao.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: swagger 配置
 * @author: Mr.Song
 * @create: 2019-12-15 20:47
 **/
@Configuration
@EnableSwagger2
@ComponentScan(basePackages= "com.seahorse.youliao")
public class SwaggerConfig {
    @Value("${swagger.basePackage:com.seahorse.youliao}")
    private String basePackage;

    @Bean
    public Docket api(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("youliao-project-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .build().globalOperationParameters(pars);
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("有料项目API文档")
                .description("HTTP对外开放接口")
                .version("1.0.0")
                .termsOfServiceUrl("http://localhost:8080")
                .license("LICENSE")
                .build();
    }
}
