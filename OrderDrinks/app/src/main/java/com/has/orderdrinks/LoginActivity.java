package com.has.orderdrinks;// thay bằng package của bạn

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Xử lý sự kiện login
        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            // Kiểm tra nhập trống
            if (TextUtils.isEmpty(user)) {
                edtUsername.setError("Vui lòng nhập username");
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                edtPassword.setError("Vui lòng nhập password");
                return;
            }

            // Kiểm tra tài khoản mẫu
            if (user.equals("admin") && pass.equals("123")) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // Chuyển sang MenuActivity
//                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
//                startActivity(intent);
//                finish(); // không cho back lại LoginActivity
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
