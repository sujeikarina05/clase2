package model.entity;

public class Dish {
    private String id;
    private String name;
    private double price;
    private String category_id;

    public Dish() {
    }

    public Dish(String id, String name, double price, String category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category_id = category_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
