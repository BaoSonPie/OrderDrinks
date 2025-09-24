package com.example.hasmilktea;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class MenuManageAdapter extends RecyclerView.Adapter<MenuManageAdapter.ViewHolder> {

    private Context context;
    private List<MenuItem> menuList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(MenuItem item);
        void onDelete(MenuItem item);
    }

    public MenuManageAdapter(Context context, List<MenuItem> menuList, OnItemClickListener listener) {
        this.context = context;
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = menuList.get(position);

        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(item.getPrice() + "đ");

        // === Load ảnh từ file path ===
        if (item.getImage() != null && !item.getImage().isEmpty()) {
            File file = new File(item.getImage());
            if (file.exists()) {
                holder.imgItem.setImageBitmap(BitmapFactory.decodeFile(item.getImage()));
            } else {
                holder.imgItem.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        } else {
            holder.imgItem.setImageResource(android.R.drawable.ic_menu_report_image);
        }

        // === Bắt sự kiện nút Edit/Delete ===
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(item);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imgItem, btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgItem = itemView.findViewById(R.id.imgItem);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
