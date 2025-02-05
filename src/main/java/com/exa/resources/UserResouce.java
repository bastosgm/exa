package com.exa.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exa.entities.User;
import com.exa.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResouce {
    // adicionando uma dependencia (injecao de dependencia, que é um objeto UserService que vira automatico) pra UserServices
    // lembrando que pra isso funcionar, essa classe precisa estar registrada como componente do spring lá no UserService
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll(); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(list); 
    }   

    @GetMapping(value = "/{id}") // indica que essa request aceita um parametro na url
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = service.findById(id); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }

}
