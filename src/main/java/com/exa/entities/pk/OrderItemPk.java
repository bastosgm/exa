package com.exa.entities.pk;

import com.exa.entities.Order;
import com.exa.entities.Product;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

// Essa classe será uma PK composta, que se refere a uma PK que é formada por dois ou mais atributos ao em vez de apenas um atributo como id. Isso se da pois como cada pedido tem varios produtos, e vice versa, nao faz sentido um unico id para cada um, entao é preciso uma PK composta.
@Embeddable // anotacao para dizer que essa classe pode ser incorporada em outra classe Entidade, sem ser uma entidade separada no db, apenas como um identificador
public class OrderItemPk implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItemPk that = (OrderItemPk) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
