import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;

import java.util.Arrays;
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges -> exchanges
   .pathMatchers(
                "/",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/swagger-ui/index.html",
                "/swagger-initializer.js",
                "/swagger-ui.css",
                "/swagger-ui-bundle.js",
                "/swagger-ui-standalone-preset.js",
                "/favicon-*",
                "/api-docs/**"
            ).permitAll()
            .pathMatchers("/projetcompain/**", "/facturation/**", "/banqueservice/**").authenticated()
            .anyExchange().authenticated()
        )
            .oauth2Login(Customizer.withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwkSetUri("https://esmm.systeo.tn/realms/projectPFE/protocol/openid-connect/certs")
                    .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(jwtAuthConverter))
                )
            )

 .headers(headers -> headers
            .contentSecurityPolicy(csp -> csp
                .policyDirectives("default-src 'self' https://m1.systeo.tn; script-src 'self' 'unsafe-inline' https://m1.systeo.tn; style-src 'self' 'unsafe-inline' https://m1.systeo.tn")
            )
        )

            .build();
    }

    @Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://m1.systeo.tn", "https://esmm.systeo.tn"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
    configuration.setExposedHeaders(Arrays.asList("X-Get-Header", "Authorization", "Content-Disposition"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
   source.registerCorsConfiguration("/swagger-ui.html", configuration);
    source.registerCorsConfiguration("/swagger-ui/**", configuration);
    source.registerCorsConfiguration("/v3/api-docs", configuration);
    source.registerCorsConfiguration("/swagger-resources/**", configuration);
    source.registerCorsConfiguration("/webjars/**", configuration);
        return source;
    }
}
