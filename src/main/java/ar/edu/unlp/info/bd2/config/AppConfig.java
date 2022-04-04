package ar.edu.unlp.info.bd2.config;

import ar.edu.unlp.info.bd2.repositories.VaxRepository;
import ar.edu.unlp.info.bd2.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public VaxService createService() {
        VaxRepository repository = this.createRepository();
        return new VaxServiceImpl(repository);
    }

    @Bean
    public VaxRepository createRepository() {
        return new VaxRepository();
    }
}
