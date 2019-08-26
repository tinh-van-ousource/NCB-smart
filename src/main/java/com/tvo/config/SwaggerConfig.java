/**
 * 
 */
package com.tvo.config;

import com.tvo.common.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Ace
 *
 */
@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket petApi() {
		// Adding Header
//		ParameterBuilder aParameterBuilder = new ParameterBuilder();
//		aParameterBuilder.name(AppConstant.HEADER_STRING).modelRef(new ModelRef("string")).parameterType("header")
//				.required(false).build();
//		List<Parameter> aParameters = new ArrayList<Parameter>();
//		aParameters.add(aParameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.tvo.controller"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false)
				.securitySchemes(newArrayList(apiKey()))
				.securityContexts(newArrayList(securityContext()))
				.enableUrlTemplating(false);
//				.globalOperationParameters(aParameters);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Api Documentation").description("Api Documentation")
				.contact(new Contact("Sonace", "facebook.com", "sonace264@gmail.com.com")).version("1.0").build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AppConstant.HEADER_STRING, "header"); // 用于Swagger UI测试时添加Bearer Token
	}

	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.any())
	        .build();
	  }

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(new SecurityReference("JWT", authorizationScopes));
	}
}
