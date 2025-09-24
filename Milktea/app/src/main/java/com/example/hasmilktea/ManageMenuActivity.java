package com.example.hasmilktea;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ManageMenuActivity extends AppCompatActivity {
    RecyclerView recyclerManageMenu;
    MenuManageAdapter adapter;
    List<MenuItem> menuList;
    DBHelper dbHelper;
    Button btnAddItem;

    private Uri selectedImageUri;

    // launcher cho chọn ảnh khi thêm
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    // launcher cho chọn ảnh khi sửa
    private ActivityResultLauncher<Intent> editImagePickerLauncher;

    // biến tạm để lưu preview image khi sửa
    private ImageView currentEditImageView;
    private String[] currentNewImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        recyclerManageMenu = findViewById(R.id.recyclerManageMenu);
        btnAddItem = findViewById(R.id.btnAddItem);
        dbHelper = new DBHelper(this);

        loadMenu();

        // init image picker cho thêm món
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (currentEditImageView != null) {
                            currentEditImageView.setImageURI(selectedImageUri);
                        }
                    }
                });

        // init image picker cho sửa món
        editImagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (currentEditImageView != null) {
                            currentEditImageView.setImageURI(selectedImageUri);
                        }
                        if (currentNewImagePath != null) {
                            // xóa ảnh cũ, lưu ảnh mới
                            deleteOldImage(currentNewImagePath[0]);
                            currentNewImagePath[0] = saveImageToInternalStorage(selectedImageUri);
                        }
                    }
                });

        btnAddItem.setOnClickListener(v -> showAddDialog());
    }

    private void loadMenu() {
        menuList = new ArrayList<>();

        Cursor c1 = dbHelper.getAllDrinks();
        while (c1.moveToNext()) {
            menuList.add(new MenuItem(
                    c1.getInt(0),
                    c1.getString(1),
                    c1.getString(2),
                    c1.getInt(3),
                    c1.getString(4),
                    "drink"
            ));
        }
        c1.close();

        Cursor c2 = dbHelper.getAllFoods();
        while (c2.moveToNext()) {
            menuList.add(new MenuItem(
                    c2.getInt(0),
                    c2.getString(1),
                    c2.getString(2),
                    c2.getInt(3),
                    c2.getString(4),
                    "food"
            ));
        }
        c2.close();

        adapter = new MenuManageAdapter(this, menuList, new MenuManageAdapter.OnItemClickListener() {
            @Override
            public void onEdit(MenuItem item) {
                showEditDialog(item);
            }

            @Override
            public void onDelete(MenuItem item) {
                deleteOldImage(item.getImage());
                if (item.getCategory().equals("drink")) {
                    dbHelper.deleteDrink(item.getId());
                } else {
                    dbHelper.deleteFood(item.getId());
                }
                loadMenu();
            }
        });

        recyclerManageMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerManageMenu.setAdapter(adapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_add_menu, null);

        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtPrice = view.findViewById(R.id.edtPrice);
        Spinner spnCategory = view.findViewById(R.id.spnCategory);
        ImageView imgPreview = view.findViewById(R.id.imgPreview);
        Button btnChooseImg = view.findViewById(R.id.btnChooseImg);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnChooseImg.setOnClickListener(v -> {
            currentEditImageView = imgPreview;
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();
            String category = spnCategory.getSelectedItem().toString().toLowerCase();

            if (name.isEmpty() || priceStr.isEmpty() || selectedImageUri == null) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin và chọn ảnh", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            String imagePath = saveImageToInternalStorage(selectedImageUri);
            if (imagePath == null) {
                Toast.makeText(this, "Không thể lưu ảnh", Toast.LENGTH_SHORT).show();
                return;
            }

            if (category.equals("drink")) {
                dbHelper.insertDrink(name, imagePath, price, "default");
            } else {
                dbHelper.insertFood(name, imagePath, price, "default");
            }

            selectedImageUri = null;
            dialog.dismiss();
            loadMenu();
        });

        btnCancel.setOnClickListener(v -> {
            selectedImageUri = null;
            dialog.dismiss();
        });
    }

    private void showEditDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_edit_menu, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtPrice = view.findViewById(R.id.edtPrice);
        Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        ImageView imgPreview = view.findViewById(R.id.imgPreview);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        selectedImageUri = null;

        edtName.setText(item.getName());
        edtPrice.setText(String.valueOf(item.getPrice()));

        String[] categories = {"Trà sữa", "Trà trái cây", "Cà phê", "Đồ ăn vặt"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapterCategory);

        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equalsIgnoreCase(item.getType())) {
                spinnerCategory.setSelection(i);
                break;
            }
        }

        if (item.getImage() != null && !item.getImage().isEmpty()) {
            File file = new File(item.getImage());
            if (file.exists()) {
                imgPreview.setImageBitmap(BitmapFactory.decodeFile(item.getImage()));
            } else {
                imgPreview.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        }

        currentNewImagePath = new String[]{item.getImage()};
        currentEditImageView = imgPreview;

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            editImagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String newName = edtName.getText().toString().trim();
            int newPrice = Integer.parseInt(edtPrice.getText().toString().trim());
            String newCategory = spinnerCategory.getSelectedItem().toString();

            // Nếu chưa chọn ảnh mới -> giữ ảnh cũ
            if (currentNewImagePath[0] == null || currentNewImagePath[0].isEmpty()) {
                currentNewImagePath[0] = item.getImage();
            }

            if (item.getCategory().equals("drink")) {
                dbHelper.updateDrink(item.getId(), newName, currentNewImagePath[0], newPrice, newCategory);
            } else {
                dbHelper.updateFood(item.getId(), newName, currentNewImagePath[0], newPrice, newCategory);
            }

            dialog.dismiss();
            loadMenu();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
    private String saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), System.currentTimeMillis() + ".jpg");

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteOldImage(String oldPath) {
        if (oldPath != null && !oldPath.isEmpty()) {
            File file = new File(oldPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
