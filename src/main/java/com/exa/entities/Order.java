package com.exa.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.exa.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
// import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    // annotation do jackson pra formatar a data no formato ISO 8601
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment;

    // aqui poderia ser do tipo OrderStatus, mas será Integer pra dizer q o valor que será gravado no db é um inteiro, porém isso é só internamente nessa classe Order, e fora dela tudo deve ser tratado como OrderStatus
    private Integer orderStatus;

    // 2. criar as assossiations
    // essa primeira representa a relacao de uma order com um cliente
    // já aproveitando o momento, a outra relacao de um cliente (User) com varias orders tem que ser feita na clase User
    // se está na classe ORder, a relacao é de muitos Order para um User
    @ManyToOne
    // aplicando a annotation JoinColumn pra especificar o nome da FK no db
    @JoinColumn(name = "client_id")
    // @JsonIgnore - Evita loop infinito pela relacao de mao dupla. Se é deixado aqui, permite que Order traga User relacionado, mas User não traga Orders relacionadas
    private User client;

    @OneToMany(mappedBy = "id.order") // aqui é o mapeamento do atributo order da classe OrderItemPk. id.order é por estar usando um set de OrderItem. com seu getter abaixo, Order reconhece os OrderItems relacionados a ele
    private Set<OrderItem> items = new HashSet<>();

    // aqui é a associacao de um Order com um Payment, que requer seus getters e setters tambem
    // necessario fazer a assossiacao com payment aqui tambem
    // nesse caso de 1 para 1, é requerido mesmo id para pedido e pagamento, o que torna obrigatorio o uso de cascade = CascadeType.ALL
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL) // nome do atributo na classe Payment que faz referencia a Order
    private Payment payment;

    // 3. criar os construtores
    public Order() {
    }

    public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
        this.id = id;
        this.moment = moment;
        // aqui como o orderStatus que chega é do tipo OrderStatus, ao inves da assimilacao direta, precisa repassar para a funcao SetOrderStatus pra fazer orderStatus receber um valor Integer
        setOrderStatus(orderStatus); 
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

    // como order status aqui dentro é um inteiro, mas fora é um OrderStatus, tem que fazer a conversao usando o metodo valueOf da classe Enum OrderStatus
    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus);
    }

    // aqui usa o outro metodo pra fazer o inverso, que é pegar um enum do tipo OrderStatus e pegar o codigo dele, ja que this.orderStatus é um Integer
    public void setOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != null) this.orderStatus = orderStatus.getCode();
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    // implementando o método getTotal que retorna o subTotal somado de todos os OrderItems
    public Double getTotal() {
        double sum = 0.0;
        for (OrderItem x : items) {
            sum += x.getSubTotal();
        }
        return sum;
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
