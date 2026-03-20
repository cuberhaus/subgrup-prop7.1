package web.config;

import domini.controladors.ControladorDomini;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ControladorDomini controladorDomini() throws Exception {
        return ControladorDomini.obtenirInstancia();
    }
}
