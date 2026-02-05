package com.example.quanlynhatro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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

import java.util.Calendar;

public class AddHopDongActivity extends AppCompatActivity {
    TextInputEditText edtNgayBatDau, edtNgayKetThuc, edtTrangThai;
    MaterialButton btnTiep, btnXoa;

    Mydata mydata;

    int idPhong, idKhach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_hop_dong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        mydata = new Mydata(this);

        // Nhận id từ Intent
        idPhong = getIntent().getIntExtra("id_phong", -1);
        idKhach = getIntent().getIntExtra("id_khach", -1);

        if (idPhong == -1 || idKhach == -1) {
            Toast.makeText(this, "Thiếu dữ liệu phòng hoặc khách", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        edtNgayBatDau.setOnClickListener(v -> DateTimePicker.showDatePicker(this, edtNgayBatDau));
        edtNgayKetThuc.setOnClickListener(v -> DateTimePicker.showDatePicker(this, edtNgayKetThuc));
        // Xoa dữ liệu
        btnXoa.setOnClickListener(v -> {
            edtNgayBatDau.setText("");
            edtNgayKetThuc.setText("");
            edtTrangThai.setText("");
        });
        // lưu hợp đồng
        btnTiep.setOnClickListener(v -> {
            String ngayBD = edtNgayBatDau.getText().toString().trim();
            String ngayKT = edtNgayKetThuc.getText().toString().trim();
            String trangThai = edtTrangThai.getText().toString().trim();

            if (ngayBD.isEmpty() || ngayKT.isEmpty() || trangThai.isEmpty()) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = mydata.addHopDong(idPhong, idKhach, ngayBD, ngayKT, trangThai);

            if (result > 0) {
                // CẬP NHẬT TRẠNG THÁI PHÒNG = ĐÃ THUÊ
                boolean ok = mydata.updateTrangThaiPhong(idPhong, "Đã thuê");
                if (ok) {
                    Toast.makeText(this, "Thêm hợp đồng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Hợp đồng OK nhưng cập nhật trạng thái phòng thất bại", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(AddHopDongActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Thêm hợp đồng thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void ax(){
        edtNgayBatDau = findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = findViewById(R.id.edtNgayKetThuc);
        edtTrangThai = findViewById(R.id.edtTrangThai);
        btnTiep = findViewById(R.id.btnTiep);
        btnXoa = findViewById(R.id.btnXoa);
    }
    /*public void showDatePicker(TextInputEditText editText) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    String date = d + "/" + (m + 1) + "/" + y;
                    editText.setText(date);
                },
                year, month, day
        );
        dialog.show();
    }*/
}