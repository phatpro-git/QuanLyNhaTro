package com.example.quanlynhatro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recleview extends RecyclerView.Adapter<recleview.MyViewHolder>{
    private Context context;
    private ArrayList<String> ten, trangthai;
    private ArrayList<Integer> gia, idPhong;
    public recleview(Context context, ArrayList<Integer> idPhong, ArrayList<String> ten, ArrayList<Integer> gia, ArrayList<String> trangthai){
        this.context = context;
        this.idPhong = idPhong;
        this.ten = ten;
        this.gia = gia;
        this.trangthai = trangthai;
    }
    @NonNull
    @Override
    public recleview.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_view, parent, false);
        return new MyViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull recleview.MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.tenP.setText(String.valueOf(ten.get(position)));
        holder.giaP.setText(String.valueOf(gia.get(position)));
        holder.trangThai.setText(String.valueOf(trangthai.get(position)));
        holder.mainUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(context, SuaPhongActivity.class);
            intent.putExtra("id_phong", idPhong.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ten.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenP, giaP, trangThai;
        LinearLayout mainUpdate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenP = itemView.findViewById(R.id.txtPhong);
            giaP = itemView.findViewById(R.id.txtGia);
            mainUpdate = itemView.findViewById(R.id.mainUpdate);
            trangThai = itemView.findViewById(R.id.txt_trangthai);
        }
    }
}
