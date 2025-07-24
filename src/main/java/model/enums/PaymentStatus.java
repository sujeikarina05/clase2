package model.enums;

public enum PaymentStatus {
    PAID("Pagado"),
    PENDING("Pendiente"),
    CANCELED("Cancelado");

    private final String name;

    PaymentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
