package com.exa.entities;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable {
    // 1. criar os atributos basicos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant moment;

    // 2. criar as associacoes (1 Order tem 1 Payment e fazer o reverso na outra classe, no caso, ORder)
    // Lembrando que segundo o diagrama, Payment é dependente de Order, pois uma Order pode entrar no db sem um Payment relacionado, o que torna Order independente de Payment
    // A assossiacao é de 1 para 1, e é feita na classe dependente, no caso, Payment
    @JsonIgnore
    @OneToOne
    @MapsId // pra garantir que o id de Payment seja o mesmo do Order
    private Order order;

    // 3. criar os construtores
    public Payment() {
    }

    public Payment(Long id, Instant moment, Order order) {
        this.id = id;
        this.moment = moment;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // 5. criar os metodos hashCode e equals
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
        Payment other = (Payment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // 6. Aplicar o serializable na classe
    // 7. Aplicar os annotations necessarios pra fazer o mapeamento objeto relacional
}
