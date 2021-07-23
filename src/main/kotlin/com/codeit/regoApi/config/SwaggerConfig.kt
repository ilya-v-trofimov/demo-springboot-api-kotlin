package com.codeit.regoApi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig : WebMvcConfigurationSupport() {
    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.codeit.regoApi"))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
        .securitySchemes(listOf(ApiKey("apiKey", ApiKeyVerificationFilter.API_KEY_HEADER, "header")))
        .securityContexts(
            listOf(
                SecurityContext.builder()
                    .securityReferences(
                        listOf(
                            SecurityReference.builder()
                                .reference("apiKey")
                                .scopes(arrayOf(AuthorizationScope("global", "access")))
                                .build()
                        )
                    )
                    .build()
            )
        )

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}
