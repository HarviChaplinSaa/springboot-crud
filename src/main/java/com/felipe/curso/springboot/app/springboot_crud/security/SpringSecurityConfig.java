package com.felipe.curso.springboot.app.springboot_crud.security;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.felipe.curso.springboot.app.springboot_crud.security.filter.JwtAuthenticationFilter;
import com.felipe.curso.springboot.app.springboot_crud.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

    private AuthenticationConfiguration authenticationConfiguration;

    private static final String ROL_ADMIN = "ADMIN";

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration){
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    AuthenticationManager authenticationManager(){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http){
        return http.authorizeHttpRequests(authz -> authz
            .requestMatchers(HttpMethod.GET ,"/api/users").permitAll()
            .requestMatchers(HttpMethod.POST ,"/api/users/register").permitAll()
            .requestMatchers(HttpMethod.POST ,"/api/users").hasRole(ROL_ADMIN)
            .requestMatchers(HttpMethod.GET ,"/api/products", "/api/products/{id}").hasAnyRole(ROL_ADMIN, "USER")
            .requestMatchers(HttpMethod.POST ,"/api/products").hasRole(ROL_ADMIN)
            .requestMatchers(HttpMethod.PUT ,"/api/products/{id}").hasRole(ROL_ADMIN)
            .requestMatchers(HttpMethod.DELETE ,"/api/products/{id}").hasRole(ROL_ADMIN)
            .anyRequest()
            .authenticated())
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtValidationFilter(authenticationManager()))
            .csrf(config -> config.disable())
            .cors(cors -> cors.configurationSource(configurationSource()))
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean
    CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT", "OPTIONS")); //options hace falta para que el navegador pueda hacer una peticion preflight
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        configuration.setAllowCredentials(true); //sirve para acptar autenticacion diferente a jwt como cookies, http, certificado. Con jwt no es necesario normalmente

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }    

    @Bean 
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(new CorsFilter(configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
