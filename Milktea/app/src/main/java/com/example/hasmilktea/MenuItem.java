package com.example.hasmilktea;

public class MenuItem {
    private int id;
    private String name;
    private String image;
    private int price;
    private String type;      // Loại chi tiết, ví dụ: "Trà sữa", "Cà phê"
    private String category;  // "drink" hoặc "food"

    public MenuItem(int id, String name, String image, int price, String type, String category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.type = type;
        this.category = category;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
