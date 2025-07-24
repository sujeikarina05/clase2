package model.enums;

public enum PaymentMethods {
    CASH("Efectivo"),
    CARD("Tarjeta"),
    EWALLET("Billetera Digital");

    private final String name;

    PaymentMethods(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
