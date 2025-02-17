package com.exa.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exa.entities.Category;
import com.exa.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResouce {
    // adicionando uma dependencia (injecao de dependencia, que é um objeto CategoryService que vira automatico) pra CategoryServices
    // lembrando que pra isso funcionar, essa classe precisa estar registrada como componente do spring lá no CategoryService
    private final CategoryService service;

    public CategoryResouce(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = service.findAll(); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(list); 
    }   

    @GetMapping(value = "/{id}") // indica que essa request aceita um parametro na url
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category obj = service.findById(id); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }

}
