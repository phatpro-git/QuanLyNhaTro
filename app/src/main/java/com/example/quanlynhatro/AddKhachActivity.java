package com.example.quanlynhatro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.datauser.Mydata;
import com.google.android.material.textfield.TextInputEditText;

public class AddKhachActivity extends AppCompatActivity {
    TextInputEditText edtHoTen, edtSdt, edtCccd, edtQueQuan;
    Button btnXoa, btnTiep, btnQuaylai;
    Mydata mydata;

    Mydata myData;
    int idPhong, ktr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_khach);
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

        // NÚT XÓA
        btnXoa.setOnClickListener(v -> {
            edtHoTen.setText("");
            edtSdt.setText("");
            edtCccd.setText("");
            edtQueQuan.setText("");
        });

        // NÚT TIẾP
        btnTiep.setOnClickListener(v -> xuLyThemKhach());
        ktr = getIntent().getIntExtra("ktra", -1);
        btnQuaylai.setVisibility(View.GONE);
        if (ktr == 2) {
            btnQuaylai.setVisibility(View.VISIBLE);
        }
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void ax(){
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSdt = findViewById(R.id.edtSdt);
        edtCccd = findViewById(R.id.edtCccd);
        edtQueQuan = findViewById(R.id.edtQueQuan);
        btnXoa = findViewById(R.id.btnXoaKhach);
        btnTiep = findViewById(R.id.btnTiepKhach);
        btnQuaylai = findViewById(R.id.btnQuayLai);
    }
    private void xuLyThemKhach() {
        String hoten = edtHoTen.getText().toString().trim();
        String sdt = edtSdt.getText().toString().trim();
        String cccd = edtCccd.getText().toString().trim();
        String que = edtQueQuan.getText().toString().trim();

        if (hoten.isEmpty() || sdt.isEmpty() || cccd.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // INSERT KHÁCH
        long idKhach = mydata.addKhach(hoten, sdt, cccd, que);

        if (idKhach > 0) {
            // 👉 CHUYỂN SANG ADD HỢP ĐỒNG
            Intent intent = new Intent(this, AddHopDongActivity.class);
            intent.putExtra("id_phong", idPhong);
            intent.putExtra("id_khach", (int) idKhach);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Thêm khách thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}