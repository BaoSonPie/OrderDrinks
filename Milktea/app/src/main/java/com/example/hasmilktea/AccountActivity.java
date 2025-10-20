package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View; // Import cho View.OnClickListener
import android.util.Log;

public class AccountActivity extends AppCompatActivity {

    TextView tvName, tvRole, tvEmail, tvPhone, tvPassword; // ✅ THÊM tvPassword
    Button btnLogout;

    private boolean isPasswordVisible = false; // ✅ Biến theo dõi trạng thái
    private String actualPassword = ""; // ✅ Biến lưu mật khẩu thực
    private final String HIDDEN_PASSWORD_TEXT = "**********"; // Văn bản ẩn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo bạn đang sử dụng layout chính xác, ví dụ: R.layout.activity_profile
        setContentView(R.layout.activity_account); // Giả sử tên layout là activity_account

        tvName = findViewById(R.id.tvName);
        tvRole = findViewById(R.id.tvRole);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvPassword = findViewById(R.id.tvPassword); // ✅ Ánh xạ tvPassword
        btnLogout = findViewById(R.id.btnLogout);

        // ✅ Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("staff_name");
        String role = intent.getStringExtra("staff_role");
        String email = intent.getStringExtra("staff_email");
        String phone = intent.getStringExtra("staff_phone");
        // Lấy mật khẩu và lưu vào biến
        actualPassword = intent.getStringExtra("staff_password"); // ✅ LƯU MẬT KHẨU THỰC

        // ✅ Hiển thị dữ liệu
        tvName.setText("Họ tên: " + name);
        tvRole.setText("Chức vụ: " + role);
        tvEmail.setText("Email: " + email);
        tvPhone.setText("SĐT: " + phone);

        // ✅ HIỂN THỊ MẬT KHẨU BAN ĐẦU (ẨN)
        updatePasswordView();

        // ✅ XỬ LÝ SỰ KIỆN CLICK CHO MẬT KHẨU
        tvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái và cập nhật hiển thị
                isPasswordVisible = !isPasswordVisible;
                updatePasswordView();
            }
        });

        // 🔙 Nút đăng xuất (Giữ nguyên)
        btnLogout.setOnClickListener(v -> {
            Intent logoutIntent = new Intent(AccountActivity.this, LoginActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
            finish();
        });
    }

    // ✅ PHƯƠNG THỨC CẬP NHẬT GIAO DIỆN MẬT KHẨU
    private void updatePasswordView() {
        if (isPasswordVisible) {
            // Hiển thị mật khẩu
            tvPassword.setText("Mật khẩu: " + actualPassword);
            // Đặt icon mắt mở (báo hiệu click để ẩn)
            tvPassword.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.password_eye_open_24, 0 // ✅ Thay bằng drawable của bạn (ví dụ: ic_visibility_off)
            );
        } else {
            // Ẩn mật khẩu
            tvPassword.setText("Mật khẩu: " + HIDDEN_PASSWORD_TEXT);
            // Đặt icon mắt đóng (báo hiệu click để hiện)
            tvPassword.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.password_eye_24, 0 // ✅ Thay bằng drawable của bạn (ví dụ: ic_visibility)
            );
        }
    }
}