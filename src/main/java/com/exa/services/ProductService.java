package com.exa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exa.entities.Product;
import com.exa.repositories.ProductRepository;
import com.exa.services.exceptions.ResourceNotFoundException;

@Service // @Component serviria para registrar a classe como componente do Spring, porem existem os especificos pra services, repositories e etc
public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll(); // simplesmente repassa a chamada pra camada de repository
    }

    public Product findById(Long id) {
        // Optional é um container que pode ou nao conter um valor nao nulo
        // retorna um Optional, entao se usa o get pra pegar o objeto
        Optional<Product> obj = repository.findById(id); 

        if (obj.isEmpty()) throw new ResourceNotFoundException(id);

        return obj.get();
    }
}
