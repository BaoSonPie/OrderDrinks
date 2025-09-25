package com.example.hasmilktea;

public class Employee {
    private int id;
    private String name;
    private String position; // chức vụ: thu ngân, pha chế...
    private String phone;
    private String image;    // đường dẫn ảnh

    public Employee() {}

    public Employee(int id, String name, String position, String phone, String image) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.image = image;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
