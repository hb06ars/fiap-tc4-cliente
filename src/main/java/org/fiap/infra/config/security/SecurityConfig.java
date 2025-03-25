package org.fiap.infra.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/cliente") // Apenas acessa o endpoint /cliente
                        .hasIpAddress("127.0.0.1") // Limita acesso ao IP local
                        .anyRequest().authenticated()) // Requer autenticação para outras rotas
                .csrf(csrf -> csrf.disable()); // Desativa CSRF, se necessário

        return http.build();
    }

}
