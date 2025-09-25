package com.example.hasmilktea;

public class Order {
    private int id;
    private String items;      // danh sách món (có thể lưu dạng JSON hoặc chuỗi ",")
    private int total;         // tổng tiền
    private String staffName;  // tên nhân viên order
    private String createdAt;  // ngày giờ tạo đơn
    private String status;     // trạng thái đơn ("xong", "chưa")

    public Order(int id, String items, int total, String staffName, String createdAt, String status) {
        this.id = id;
        this.items = items;
        this.total = total;
        this.staffName = staffName;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
