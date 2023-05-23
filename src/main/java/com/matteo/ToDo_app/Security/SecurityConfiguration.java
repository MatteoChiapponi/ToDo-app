package com.matteo.ToDo_app.Security;

import com.matteo.ToDo_app.Services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    final UserServiceImpl userService;
    final PasswordEncoder passwordEncoder;
    final JwtRequestFilter jwtRequestFilter;
    final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(UserServiceImpl userService, PasswordEncoder passwordEncoder, JwtRequestFilter jwtRequestFilter, AuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .cors().configurationSource(corsConfigurationSource())
                    .and()
                    .csrf()
                    .disable()
                    .authorizeHttpRequests()
                    .requestMatchers(
                            "/v1/auth/**",
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui/**",
                            "/webjars/**",
                            "/swagger-ui.html")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                /*    .authorizeHttpRequests(auth -> {
                        auth.requestMatchers(
                                "/v1/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/",
                                        "/swagger-resources",
                                        "/swagger-resources/",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/",
                                        "/webjars/",
                                        "/swagger-ui.html")
                                .permitAll();
                        auth.requestMatchers("/v1/tasks/**").hasAuthority("ROLE_USER");
                        //auth.requestMatchers("/turnos/**", "/index.html").hasAnyRole("ROL_ADMIN", "ROL_USER");
                        auth.anyRequest().authenticated();
                    })

                 */
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern(("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
