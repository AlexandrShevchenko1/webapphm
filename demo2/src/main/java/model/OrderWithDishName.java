package model;

public class OrderWithDishName extends Order {
    private String dishName;

    // Getters and setters
    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}