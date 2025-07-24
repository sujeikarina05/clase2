package model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    @NonNull
    private LocalDateTime date;
    @NonNull
    private double totalAmount;
    private boolean paid;
    @NonNull
    private Client client;

    public double getIgv() {
        return Math.round(totalAmount * 0.18 * 100.0) / 100.0;
    }

    public double getSubtotal() {
        return Math.round((totalAmount - getIgv()) * 100.0) / 100.0;
    }
}
