package com.example.quanlynhatro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatro.ActivityChiTietHoaDon;
import com.example.quanlynhatro.HoaDon;
import com.example.quanlynhatro.R;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {

    Context context;
    List<HoaDon> list;

    public HoaDonAdapter(Context context, List<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(com.example.quanlynhatro.R.layout.item_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        HoaDon hd = list.get(position);

        h.tvThangNam.setText("Tháng " + hd.getThangNam());
        h.tvTongTien.setText("Tổng tiền: " + String.format("%,d", hd.getTongTien()) + "đ");

        if ("Đã thanh toán".equals(hd.getTrangThai())) {
            h.tvTrangThai.setText("Đã thanh toán");
            h.tvTrangThai.setTextColor(Color.parseColor("#388E3C"));
        } else {
            h.tvTrangThai.setText("Chưa thanh toán");
            h.tvTrangThai.setTextColor(Color.parseColor("#D32F2F"));
        }

        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ActivityChiTietHoaDon.class);
            i.putExtra("idHoaDon", hd.getIdHoaDon());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvThangNam, tvTongTien, tvTrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvThangNam = itemView.findViewById(R.id.tvThangNam);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}
