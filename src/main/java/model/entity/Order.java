package model.entity;

import java.time.LocalDateTime;

public class Order {
    private String id;
    private LocalDateTime date;
    private double totalAmount;
    private boolean paid;
    private Client client;

    public Order() {
    }

    public Order(String id, LocalDateTime date, double totalAmount, boolean paid, Client client) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.paid = paid;
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getIgv() {
        return Math.round(totalAmount * 0.18 * 100.0) / 100.0;
    }

    public double getSubtotal() {
        return Math.round((totalAmount - getIgv()) * 100.0) / 100.0;
    }
}
