package com.exa.entities;

import java.io.Serializable;

import com.exa.entities.pk.OrderItemPk;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {
    // atributo idetificador correspondente a PK. Define um objeto composto como PK
    // lembrando de criar já instanciando o objeto
    @EmbeddedId
    private OrderItemPk id = new OrderItemPk();
    
    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Integer quantity, Double price) {
        super();
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    // gerando os getters e setters de Order e Product, mesmo que não existam essas propriedades diretamente aqui, mas indiretamente por " OrderItemPk id"
    // pro externo, nao existirá um getId dessa classe, mas sim um getOrder e getProduct
    @JsonIgnore // usado aqui pra evitar loop infinito na serializacao de Order pois esse cara é quem chama Order associado a esse OrderItem onde começa o loop
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // implementando método subTotal
    // na plataforma EE, o que vale é o get, então o que for retornado no get é o que será serializado. Pra aparecer o resultado no json, é preciso criar um get
    public double getSubTotal() {
        return price * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        return id != null ? id.equals(orderItem.id) : orderItem.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
