package model.entity;

public class CartDish {
    private Dish dish;
    private int quantity;
    private double subtotal;
    private String notes;

    public CartDish() {
    }

    public CartDish(Dish dish, int quantity, String notes) {
        this.dish = dish;
        this.quantity = quantity;
        this.notes = notes;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        subtotal = dish.getPrice() * quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        calculateTotalPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
