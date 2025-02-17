package com.exa.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exa.entities.Product;
import com.exa.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResouce {
    // adicionando uma dependencia (injecao de dependencia, que é um objeto ProductService que vira automatico) pra ProductServices
    // lembrando que pra isso funcionar, essa classe precisa estar registrada como componente do spring lá no ProductService
    private final ProductService service;

    public ProductResouce(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = service.findAll(); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(list); 
    }   

    @GetMapping(value = "/{id}") // indica que essa request aceita um parametro na url
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product obj = service.findById(id); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }

}
