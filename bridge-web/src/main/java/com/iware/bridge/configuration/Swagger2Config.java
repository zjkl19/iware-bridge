package com.iware.bridge.configuration;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.enable}")
    private boolean enableSwagger;
    @Value("${info.version}")
    private String version;

    private static List<Parameter> operationParameters = Lists.newArrayList();

    static{
    	ParameterBuilder tokenPar = new ParameterBuilder();
    	tokenPar.name("X-AUTH-TOKEN").description("身份令牌")
    		.modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        ParameterBuilder urlPar = new ParameterBuilder();
        urlPar.name("X-ROUTER-URL").description("请求路由")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	operationParameters.add(tokenPar.build());
        operationParameters.add(urlPar.build());
    }

    @Bean
    public Docket createHomeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("首页")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.home"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createOnlineApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("在线监测")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.online"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createDailyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("日常巡查/维修养护")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.inspection"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createVideoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("视频观察")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.video"))
                .paths(PathSelectors.any())
                .build();
    }


    @Bean
    public Docket createAssessApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("检测评估")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.evaluation"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createInfoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("信息管理")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.info"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createAppApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("App端")
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .globalOperationParameters(operationParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iware.bridge.app"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("互联网接口文档")
                .description("互联网 Swagger API 服务")
                .termsOfServiceUrl("http://swagger.io/")
                .contact(new Contact("ZR", "127.0.0.1", "中锐网络科技"))
                .version(version)
                .license("中锐")
                .licenseUrl("www.zr.com")
                .build();
    }

}
