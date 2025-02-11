package com.exa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exa.entities.User;
import com.exa.repositories.UserRepository;

@Service // @Component serviria para registrar a classe como componente do Spring, porem existem os especificos pra services, repositories e etc
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll(); // simplesmente repassa a chamada pra camada de repository
    }

    public User findById(Long id) {
        // Optional é um container que pode ou nao conter um valor nao nulo
        // retorna um Optional, entao se usa o get pra pegar o objeto
        Optional<User> obj = repository.findById(id); 
        return obj.get();
    }

    // metodo pra inserir um novo usuario no db
    public User insert(User obj) {
        return repository.save(obj);
    }
}
