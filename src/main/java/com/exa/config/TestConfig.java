package com.exa.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.exa.entities.Category;
import com.exa.entities.Order;
import com.exa.entities.Product;
import com.exa.entities.User;
import com.exa.entities.enums.OrderStatus;
import com.exa.repositories.CategoryRepository;
import com.exa.repositories.OrderRepository;
import com.exa.repositories.ProductRepository;
import com.exa.repositories.UserRepository;

@Configuration
@Profile("test")
// vai ser necessario aplicar injecao de dependencia pra evitar forte
// acoplamento. Frameworks costumam ter um mecanismo pra isso nativo.
// CommandLineRunner é uma interface do Spring Boot que permite executar um
// metodo assim que a aplicacao é iniciada
public class TestConfig implements CommandLineRunner {
    // injecao de dependencia automatica associando uma instancia de UserReposito
    // aqui no TestConfig
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // pode vir primeiro aqui pq Category é uma classe independente das outras, só
        // confirmar no diagrama UML
        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");

        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
        Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
        Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
        Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
        Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        // salvar os produtos
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        // depois de salvar as categorias e produtos, é preciso associar as categorias
        p1.getCategories().add(cat2); // associacao entre objetos
        p2.getCategories().add(cat1);
        p2.getCategories().add(cat3);
        p3.getCategories().add(cat3);
        p4.getCategories().add(cat3);
        p5.getCategories().add(cat2);

        // agora é salvar os produtos novamente com essas associacoes
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        // cada Order aqui está sendo uma associacao entre objetos, como da pra ver no
        // ultimo parametro
        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);

        // Arrays.asList cria uma lista a partir dos objetos passados como argumento,
        // pra nao precisar criar uma variavel so pra isso
        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
    }

}
