package com.example.quanlynhatro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.datauser.Mydata;

public class SuaKhachActivity extends AppCompatActivity {

    EditText edtHoTen, edtSDT, edtCCCD, edtQue;
    Button btnCapNhat, btnQuayLai;

    Mydata mydata;
    int idPhong;
    int idKhach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_khach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        mydata = new Mydata(this);
        idPhong = getIntent().getIntExtra("id_phong", -1);
        if (idPhong == -1) {
            Toast.makeText(this, "Không nhận được id phòng", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        loadKhachTheoPhong();
        xuLyCapNhat();
        xuLyQuayLai();
    }
    public void ax(){
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSDT   = findViewById(R.id.edtSDT);
        edtCCCD  = findViewById(R.id.edtCCCD);
        edtQue   = findViewById(R.id.edtQueQuan);

        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnQuayLai = findViewById(R.id.btnQuayLai);
    }
    private void loadKhachTheoPhong() {
        Integer khach = mydata.getIdKhachByIdPhong(idPhong);

        // Phòng CHƯA có khách → sang thêm khách
        if (khach == null) {
            Intent i = new Intent(this, AddKhachActivity.class);
            i.putExtra("id_phong", idPhong);
            startActivity(i);
            finish();
            return;
        }

        idKhach = khach;

        Cursor c = mydata.getKhachById(idKhach);
        if (c.moveToFirst()) {
            edtHoTen.setText(c.getString(c.getColumnIndexOrThrow("hoten")));
            edtSDT.setText(c.getString(c.getColumnIndexOrThrow("sdt")));
            edtCCCD.setText(c.getString(c.getColumnIndexOrThrow("cccd")));
            edtQue.setText(c.getString(c.getColumnIndexOrThrow("quequan")));
        }
        c.close();
    }

    // ================= CẬP NHẬT =================
    private void xuLyCapNhat() {
        btnCapNhat.setOnClickListener(v -> {
            String hoTen = edtHoTen.getText().toString().trim();
            String sdt   = edtSDT.getText().toString().trim();
            String cccd  = edtCCCD.getText().toString().trim();
            String que   = edtQue.getText().toString().trim();

            boolean ok = mydata.updateKhach(idKhach, hoTen, sdt, cccd, que);

            if (ok) {
                Toast.makeText(this, "Cập nhật khách thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ================= QUAY LẠI =================
    private void xuLyQuayLai() {
        btnQuayLai.setOnClickListener(v -> finish());
    }}