package model;

import java.math.BigDecimal;

public class Dish {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;
    private int complexityOfExecution;

    private boolean isavailable = true;
    public Dish() {}
    // Constructor
    public Dish(int id, String name, String description, int quantity, BigDecimal price, int complexityOfExecution) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.complexityOfExecution = complexityOfExecution;
    }

    public boolean getIsAvailable() { return isavailable; }
    public void setIsAvailable(boolean isAvailable) { this.isavailable = isAvailable; }
    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public int getComplexityOfExecution() { return complexityOfExecution; }


    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setComplexityOfExecution(int complexityOfExecution) { this.complexityOfExecution = complexityOfExecution; }
}
