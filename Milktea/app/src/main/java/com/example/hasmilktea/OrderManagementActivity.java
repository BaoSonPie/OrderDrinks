package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.database.Cursor;
import android.widget.AdapterView;
import java.util.List;
import android.view.View;


public class OrderManagementActivity extends AppCompatActivity {

    RecyclerView recyclerMenu;
    SearchView searchView;
    Spinner spinnerCategory;

    MenuAdapter menuAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        recyclerMenu = findViewById(R.id.recyclerMenu);
        searchView = findViewById(R.id.searchView);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        dbHelper = new DBHelper(this);

        // Spinner category
        String[] categories = {"Tất cả", "Trà sữa", "Trà trái cây", "Cà phê", "Đồ ăn vặt"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(categoryAdapter);

        // Load dữ liệu drinks + foods
        List<MenuItem> menuList = new ArrayList<>();
        Cursor cursorDrinks = dbHelper.getAllDrinks();
        while (cursorDrinks.moveToNext()) {
            menuList.add(new MenuItem(
                    cursorDrinks.getInt(0),
                    cursorDrinks.getString(1),
                    cursorDrinks.getString(2),
                    cursorDrinks.getInt(3),
                    cursorDrinks.getString(4),
                    "drink"
            ));
        }
        cursorDrinks.close();

        Cursor cursorFoods = dbHelper.getAllFoods();
        while (cursorFoods.moveToNext()) {
            menuList.add(new MenuItem(
                    cursorFoods.getInt(0),
                    cursorFoods.getString(1),
                    cursorFoods.getString(2),
                    cursorFoods.getInt(3),
                    cursorFoods.getString(4),
                    "food"
            ));
        }
        cursorFoods.close();

        recyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(menuList);
        recyclerMenu.setAdapter(menuAdapter);

        // Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                menuAdapter.filter(query, spinnerCategory.getSelectedItem().toString());
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                menuAdapter.filter(newText, spinnerCategory.getSelectedItem().toString());
                return true;
            }
        });

        // Filter theo loại (spinner)
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.filter(searchView.getQuery().toString(), categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
