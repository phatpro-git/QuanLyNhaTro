package com.example.quanlynhatro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.datauser.Mydata;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activityTinhTien extends AppCompatActivity {
    TextView tvGiaPhong, tvSoDienCu, tvSoNuocCu, tvTongTien, tvTienDien, tvTienNuoc;
    TextInputEditText edtSoDienMoi, edtSoNuocMoi;
    MaterialButton btnTinhTien, btnXuatHoaDon, btnQuailai;

    Mydata myData;

    int idPhong;
    int giaPhong, soDienCu, soNuocCu;
    int giaDien, giaNuoc;

    int tienDien, tienNuoc, tongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tinh_tien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        myData = new Mydata(this);
        // ===== NHẬN ID PHÒNG =====
        idPhong = getIntent().getIntExtra("id_phong", -1);
        if (idPhong == -1) {
            Toast.makeText(this, "Không nhận được ID phòng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadPhong();
        loadGiaDienNuoc();

        btnTinhTien.setOnClickListener(v -> tinhTien());
        btnXuatHoaDon.setOnClickListener(v -> xuatHoaDon());
        btnQuailai.setOnClickListener(view -> finish());
    }
    public void ax(){
        tvGiaPhong = findViewById(R.id.tvGiaPhong);
        tvSoDienCu = findViewById(R.id.tvSoDienCu);
        tvSoNuocCu = findViewById(R.id.tvSoNuocCu);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvTienDien = findViewById(R.id.tvTienDien);
        tvTienNuoc = findViewById(R.id.tvTienNuoc);

        edtSoDienMoi = findViewById(R.id.edtSoDienMoi);
        edtSoNuocMoi = findViewById(R.id.edtSoNuocMoi);

        btnQuailai = findViewById(R.id.btnQuaylai);
        btnTinhTien = findViewById(R.id.btnTinhTien);
        btnXuatHoaDon = findViewById(R.id.btnXuatHoaDon);
    }
    private void loadPhong() {
        Cursor c = myData.getPhongById(idPhong);
        if (c.moveToFirst()) {
            giaPhong = c.getInt(c.getColumnIndexOrThrow("gia_phong"));
            soDienCu = c.getInt(c.getColumnIndexOrThrow("so_dien"));
            soNuocCu = c.getInt(c.getColumnIndexOrThrow("so_nuoc"));

            tvGiaPhong.setText("Giá phòng: " + giaPhong + " VNĐ");
            tvSoDienCu.setText("Số điện cũ: " + soDienCu);
            tvSoNuocCu.setText("Số nước cũ: " + soNuocCu);
        }
        c.close();
    }

    private void loadGiaDienNuoc() {
        Cursor c = myData.getGiaDienNuoc();

        if (c == null || !c.moveToFirst()) {
            Toast.makeText(this,
                    "Chưa cài giá điện nước.\nVui lòng cài trước!",
                    Toast.LENGTH_LONG).show();
            if (c != null) c.close();
            // QUAY THẲNG VỀ MAIN, XOÁ STACK
            Intent intent = new Intent(activityTinhTien.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        giaDien = c.getInt(c.getColumnIndexOrThrow("gia_dien"));
        giaNuoc = c.getInt(c.getColumnIndexOrThrow("gia_nuoc"));

        c.close();
    }

    private void tinhTien() {
        if (edtSoDienMoi.getText().toString().isEmpty() || edtSoNuocMoi.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nhập số điện và nước mới", Toast.LENGTH_SHORT).show();
            return;
        }
        int soDienMoi = Integer.parseInt(edtSoDienMoi.getText().toString());
        int soNuocMoi = Integer.parseInt(edtSoNuocMoi.getText().toString());
        if (soDienMoi < soDienCu || soNuocMoi < soNuocCu) {
            Toast.makeText(this, "Số mới không được nhỏ hơn số cũ", Toast.LENGTH_SHORT).show();
            return;
        }
        tienDien = (soDienMoi - soDienCu) * giaDien;
        tienNuoc = (soNuocMoi - soNuocCu) * giaNuoc;
        tongTien = giaPhong + tienDien + tienNuoc;
        tvTienDien.setText("Tiền điện: " + tienDien + " VNĐ");
        tvTienNuoc.setText("Tiền nước: " + tienNuoc + " VNĐ");
        tvTongTien.setText("TỔNG TIỀN: " + tongTien + " VNĐ");
    }
    private void xuatHoaDon() {
        if (tongTien == 0) {
            Toast.makeText(this, "Vui lòng tính tiền trước", Toast.LENGTH_SHORT).show();
            return;
        }
        int soDienMoi = Integer.parseInt(edtSoDienMoi.getText().toString());
        int soNuocMoi = Integer.parseInt(edtSoNuocMoi.getText().toString());
        String thangNam = new SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(new Date());

        boolean ok1 = myData.insertHoaDon(idPhong, thangNam, giaPhong, tienDien, tienNuoc, tongTien);
        boolean ok2 = myData.updateSoDienNuoc(idPhong, soDienMoi, soNuocMoi);

        if (ok1 && ok2) {
            Toast.makeText(this, "Xuất hóa đơn thành công", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SuaPhongActivity.class);
            i.putExtra("id_phong", idPhong);
            startActivity(i);
        } else {
            Toast.makeText(this, "Lỗi khi xuất hóa đơn", Toast.LENGTH_SHORT).show();
        }
    }

}