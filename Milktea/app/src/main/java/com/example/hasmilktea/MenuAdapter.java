package com.example.hasmilktea;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuItem> menuList;
    private List<MenuItem> menuListFull; // để search + filter

    public MenuAdapter(List<MenuItem> menuList) {
        this.menuList = new ArrayList<>(menuList == null ? new ArrayList<>() : menuList);
        this.menuListFull = new ArrayList<>(this.menuList);
    }

    // ----------------- Interface callback -----------------
    public interface OnItemClickListener {
        void onEditClick(MenuItem item, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // ----------------- Adapter methods -----------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = menuList.get(position);
        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(item.getPrice() + "đ");

        // ---- Xử lý ảnh (hỗ trợ Uri string hoặc string chứa resource id) ----
        String img = item.getImage(); // MenuItem.getImage() trả về String
        if (img == null || img.trim().isEmpty()) {
            holder.imgItem.setImageResource(android.R.drawable.ic_menu_report_image);
        } else {
            try {
                if (img.matches("^\\d+$")) { // nếu là số (resource id)
                    int resId = Integer.parseInt(img);
                    holder.imgItem.setImageResource(resId);
                } else { // nếu là Uri
                    Uri uri = Uri.parse(img);
                    holder.imgItem.setImageURI(uri);
                }
            } catch (Exception e) {
                holder.imgItem.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        }

        // Gắn sự kiện click nút sửa
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    // ----------------- Helper methods -----------------
    public void setData(List<MenuItem> newList) {
        this.menuList.clear();
        this.menuList.addAll(newList == null ? new ArrayList<>() : newList);

        this.menuListFull.clear();
        this.menuListFull.addAll(this.menuList);

        notifyDataSetChanged();
    }

    public void filter(String query, String categoryFilter) {
        String q = (query == null) ? "" : query.toLowerCase(Locale.ROOT).trim();
        menuList.clear();
        for (MenuItem item : menuListFull) {
            boolean matchQuery = item.getName() != null && item.getName().toLowerCase(Locale.ROOT).contains(q);
            boolean matchCategory = categoryFilter == null || categoryFilter.equals("Tất cả")
                    || categoryFilter.isEmpty() || item.getType().equalsIgnoreCase(categoryFilter);
            if (matchQuery && matchCategory) menuList.add(item);
        }
        notifyDataSetChanged();
    }

    // ----------------- ViewHolder -----------------
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imgItem;
        ImageButton btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgItem = itemView.findViewById(R.id.imgItem);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
