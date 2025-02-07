package com.exa.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.exa.entities.Order;
import com.exa.entities.User;
import com.exa.entities.enums.OrderStatus;
import com.exa.repositories.OrderRepository;
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

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        // cada Order aqui está sendo uma associacao entre objetos, como da pra ver no ultimo parametro
        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

        // Arrays.asList cria uma lista a partir dos objetos passados como argumento,
        // pra nao precisar criar uma variavel so pra isso
        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
    }

}
