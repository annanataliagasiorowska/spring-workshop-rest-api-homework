package pl.dgadecki.springworkshoprestapi.configuration.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] API_DOCUMENTATION_PATHS = new String[]{
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html#"
    };


    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user_1")
                .password("{noop}user_1")
                .authorities("ACCESS_TO_ARTICLE_API_READ")
                .build(),
                User.withUsername("user_2")
                .password("{noop}user_2")
                .authorities("ACCESS_TO_ARTICLE_API_WRITE")
                .build(),
                User.withUsername("user_3")
                .password("{noop}user_3")
                .authorities("ACCESS_TO_CUSTOMER_API_READ")
                .build(),
                User.withUsername("admin")
                .password("{noop}admin")
                .authorities("ACCESS_TO_ARTICLE_API_READ",
                        "ACCESS_TO_ARTICLE_API_WRITE",
                        "ACCESS_TO_CUSTOMER_API_READ",
                        "ACCESS_TO_CUSTOMER_API_WRITE"
                )
                .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // never disable csrf protection while living sessionManagement enabled
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(API_DOCUMENTATION_PATHS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAuthority("ACCESS_TO_CUSTOMER_API_WRITE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/articles/**").hasAuthority("ACCESS_TO_ARTICLE_API_WRITE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**").hasAuthority("ACCESS_TO_CUSTOMER_API_WRITE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/articles/**").hasAuthority("ACCESS_TO_ARTICLE_API_WRITE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAuthority("ACCESS_TO_CUSTOMER_API_WRITE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/articles/**").hasAuthority("ACCESS_TO_ARTICLE_API_WRITE")
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((httpServletRequest, httpServletResponse, authenticationException) -> {
                            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            httpServletResponse.setContentType("application/json");
                            httpServletResponse.getWriter().write("{\"message\": \"Unauthorized\"}");
                        })
                        .accessDeniedHandler((httpServletRequest, httpServletResponse, accessDeniedException) -> {
                            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            httpServletResponse.setContentType("application/json");
                            httpServletResponse.getWriter().write("{\"message\": \"Forbidden\"}");
                        })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
