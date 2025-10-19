package com.example.hasmilktea;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageStaffActivity extends AppCompatActivity {

    RecyclerView recyclerStaff;
    StaffAdapter adapter;
    List<Staff> staffList;
    DBHelper dbHelper;
    Button btnAddStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        recyclerStaff = findViewById(R.id.recyclerStaff);
        btnAddStaff = findViewById(R.id.btnAddStaff);
        dbHelper = new DBHelper(this);

        loadStaffs();

        btnAddStaff.setOnClickListener(v -> showAddDialog());
    }

    private void loadStaffs() {
        staffList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllStaff();
        while (cursor.moveToNext()) {
            staffList.add(new Staff(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("role"))
            ));
        }
        cursor.close();

        adapter = new StaffAdapter(this, staffList, new StaffAdapter.OnItemClickListener() {
            @Override
            public void onEdit(Staff staff) {
                showEditDialog(staff);
            }

            @Override
            public void onDelete(Staff staff) {
                String check = staff.getRole();
                if("Staff".equals(check)) {
                    dbHelper.deleteStaff(staff.getId());
                    Toast.makeText(ManageStaffActivity.this, "Không thể xóa Admin", Toast.LENGTH_SHORT).show();
                    loadStaffs();
                }
                else{
                    Toast.makeText(ManageStaffActivity.this, "Không thể xóa Admin", Toast.LENGTH_SHORT).show();
                    loadStaffs();
                }
            }

            @Override
            public void onView(Staff staff) {
                showInfoDialog(staff);
            }
        });

        recyclerStaff.setLayoutManager(new LinearLayoutManager(this));
        recyclerStaff.setAdapter(adapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_staff, null);
        builder.setView(view);

        EditText edtName = view.findViewById(R.id.edtStaffName);
        EditText edtUsername = view.findViewById(R.id.edtStaffUsername);
        EditText edtPassword = view.findViewById(R.id.edtStaffPassword);
        EditText edtPhone = view.findViewById(R.id.edtStaffPhone);
        EditText edtEmail = view.findViewById(R.id.edtStaffEmail);
        EditText edtRole = view.findViewById(R.id.edtStaffRole);
        Button btnSave = view.findViewById((R.id.btnSaveStaff));

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String role = edtRole.getText().toString().trim();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()
                    || phone.isEmpty() || email.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertStaff(name, username, password, phone, email, role);
            if (inserted) {
                Toast.makeText(this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                loadStaffs();
            } else {
                Toast.makeText(this, "Lỗi khi thêm nhân viên", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });
    }

    private void showEditDialog(Staff staff) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_staff, null);
        builder.setView(view);

        EditText edtName = view.findViewById(R.id.edtStaffName);
        EditText edtUsername = view.findViewById(R.id.edtStaffUsername);
        EditText edtPassword = view.findViewById(R.id.edtStaffPassword);
        EditText edtPhone = view.findViewById(R.id.edtStaffPhone);
        EditText edtEmail = view.findViewById(R.id.edtStaffEmail);
        EditText edtRole = view.findViewById(R.id.edtStaffRole);

        Button btnSave = view.findViewById(R.id.btnUpdateStaff);

        // Set dữ liệu mặc định
        edtName.setText(staff.getName());
        edtUsername.setText(staff.getUsername());
        edtPassword.setText(staff.getPassword());
        edtPhone.setText(staff.getPhone());
        edtEmail.setText(staff.getEmail());
        edtRole.setText(staff.getRole());
        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            String newName = edtName.getText().toString().trim();
            String newUsername = edtUsername.getText().toString().trim();
            String newPassword = edtPassword.getText().toString().trim();
            String newPhone = edtPhone.getText().toString().trim();
            String newEmail = edtEmail.getText().toString().trim();
            String newRole = edtRole.getText().toString().trim();

            if (newName.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()
                   || newPhone.isEmpty() || newEmail.isEmpty() || newRole.isEmpty()) {

                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = dbHelper.updateStaff(staff.getId(), newName, newUsername, newPassword,newPhone,newEmail,newRole);
            if (updated) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                loadStaffs();
            } else {
                Toast.makeText(this, "Lỗi khi cập nhật", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        //btnCancel.setOnClickListener(v -> dialog.dismiss());
    }

    private void showInfoDialog(Staff staff) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_info_staff, null);
        builder.setView(view);

        // Ánh xạ view
        EditText edtName = view.findViewById(R.id.edtStaffName);
        EditText edtUsername = view.findViewById(R.id.edtStaffUsername);
        EditText edtPassword = view.findViewById(R.id.edtStaffPassword);
        EditText edtPhone = view.findViewById(R.id.edtStaffPhone);
        EditText edtEmail = view.findViewById(R.id.edtStaffEmail);
        EditText edtRole = view.findViewById(R.id.edtStaffRole);
        Button btnClose = view.findViewById(R.id.btnClose);

        // Set dữ liệu
        edtName.setText(staff.getName());
        edtUsername.setText(staff.getUsername());
        edtPassword.setText(staff.getPassword());
        edtPhone.setText(staff.getPhone());
        edtEmail.setText(staff.getEmail());
        edtRole.setText(staff.getRole());

        // Khóa edit (chỉ xem, không sửa)
        edtName.setEnabled(false);
        edtUsername.setEnabled(false);
        edtPassword.setEnabled(false);
        edtPhone.setEnabled(false);
        edtEmail.setEnabled(false);
        edtRole.setEnabled(false);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnClose.setOnClickListener(v -> dialog.dismiss());
    }

}
