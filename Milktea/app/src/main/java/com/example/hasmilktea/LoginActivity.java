package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ View và Khởi tạo DBHelper (Giữ nguyên)
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DBHelper(this);

        // Chức năng ẩn/hiện mật khẩu (Giữ nguyên)
        edtPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edtPassword.getRight()
                        - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    v.performClick();
                    if (edtPassword.getInputType() ==
                            (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_eye_open_24, 0);
                    } else {
                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_eye_24, 0);
                    }
                    edtPassword.setSelection(edtPassword.getText().length());
                    return true;
                }
            }
            return false;
        });

        // 🔑 Xử lý đăng nhập và PHÂN QUYỀN
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = dbHelper.login(username, password);
            if (cursor != null && cursor.moveToFirst()) {
                try {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String role = cursor.getString(cursor.getColumnIndexOrThrow("role")); // ✅ LẤY CHỨC VỤ
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                    Toast.makeText(this, "Đăng nhập thành công! Xin chào " + name, Toast.LENGTH_SHORT).show();

                    Intent intent;

                    if (role.equalsIgnoreCase("Admin")) {
                        // Admin: Vào DashboardActivity
                        intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    } else {
                        // Nhân viên: Vào OrderManagementActivity
                        intent = new Intent(LoginActivity.this, OrderManagementActivity.class);
                        // THAY OrderManagementActivity.class bằng Activity quản lý Order của bạn
                    }

                    // Truyền thông tin nhân viên sang Activity tiếp theo (cần thiết cho AccountActivity)
                    intent.putExtra("staff_id", id);
                    intent.putExtra("staff_name", name);
                    intent.putExtra("staff_role", role);
                    intent.putExtra("staff_email", email);
                    intent.putExtra("staff_phone", phone);
                    intent.putExtra("staff_password", password); // Vẫn truyền mật khẩu

                    startActivity(intent);
                    finish();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(this, "Lỗi dữ liệu: Thiếu cột thông tin nhân viên.", Toast.LENGTH_LONG).show();
                } finally {
                    cursor.close();
                }
            } else {
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }
}