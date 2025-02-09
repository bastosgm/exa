package com.exa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exa.entities.Category;
import com.exa.repositories.CategoryRepository;

@Service // @Component serviria para registrar a classe como componente do Spring, porem existem os especificos pra services, repositories e etc
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll(); // simplesmente repassa a chamada pra camada de repository
    }

    public Category findById(Long id) {
        // Optional é um container que pode ou nao conter um valor nao nulo
        // retorna um Optional, entao se usa o get pra pegar o objeto
        Optional<Category> obj = repository.findById(id); 
        return obj.get();
    }
}
