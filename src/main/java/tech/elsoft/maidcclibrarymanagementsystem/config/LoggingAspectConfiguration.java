package tech.elsoft.maidcclibrarymanagementsystem.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import tech.elsoft.maidcclibrarymanagementsystem.logging.LoggingAspect;


@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile("local")
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
