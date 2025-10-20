package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class DashboardActivity extends AppCompatActivity {

    ImageView btnMenu, btnOrder, btnStaff, btnAccount, coffee, khoai, milktea, ga;

    // ✅ 1. Khai báo biến để lưu mật khẩu
    private String staffPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ✅ 2. Lấy mật khẩu từ Intent mà LoginActivity đã gửi đến
        staffPassword = getIntent().getStringExtra("staff_password");

        btnMenu = findViewById(R.id.btnMenu);
        btnOrder = findViewById(R.id.btnOrder);
        btnStaff = findViewById(R.id.btnStaff);
        btnAccount = findViewById(R.id.btnAccount);
        coffee = findViewById(R.id.coffee);
        khoai = findViewById(R.id.khoai);
        milktea = findViewById(R.id.milktea);
        ga = findViewById(R.id.ga);

        // Tải hiệu ứng xoay (Giữ nguyên)
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);

        // Gán hiệu ứng xoay cho từng nút (ImageView) (Giữ nguyên)
        coffee.startAnimation(rotateAnimation);
        khoai.startAnimation(rotateAnimation);
        milktea.startAnimation(rotateAnimation);
        ga.startAnimation(rotateAnimation);

        // Xử lý sự kiện khi click (Giữ nguyên)
        btnMenu.setOnClickListener(v ->
                startActivity(new Intent(this, ManageMenuActivity.class))
        );

        btnOrder.setOnClickListener(v ->
                startActivity(new Intent(this, OrderManagementActivity.class))
        );

        btnStaff.setOnClickListener(v ->
                        //Toast.makeText(this, "Đi đến Quản lý Nhân viên", Toast.LENGTH_SHORT).show()
                 startActivity(new Intent(this, ManageStaffActivity.class))
        );

        btnAccount.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AccountActivity.class);
            // ✅ Truyền các thông tin đã nhận từ LoginActivity
            intent.putExtra("staff_name", getIntent().getStringExtra("staff_name"));
            intent.putExtra("staff_role", getIntent().getStringExtra("staff_role"));
            intent.putExtra("staff_email", getIntent().getStringExtra("staff_email"));
            intent.putExtra("staff_phone", getIntent().getStringExtra("staff_phone"));

            // ✅ THAY THẾ bằng biến đã lưu
            intent.putExtra("staff_password", staffPassword);

            startActivity(intent);
        });
    }
}