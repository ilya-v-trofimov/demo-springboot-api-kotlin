package com.codeit.regoApi.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class PathConfigurer : WebMvcConfigurer {

    companion object {
        const val API_V1_PREFIX = "/api/v1"
    }

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer
            .addPathPrefix(API_V1_PREFIX) { true }
    }
}
