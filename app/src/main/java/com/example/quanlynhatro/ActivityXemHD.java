package com.example.quanlynhatro;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.DateTimePicker;
import com.example.quanlynhatro.datauser.Mydata;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ActivityXemHD extends AppCompatActivity {
    TextInputEditText edtNgayBD, edtNgayKT, edtTrangThai;
    MaterialButton btnCapNhat, btnQuayLai;

    Mydata db;
    int idPhong;
    int idHopDong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xem_hd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        db = new Mydata(this);
        idPhong = getIntent().getIntExtra("id_phong", -1);
        if (idPhong == -1) {
            Toast.makeText(this, "Không nhận được ID phòng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadHopDong();
        edtNgayBD.setOnClickListener(view -> DateTimePicker.showDatePicker(this, edtNgayBD));
        edtNgayKT.setOnClickListener(view -> DateTimePicker.showDatePicker(this, edtNgayKT));
        btnCapNhat.setOnClickListener(v -> updateHopDong());

        btnQuayLai.setOnClickListener(v -> finish());
    }
    public void ax(){
        edtNgayBD = findViewById(R.id.edtNgayBatDau);
        edtNgayKT = findViewById(R.id.edtNgayKetThuc);
        edtTrangThai = findViewById(R.id.edtTrangThaiHD);
        btnCapNhat = findViewById(R.id.btnCapNhatHD);
        btnQuayLai = findViewById(R.id.btnQuayLai);
    }
    private void loadHopDong() {
        Cursor cursor = db.getHopDongByPhong(idPhong);

        if (cursor != null && cursor.moveToFirst()) {
            idHopDong = cursor.getInt(
                    cursor.getColumnIndexOrThrow("id_hopdong")
            );

            edtNgayBD.setText(
                    cursor.getString(cursor.getColumnIndexOrThrow("ngay_bat_dau"))
            );
            edtNgayKT.setText(
                    cursor.getString(cursor.getColumnIndexOrThrow("ngay_ket_thuc"))
            );
            edtTrangThai.setText(
                    cursor.getString(cursor.getColumnIndexOrThrow("trang_thai"))
            );
        } else {
            Toast.makeText(this, "Phòng chưa có hợp đồng", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (cursor != null) cursor.close();
    }
    private void updateHopDong() {
        String ngayBD = edtNgayBD.getText().toString().trim();
        String ngayKT = edtNgayKT.getText().toString().trim();
        String trangThai = edtTrangThai.getText().toString().trim();

        boolean success = db.updateHopDong(idHopDong, ngayBD, ngayKT, trangThai);
        if (success) {
            Toast.makeText(this, "Cập nhật hợp đồng thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}