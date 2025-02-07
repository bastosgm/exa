package com.exa.entities.enums;

public enum OrderStatus {
    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    private int code;

    // esse construtor será um caso especial private pois nao queremos que seja possivel instanciar objetos do tipo OrderStatus
    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // esse metodo é static pois nao depende de estanciar nenhum objeto do tipo OrderStatus
    public static OrderStatus valueOf(int code) {
        // é uma forma que percorre todos os valores possiveis de OrderStatus pra achar o que casa com o codigo passado
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code");
    }
}
