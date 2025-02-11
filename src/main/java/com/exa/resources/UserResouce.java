package com.exa.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PostMapping
    // Pra dizer que User obj vai ser um objeto json que vai ser enviado no corpo da requisicao, usa-se o @RequestBody
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = service.insert(obj); // aqui se usa a dependencia que foi injetada
        // criando o objeto do tipo URI pra retornar o endereco do novo objeto criado e servir de parametro para created()
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(obj.getId())
            .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); // aqui se usa a dependencia que foi injetada

        // noContent() retorna uma resposta vazia com o codigo 204
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
        obj = service.update(id, obj); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }
    // @PutMapping(value = "/{id}")
    // public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
    //     obj = service.update(id, obj); // aqui se usa a dependencia que foi injetada

    //     return ResponseEntity.ok().body(obj);
    // }
}
