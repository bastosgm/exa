package com.exa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exa.entities.OrderItem;

// JpaRepository pede tipo de entidade e tipo do id (PK)
// nao precisa de implementacao, o Spring Data JPA gerará
// aqui nao é necessario anotar com @Repository, pois JpaRepository ja é um componente do Spring
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
