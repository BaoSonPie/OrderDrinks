package com.example.hasmilktea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "has_milktea.db";
    private static final int DATABASE_VERSION = 1;

    // ---------- Drinks ----------
    private static final String TABLE_DRINKS = "drinks";
    private static final String COL_DRINK_ID = "id";
    private static final String COL_DRINK_NAME = "name";
    private static final String COL_DRINK_IMAGE = "image";
    private static final String COL_DRINK_PRICE = "price";
    private static final String COL_DRINK_TYPE = "type";

    // ---------- Foods ----------
    private static final String TABLE_FOODS = "foods";
    private static final String COL_FOOD_ID = "id";
    private static final String COL_FOOD_NAME = "name";
    private static final String COL_FOOD_IMAGE = "image";
    private static final String COL_FOOD_PRICE = "price";
    private static final String COL_FOOD_TYPE = "type";

    // ---------- Orders ----------
    private static final String TABLE_ORDERS = "orders";
    private static final String COL_ORDER_ID = "id";
    private static final String COL_ORDER_ITEMS = "items";   // JSON hoặc String
    private static final String COL_ORDER_TOTAL = "total";
    private static final String COL_ORDER_STAFF = "staff_name";
    private static final String COL_ORDER_CREATED_AT = "created_at";
    private static final String COL_ORDER_STATUS = "status";

    // ---------- Staff ----------
    private static final String TABLE_STAFF = "staff";
    private static final String COL_STAFF_ID = "id";
    private static final String COL_STAFF_NAME = "name";
    private static final String COL_STAFF_USERNAME = "username";
    private static final String COL_STAFF_PASSWORD = "password";
    private static final String COL_STAFF_PHONE = "phone";
    private static final String COL_STAFF_EMAIL = "email";
    private static final String COL_STAFF_ROLE = "role";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Drinks
        db.execSQL("CREATE TABLE " + TABLE_DRINKS + " (" +
                COL_DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DRINK_NAME + " TEXT, " +
                COL_DRINK_IMAGE + " TEXT, " +
                COL_DRINK_PRICE + " INTEGER, " +
                COL_DRINK_TYPE + " TEXT)");

        // Foods
        db.execSQL("CREATE TABLE " + TABLE_FOODS + " (" +
                COL_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FOOD_NAME + " TEXT, " +
                COL_FOOD_IMAGE + " TEXT, " +
                COL_FOOD_PRICE + " INTEGER, " +
                COL_FOOD_TYPE + " TEXT)");

        // Orders
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                COL_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ORDER_ITEMS + " TEXT, " +
                COL_ORDER_TOTAL + " INTEGER, " +
                COL_ORDER_STAFF + " TEXT, " +
                COL_ORDER_CREATED_AT + " TEXT, " +
                COL_ORDER_STATUS + " TEXT)");

        // Staff
        db.execSQL("CREATE TABLE " + TABLE_STAFF + " (" +
                COL_STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STAFF_NAME + " TEXT, " +
                COL_STAFF_USERNAME + " TEXT, " +
                COL_STAFF_PASSWORD + " TEXT, " +
                COL_STAFF_PHONE + " TEXT, " +
                COL_STAFF_EMAIL + " TEXT, " +
                COL_STAFF_ROLE + " TEXT)");

        // Insert mẫu staff
        db.execSQL("INSERT INTO " + TABLE_STAFF + " (" +
                COL_STAFF_NAME + ", " + COL_STAFF_USERNAME + ", " + COL_STAFF_PASSWORD + ", " +
                COL_STAFF_PHONE + ", " + COL_STAFF_EMAIL + ", " + COL_STAFF_ROLE +
                ") VALUES ('Admin', 'admin', '123456', '0123456789', 'admin@email.com', 'Admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
        onCreate(db);
    }

    // ------------ Drinks CRUD ------------
    public boolean insertDrink(String name, String image, int price, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DRINK_NAME, name);
        values.put(COL_DRINK_IMAGE, image);
        values.put(COL_DRINK_PRICE, price);
        values.put(COL_DRINK_TYPE, type);
        return db.insert(TABLE_DRINKS, null, values) != -1;
    }

    public Cursor getAllDrinks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DRINKS, null);
    }

    public boolean updateDrink(int id, String name, String image, int price, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DRINK_NAME, name);
        values.put(COL_DRINK_IMAGE, image);
        values.put(COL_DRINK_PRICE, price);
        values.put(COL_DRINK_TYPE, type);
        return db.update(TABLE_DRINKS, values, COL_DRINK_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteDrink(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_DRINKS, COL_DRINK_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    // ------------ Foods CRUD ------------
    public boolean insertFood(String name, String image, int price, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FOOD_NAME, name);
        values.put(COL_FOOD_IMAGE, image);
        values.put(COL_FOOD_PRICE, price);
        values.put(COL_FOOD_TYPE, type);
        return db.insert(TABLE_FOODS, null, values) != -1;
    }

    public Cursor getAllFoods() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FOODS, null);
    }

    public boolean updateFood(int id, String name, String image, int price, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FOOD_NAME, name);
        values.put(COL_FOOD_IMAGE, image);
        values.put(COL_FOOD_PRICE, price);
        values.put(COL_FOOD_TYPE, type);
        return db.update(TABLE_FOODS, values, COL_FOOD_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FOODS, COL_FOOD_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    // ------------ Orders CRUD ------------
    public boolean insertOrder(String items, int total, String staffName, String createdAt, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_ITEMS, items);
        values.put(COL_ORDER_TOTAL, total);
        values.put(COL_ORDER_STAFF, staffName);
        values.put(COL_ORDER_CREATED_AT, createdAt);
        values.put(COL_ORDER_STATUS, status);
        return db.insert(TABLE_ORDERS, null, values) != -1;
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " ORDER BY " + COL_ORDER_CREATED_AT + " DESC", null);
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_STATUS, newStatus);
        return db.update(TABLE_ORDERS, values, COL_ORDER_ID + "=?", new String[]{String.valueOf(orderId)}) > 0;
    }

    public boolean deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORDERS, COL_ORDER_ID + "=?", new String[]{String.valueOf(orderId)}) > 0;
    }

    // ------------ Staff CRUD + Login ------------
    public boolean insertStaff(String name, String username, String password, String phone, String email, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STAFF_NAME, name);
        values.put(COL_STAFF_USERNAME, username);
        values.put(COL_STAFF_PASSWORD, password);
        values.put(COL_STAFF_PHONE, phone);
        values.put(COL_STAFF_EMAIL, email);
        values.put(COL_STAFF_ROLE, role);
        return db.insert(TABLE_STAFF, null, values) != -1;
    }

    public Cursor getAllStaff() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STAFF, null);
    }

    public boolean updateStaff(int id, String name, String username, String password, String phone, String email, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STAFF_NAME, name);
        values.put(COL_STAFF_USERNAME, username);
        values.put(COL_STAFF_PASSWORD, password);
        values.put(COL_STAFF_PHONE, phone);
        values.put(COL_STAFF_EMAIL, email);
        values.put(COL_STAFF_ROLE, role);
        return db.update(TABLE_STAFF, values, COL_STAFF_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteStaff(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STAFF, COL_STAFF_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Cursor login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STAFF +
                        " WHERE " + COL_STAFF_USERNAME + "=? AND " + COL_STAFF_PASSWORD + "=?",
                new String[]{username, password});
    }

    public void updateMenuItem(int id, String name, int price, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        values.put("type", type);
        db.update("menu", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
