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
                    cursor.getString(cursor.getColumnIndexOrThrow("role")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phone")),"","",""
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
                dbHelper.deleteStaff(staff.getId());
                loadStaffs();
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
        EditText edtRole = view.findViewById(R.id.edtStaffRole);
        EditText edtPhone = view.findViewById(R.id.edtStaffPhone);
        Button btnSave = view.findViewById(R.id.btnSaveStaff);
//        Button btnCancel = view.findViewById(R.id.btnCancelStaff);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String role = edtRole.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            if (name.isEmpty() || role.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertStaff(name, role, phone,"","","");
            if (inserted) {
                Toast.makeText(this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                loadStaffs();
            } else {
                Toast.makeText(this, "Lỗi khi thêm nhân viên", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        //btnCancel.setOnClickListener(v -> dialog.dismiss());
    }

    private void showEditDialog(Staff staff) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_staff, null);
        builder.setView(view);

        EditText edtName = view.findViewById(R.id.edtStaffName);
        EditText edtRole = view.findViewById(R.id.edtStaffRole);
        EditText edtPhone = view.findViewById(R.id.edtStaffPhone);
        Button btnSave = view.findViewById(R.id.btnUpdateStaff);
      //  Button btnCancel = view.findViewById(R.id.btnCancelStaff);

        // Set dữ liệu mặc định
        edtName.setText(staff.getName());
        edtRole.setText(staff.getRole());
        edtPhone.setText(staff.getPhone());

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            String newName = edtName.getText().toString().trim();
            String newRole = edtRole.getText().toString().trim();
            String newPhone = edtPhone.getText().toString().trim();

            if (newName.isEmpty() || newRole.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = dbHelper.updateStaff(staff.getId(), newName, newRole, newPhone,"","","");
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
}
