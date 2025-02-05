package com.exa.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.exa.entities.User;
import com.exa.repositories.UserRepository;

@Configuration
@Profile("test")
// vai ser necessario aplicar injecao de dependencia pra evitar forte
// acoplamento. Frameworks costumam ter um mecanismo pra isso nativo.
// CommandLineRunner é uma interface do Spring Boot que permite executar um
// metodo assim que a aplicacao é iniciada
public class TestConfig implements CommandLineRunner {
    @Autowired // injecao de dependencia automatica associando uma instancia de UserRepository
               // aqui no TestConfig
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        // Arrays.asList cria uma lista a partir dos objetos passados como argumento, pra nao precisar criar uma variavel so pra isso
        userRepository.saveAll(Arrays.asList(u1, u2));
    }

}
