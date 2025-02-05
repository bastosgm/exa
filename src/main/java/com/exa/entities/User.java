package com.exa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// 1. definiri Serializable quando quer q os objetos sejam transformados em cadeias de bytes pra que o objeto trafegue na rede, pra poder ser gravado em arquivos e etc.
// 2. aplicando aqui as anotações do JPA pra guia-lo em como fazer a conversao objeto-relacional
@Entity
@Table(name = "tb_user") // isso especifica o nome da tabela no banco de dados. É preciso fazer isso pois User é uma palavra reservada do SQL
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id // definindo a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // definindo a estratégia de geração automática de id
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;

    // essa assossiation precisa ser instanciada
    // definindo a relação de um User com várias Orders
    // o mappedBy é pra dizer que o atributo client da classe Order é o dono da relação
    @OneToMany(mappedBy = "client") 
    private List<Order> orders = new ArrayList<>();

    // como está usando framework, é obrigado declarar construtor vazio
    public User() {
    }

    public User(Long id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // collections tem apenas getters
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

