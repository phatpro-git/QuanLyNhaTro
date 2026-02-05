package com.example.quanlynhatro;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlynhatro.datauser.Mydata;

public class ActivityChiTietHoaDon extends AppCompatActivity {
    TextView tvThangNam, tvTienPhong, tvTienDien, tvTienNuoc, tvTongTien;
    Spinner spTrangThai;
    Button btnCapNhat, btnXoa, btnQuayLai;

    Mydata mydata;
    HoaDon hoaDon;
    int idHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        mydata = new Mydata(this);

        // Nhận ID hóa đơn
        idHoaDon = getIntent().getIntExtra("idHoaDon", -1);
        if (idHoaDon == -1) {
            finish();
            return;
        }
        hoaDon = mydata.getHoaDonById(idHoaDon);
        if (hoaDon == null) {
            finish();
            return;
        }

        // Hiển thị dữ liệu
        tvThangNam.setText("Tháng: " + hoaDon.getThangNam());
        tvTienPhong.setText("Tiền phòng: " + formatTien(hoaDon.getTienPhong()));
        tvTienDien.setText("Tiền điện: " + formatTien(hoaDon.getTienDien()));
        tvTienNuoc.setText("Tiền nước: " + formatTien(hoaDon.getTienNuoc()));
        tvTongTien.setText("TỔNG TIỀN: " + formatTien(hoaDon.getTongTien()));

        // Spinner trạng thái
        String[] arrTrangThai = {"Chưa thanh toán", "Đã thanh toán"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arrTrangThai
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTrangThai.setAdapter(adapter);

        // Set trạng thái hiện tại
        if ("Đã thanh toán".equals(hoaDon.getTrangThai())) {
            spTrangThai.setSelection(1);
        } else {
            spTrangThai.setSelection(0);
        }

        // Cập nhật trạng thái
        btnCapNhat.setOnClickListener(v -> {
            String trangThaiMoi = spTrangThai.getSelectedItem().toString();
            mydata.updateTrangThaiHoaDon(idHoaDon, trangThaiMoi);
            Toast.makeText(this, "Đã cập nhật trạng thái", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, ActivityDanhSachHD.class);
            i.putExtra("idHoaDon", idHoaDon);
            startActivity(i);
        });

        // Xóa hóa đơn
        btnXoa.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa hóa đơn này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        mydata.deleteHoaDon(idHoaDon);
                        Toast.makeText(this, "Đã xóa hóa đơn", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
        btnQuayLai.setOnClickListener(view -> finish());
    }

    private String formatTien(int tien) {
        return String.format("%,d", tien) + " đ";
    }
    public void ax(){
        tvThangNam = findViewById(R.id.tvThangNam);
        tvTienPhong = findViewById(R.id.tvTienPhong);
        tvTienDien = findViewById(R.id.tvTienDien);
        tvTienNuoc = findViewById(R.id.tvTienNuoc);
        tvTongTien = findViewById(R.id.tvTongTien);
        spTrangThai = findViewById(R.id.spTrangThai);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        btnXoa = findViewById(R.id.btnXoa);
        btnQuayLai = findViewById(R.id.btnQuaylai);
    }
}