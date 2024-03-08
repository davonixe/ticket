package com.ennov.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.context.annotation.Bean;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//import com.ennov.tickets.model.audit.AuditorAwareImpl;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TicketsApplication {

	// Pour l'ajout des variables de monitoring des donnees.
	/*@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }*/

	public static void main(String[] args) {
		SpringApplication.run(TicketsApplication.class, args);
	}

}
