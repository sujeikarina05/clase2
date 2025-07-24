package model.entity;

import model.enums.PaymentMethods;
import model.enums.PaymentStatus;

public class Payment {
    private String id;
    private PaymentStatus status;
    private PaymentMethods method;
    private int transactionNumber;
    private Order order;

    public Payment() {
    }

    public Payment(String id, PaymentStatus status, PaymentMethods method, int transactionNumber, Order order) {
        this.id = id;
        this.status = status;
        this.method = method;
        this.transactionNumber = transactionNumber;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethods getMethod() {
        return method;
    }

    public void setMethod(PaymentMethods method) {
        this.method = method;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
