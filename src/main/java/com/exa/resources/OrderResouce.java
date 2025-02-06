package com.exa.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exa.entities.Order;
import com.exa.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResouce {
    // adicionando uma dependencia (injecao de dependencia, que é um objeto OrderService que vira automatico) pra OrderServices
    // lembrando que pra isso funcionar, essa classe precisa estar registrada como componente do spring lá no OrderService
    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> list = service.findAll(); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(list); 
    }   

    @GetMapping(value = "/{id}") // indica que essa request aceita um parametro na url
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order obj = service.findById(id); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }

}
