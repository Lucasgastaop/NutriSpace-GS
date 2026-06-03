package com.nutrispace.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nutrispace.model.Astronauta;
import com.nutrispace.repository.AstronautaRepository;

@Configuration
@Profile("dev")
public class DevDataLoader {

	@Bean
	CommandLineRunner seedDevData(AstronautaRepository astronautaRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (astronautaRepository.existsByEmail("admin@nutrispace.com")) {
				return;
			}
			Astronauta admin = new Astronauta();
			admin.setNome("Admin Teste");
			admin.setCargo("Comandante");
			admin.setEmail("admin@nutrispace.com");
			admin.setSenha(passwordEncoder.encode("123456"));
			astronautaRepository.save(admin);
		};
	}
}
