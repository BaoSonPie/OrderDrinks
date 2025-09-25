package com.example.hasmilktea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private Context context;
    private List<Staff> staffList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(Staff staff);
        void onDelete(Staff staff);
    }

    public StaffAdapter(Context context, List<Staff> staffList, OnItemClickListener listener) {
        this.context = context;
        this.staffList = staffList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_staff, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        Staff staff = staffList.get(position);
        holder.txtName.setText(staff.getName());
        holder.txtRole.setText(staff.getRole());
        holder.txtPhone.setText(staff.getPhone());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(staff));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(staff));
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtRole, txtPhone;
        ImageButton btnEdit, btnDelete;

        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtStaffName);
            txtRole = itemView.findViewById(R.id.txtStaffRole);
            txtPhone = itemView.findViewById(R.id.txtStaffPhone);
            btnEdit = itemView.findViewById(R.id.btnEditStaff);
            btnDelete = itemView.findViewById(R.id.btnDeleteStaff);
        }
    }
}
