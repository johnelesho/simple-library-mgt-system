package tech.elsoft.maidcclibrarymanagementsystem.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsFilterConfig implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(("*")));
        corsConfiguration.setAllowedMethods(List.of("POST", "PUT", "GET", "OPTIONS", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("X-API-RESPONSE-TIME", "x-requested-with","x-auth-username","x-auth-password","x-auth-token","origin","accept","content-type","access-control-request-method","access-control-request-headers","authorization"));

        return corsConfiguration;
    }
}
