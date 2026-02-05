package com.example.quanlynhatro;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.datauser.Mydata;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SuaPhongActivity extends AppCompatActivity {
    TextInputEditText edtTenPhong, edtGiaPhong, edtTrangThai, edtSoDien, edtSoNuoc;
    MaterialButton btnSua, btnXoa, btnThoat, btnXemKhach, btnXemHD, btnTinhtien;
    Button btnXemHDon;
    Mydata mydata;
    int idPhong = -1;
    String trangThai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_phong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        mydata = new Mydata(this);
        idPhong = getIntent().getIntExtra("id_phong", -1);
        if (idPhong == -1) {
            Toast.makeText(this, "Không nhận được ID phòng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadPhong();
        btnXemKhach.setOnClickListener(v -> {
            Mydata db = new Mydata(this);

            int idKhach = db.getKhachIdByPhong(idPhong);

            if (idKhach == -1) {
                //Chưa có khách → sang thêm khách
                int ktr = 2;
                Intent intent = new Intent(SuaPhongActivity.this, AddKhachActivity.class);
                intent.putExtra("id_phong", idPhong);
                intent.putExtra("ktra", ktr);
                startActivity(intent);
            } else {
                //Đã có khách → sang cập nhật khách
                Intent intent = new Intent(SuaPhongActivity.this, SuaKhachActivity.class);
                intent.putExtra("id_khach", idKhach);
                intent.putExtra("id_phong", idPhong);
                startActivity(intent);
            }
        });
        btnXemHD.setOnClickListener(v -> {
            if (!mydata.phongCoHopDong(idPhong)) {
                Toast.makeText(SuaPhongActivity.this, "Phòng chưa có khách thuê", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(SuaPhongActivity.this, ActivityXemHD.class);
            intent.putExtra("id_phong", idPhong);
            startActivity(intent);
        });
        // SỬA
        btnSua.setOnClickListener(v -> suaPhong());
        // XÓA
        btnXoa.setOnClickListener(v -> xoaPhong());
        // THOÁT
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuaPhongActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnTinhtien.setOnClickListener(v -> {

            if (trangThai == null || trangThai.isEmpty()) {
                Toast.makeText(this, "Không xác định được trạng thái phòng", Toast.LENGTH_SHORT).show();
                return;
            }

            // PHÒNG TRỐNG
            if (trangThai.equalsIgnoreCase("Trống")) {
                Toast.makeText(this, "Phòng chưa có khách", Toast.LENGTH_SHORT).show();
                return;
            }
            // PHÒNG ĐÃ THUÊ
            if (trangThai.equalsIgnoreCase("Đã thuê")) {
                Intent intent = new Intent(SuaPhongActivity.this, activityTinhTien.class);
                intent.putExtra("id_phong", idPhong);
                startActivity(intent);
            }
        });
        btnXemHDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaPhongActivity.this, ActivityDanhSachHD.class);
                intent.putExtra("id_phong", idPhong);
                startActivity(intent);

            }
        });
    }
    public  void ax(){
        edtTenPhong = findViewById(R.id.edtTenPhong);
        edtGiaPhong = findViewById(R.id.edtGiaPhong);
        edtTrangThai = findViewById(R.id.edtTrangThai);
        edtSoDien = findViewById(R.id.edtSoDien);
        edtSoNuoc = findViewById(R.id.edtSoNuoc);
        btnTinhtien = findViewById(R.id.btnTinhTien);
        btnXemKhach = findViewById(R.id.btnXemKhach);
        btnXemHD = findViewById(R.id.btnXemHD);
        btnSua = findViewById(R.id.btnThemphong);
        btnXoa = findViewById(R.id.btnXoa);
        btnThoat = findViewById(R.id.btnThoat);
        btnXemHDon = findViewById(R.id.btnXemHDon);
    }
    private void loadPhong() {
        Cursor cursor = mydata.getPhongById(idPhong);
        if (cursor.moveToFirst()) {

            String tenPhong = cursor.getString(cursor.getColumnIndexOrThrow("ten_phong"));
            String giaPhong = cursor.getString(cursor.getColumnIndexOrThrow("gia_phong"));
            trangThai = cursor.getString(cursor.getColumnIndexOrThrow("trang_thai"));
            String soDien = cursor.getString(cursor.getColumnIndexOrThrow("so_dien"));
            String soNuoc = cursor.getString(cursor.getColumnIndexOrThrow("so_nuoc"));

            edtTenPhong.setText(tenPhong);
            edtGiaPhong.setText(giaPhong);
            edtTrangThai.setText(trangThai);
            edtSoDien.setText(soDien);
            edtSoNuoc.setText(soNuoc);

            //ĐỔI TEXT NÚT XEM KHÁCH THEO TRẠNG THÁI
            if (trangThai.equalsIgnoreCase("Trống")) {
                btnXemKhach.setText("THÊM KHÁCH THUÊ");
            } else if (trangThai.equalsIgnoreCase("Đã thuê")) {
                btnXemKhach.setText("XEM KHÁCH THUÊ");
            }
        }
        cursor.close();
    }



    private void suaPhong() {
        String ten = edtTenPhong.getText().toString().trim();
        String giaStr = edtGiaPhong.getText().toString().trim();
        String trangThai = edtTrangThai.getText().toString().trim();
        String dienStr = edtSoDien.getText().toString().trim();
        String nuocStr = edtSoNuoc.getText().toString().trim();

        if (ten.isEmpty() || giaStr.isEmpty() || dienStr.isEmpty() || nuocStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int gia = Integer.parseInt(giaStr);
        int soDien = Integer.parseInt(dienStr);
        int soNuoc = Integer.parseInt(nuocStr);

        boolean ok = mydata.updatePhong(idPhong, ten, gia, soDien, soNuoc, trangThai);

        if (ok) {
            Toast.makeText(this, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    private void xoaPhong() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa phòng")
                .setMessage("Bạn có chắc muốn xóa phòng này?")
                .setPositiveButton("Xóa", (d, w) -> {
                    boolean ok = mydata.deletePhong(idPhong);
                    if (ok) {
                        Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}