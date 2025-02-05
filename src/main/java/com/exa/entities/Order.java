package com.exa.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
// isso especifica o nome da tabela no banco de dados. É preciso fazer isso pois Order é uma palavra reservada do SQL
@Table(name = "tb_order") 
public class Order implements Serializable {
    //passo a passo para criar uma entidade/resource
    // 1. criar os atributos basico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant moment;

    // 2. criar as assossiations
    // essa primeira representa a relacao de uma order com um cliente
    // já aproveitando o momento, a outra relacao de um cliente (User) com varias orders tem que ser feita na clase User
    // se está na classe ORder, a relacao é de muitos Order para um User
    @ManyToOne
    // aplicando a annotation JoinColumn pra especificar o nome da FK no db
    @JoinColumn(name = "client_id")
    private User client;

    // 3. criar os construtores
    public Order() {
    }

    public Order(Long id, Instant moment, User client) {
        this.id = id;
        this.moment = moment;
        this.client = client;
    }

    // 4. criar os getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    // 5. criar os hashcode e equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // 6. implementar o Serializable

    // 7. por estar trabalhando com spring, adicionar as annotations necessarias

    // 8. criar o relacionamento entre Order e User pro JPA criar as foreign keys no db (Estando aqui criamos a relacao de muitos Order para um User) e na classe User criamos a relacao de um User para muitos Order
}
