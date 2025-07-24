package com.mycompany.polloloco.modelo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un pedido realizado por un cliente en el sistema «Pollería Pollo Loco».
 */
public class Order {

    /** Identificador único (UUID o generado en BD). */
    private String id;

    /** Fecha y hora de creación del pedido. */
    private LocalDateTime date;

    /** Importe total del pedido (incluye IGV). */
    private double totalAmount;

    /** Indica si el pedido ya fue pagado totalmente. */
    private boolean paid;

    /** Cliente que realiza el pedido. */
    private Client client;

    /** Tasa de IGV (Impuesto General a las Ventas) peruano. */
    private static final double IGV_RATE = 0.18;

    /** Constructor sin argumentos requerido por algunos frameworks (p. ej. JPA). */
    public Order() {
    }

    /**
     * Crea un pedido con todos los datos.
     *
     * @param id          identificador del pedido
     * @param date        fecha y hora del pedido
     * @param totalAmount importe total (incluye IGV)
     * @param paid        indicador de pago
     * @param client      cliente asociado
     */
    public Order(String id, LocalDateTime date, double totalAmount, boolean paid, Client client) {
        this.id = id;
        this.date = date;
        this.totalAmount = totalAmount;
        this.paid = paid;
        this.client = client;
    }

    /**
     * Crea un pedido nuevo (sin ID y aún no pagado).
     *
     * @param date        fecha y hora del pedido
     * @param totalAmount importe total (incluye IGV)
     * @param client      cliente asociado
     */
    public Order(LocalDateTime date, double totalAmount, Client client) {
        this(null, date, totalAmount, false, client);
    }

    // ---------- Getters & Setters ----------

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

    // ---------- Métodos derivados ----------

    /**
     * Calcula el IGV incluido en el importe total.
     *
     * @return IGV redondeado a 2 decimales
     */
    public double getIgv() {
        return Math.round(totalAmount * IGV_RATE * 100.0) / 100.0;
    }

    /**
     * Calcula el subtotal (sin IGV).
     *
     * @return subtotal redondeado a 2 decimales
     */
    public double getSubtotal() {
        return Math.round((totalAmount - getIgv()) * 100.0) / 100.0;
    }

    // ---------- Utilidades ----------

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                ", paid=" + paid +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}