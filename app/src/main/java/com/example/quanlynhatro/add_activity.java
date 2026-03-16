package com.example.quanlynhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.datauser.Mydata;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class add_activity extends AppCompatActivity {
    TextInputEditText edtTenPhong, edtGiaPhong, edtTrangThai, edtSoDien, edtSoNuoc;
    MaterialButton btnTiep, btnThoat, btnXoa, btnThemphong;
    Mydata mydata;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        mydata = new Mydata(this);
        btnXoa.setOnClickListener(v -> {
            edtTenPhong.setText("");
            edtGiaPhong.setText("");
            edtTrangThai.setText("");
            edtSoDien.setText("");
            edtSoNuoc.setText("");
        });
        btnTiep.setOnClickListener(v -> xuLyThemPhongChoKhach());
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(add_activity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnThemphong.setOnClickListener(view -> xuLyThemPhong());
    }
    public  void ax(){
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtGiaPhong = findViewById(R.id.edtGiaPhong);
        edtTrangThai = findViewById(R.id.edtTrangThai);
        btnThemphong = findViewById(R.id.btnThemphong);
        edtSoDien = findViewById(R.id.edtSoDien);
        edtSoNuoc = findViewById(R.id.edtSoNuoc);
        btnTiep = findViewById(R.id.btnTiep);
        btnThoat = findViewById(R.id.btnThoat);
        btnXoa = findViewById(R.id.btnXoa);
    }
    private void xuLyThemPhong() {
        String ten = edtTenPhong.getText().toString().trim();
        String giaStr = edtGiaPhong.getText().toString().trim();
        String soDienStr = edtSoDien.getText().toString().trim();
        String soNuocStr = edtSoNuoc.getText().toString().trim();

        if (ten.isEmpty() || giaStr.isEmpty() || soDienStr.isEmpty() || soNuocStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        int gia, soDien, soNuoc;
        try {
            gia = Integer.parseInt(giaStr);
            soDien = Integer.parseInt(soDienStr);
            soNuoc = Integer.parseInt(soNuocStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        String trangThai = "Trống";
        long idPhong = mydata.addPhong(ten, gia, soDien, soNuoc, trangThai);
        if (idPhong > 0) {
            Toast.makeText(this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();

        } else if (idPhong == -2) {
            Toast.makeText(this, "Tên phòng đã tồn tại!", Toast.LENGTH_SHORT).show();
            edtTenPhong.setText("");
        } else {
            Toast.makeText(this, "Thêm phòng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void xuLyThemPhongChoKhach() {
        String ten = edtTenPhong.getText().toString().trim();
        String giaStr = edtGiaPhong.getText().toString().trim();
        String soDienStr = edtSoDien.getText().toString().trim();
        String soNuocStr = edtSoNuoc.getText().toString().trim();

        if (ten.isEmpty() || giaStr.isEmpty() || soDienStr.isEmpty() || soNuocStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        int gia, soDien, soNuoc;
        try {
            gia = Integer.parseInt(giaStr);
            soDien = Integer.parseInt(soDienStr);
            soNuoc = Integer.parseInt(soNuocStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        String trangThai = "Đã thuê";
        long idPhong = mydata.addPhong(ten, gia, soDien, soNuoc, trangThai);
        if (idPhong > 0) {
            Intent intent = new Intent(this, AddKhachActivity.class);
            intent.putExtra("id_phong", (int) idPhong);
            startActivity(intent);
        } else if (idPhong == -2) {
            Toast.makeText(this, "Tên phòng đã tồn tại!", Toast.LENGTH_SHORT).show();
            edtTenPhong.setText("");
        } else {
            Toast.makeText(this, "Thêm phòng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}