package com.exa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exa.entities.Order;
import com.exa.repositories.OrderRepository;
import com.exa.services.exceptions.ResourceNotFoundException;

@Service // @Component serviria para registrar a classe como componente do Spring, porem existem os especificos pra services, repositories e etc
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll(); // simplesmente repassa a chamada pra camada de repository
    }

    public Order findById(Long id) {
        // Optional é um container que pode ou nao conter um valor nao nulo
        // retorna um Optional, entao se usa o get pra pegar o objeto
        Optional<Order> obj = repository.findById(id);
        
        if (obj.isEmpty()) throw new ResourceNotFoundException(id);

        return obj.get();
    }
}
