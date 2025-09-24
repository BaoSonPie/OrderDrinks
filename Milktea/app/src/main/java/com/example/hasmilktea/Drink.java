package com.example.hasmilktea;

public class Drink {
    private int id;
    private String name;
    private int image; // resource id hoặc link
    private int price;
    private String type;

    public Drink(int id, String name, int image, int price, String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.type = type;
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getName() { return name; }
    public int getImage() { return image; }
    public int getPrice() { return price; }
    public String getType() { return type; }
}
