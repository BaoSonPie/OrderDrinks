package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;   // ✅ thêm
import android.text.InputType;    // ✅ thêm
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 🔽 Thêm đoạn xử lý ẩn/hiện mật khẩu
        edtPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2; // vị trí drawableEnd
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edtPassword.getRight()
                        - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    v.performClick();
                    // Nếu đang là password thì chuyển sang hiện
                    if (edtPassword.getInputType() ==
                            (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_eye_open_24, 0);
                    } else {
                        // Nếu đang hiện thì chuyển về ẩn
                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edtPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_eye_24, 0);
                    }
                    edtPassword.setSelection(edtPassword.getText().length()); // con trỏ về cuối
                    return true;
                }
            }
            return false;
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsername.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                // ✅ Check account tĩnh (tạm thời)
                if (user.equals("admin") && pass.equals("123456")) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

                /* 🔥 Sau này sẽ thay bằng Firebase Auth
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                 */
            }
        });
    }
}
