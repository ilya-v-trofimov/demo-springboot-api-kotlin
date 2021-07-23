package com.codeit.regoApi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

@EnableWebSecurity
class SecurityConfig(@Value("\${api.key}") val apiKey: String) : WebSecurityConfigurerAdapter() {

    private val AUTH_WHITELIST = arrayOf(
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/h2-console/**",
    )

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {
        val filter = ApiKeyVerificationFilter()
        filter.setAuthenticationManager { authentication ->
            val principal = authentication.principal as String
            if (apiKey != principal) {
                throw BadCredentialsException("Invalid API key")
            } else {
                authentication.isAuthenticated = true
                authentication
            }
        }
        http
            .csrf().disable().cors().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilter(filter)
            .authorizeRequests()
            .antMatchers(*AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(*AUTH_WHITELIST)
    }
}

class ApiKeyVerificationFilter : AbstractPreAuthenticatedProcessingFilter() {
    companion object {
        const val API_KEY_HEADER = "X-API-Key"
    }

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any {
        return request?.getHeader(API_KEY_HEADER) ?: "defaultApiKey"
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest?): Any {
        return "n/a"
    }
}
