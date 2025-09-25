package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    ImageView btnMenu, btnOrder, btnStaff, btnAccount, coffee, khoai, milktea, ga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnMenu = findViewById(R.id.btnMenu);
        btnOrder = findViewById(R.id.btnOrder);
        btnStaff = findViewById(R.id.btnStaff);
        btnAccount = findViewById(R.id.btnAccount);
        coffee = findViewById(R.id.coffee);
        khoai = findViewById(R.id.khoai);
        milktea = findViewById(R.id.milktea);
        ga = findViewById(R.id.ga);

        // Tải hiệu ứng xoay
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);

        // Gán hiệu ứng xoay cho từng nút (ImageView)
        coffee.startAnimation(rotateAnimation);
        khoai.startAnimation(rotateAnimation);
        milktea.startAnimation(rotateAnimation);
        ga.startAnimation(rotateAnimation);

        // Xử lý sự kiện khi click
        btnMenu.setOnClickListener(v ->
//                        Toast.makeText(this, "Đi đến Quản lý Menu", Toast.LENGTH_SHORT).show()
                startActivity(new Intent(this, ManageMenuActivity.class))
        );

        btnOrder.setOnClickListener(v ->
                        //Toast.makeText(this, "Đi đến Quản lý Order", Toast.LENGTH_SHORT).show()
                startActivity(new Intent(this, OrderManagementActivity.class))
        );
//thay đổi button QLNV
        btnStaff.setOnClickListener(v -> {
            Toast.makeText(this, "Đi đến Quản lý Nhân viên", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ManageStaffActivity.class);
            startActivity(intent);
        });


        btnAccount.setOnClickListener(v ->
                        Toast.makeText(this, "Đi đến Tài khoản", Toast.LENGTH_SHORT).show()
                // startActivity(new Intent(this, AccountActivity.class));
        );
    }
}
