package br.com.fiap.projeto_pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@ComponentScan
@EntityScan
@EnableJpaRepositories
@SpringBootApplication
public class ProjetoPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPetApplication.class, args);
	}

}
