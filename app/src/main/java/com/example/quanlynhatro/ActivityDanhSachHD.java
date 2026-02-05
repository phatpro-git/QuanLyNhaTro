package com.example.quanlynhatro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhatro.datauser.Mydata;

import java.util.List;

public class ActivityDanhSachHD extends AppCompatActivity {
    RecyclerView rvHoaDon;
    HoaDonAdapter adapter;
    Button btnQuaylai;
    Mydata mydata;
    List<HoaDon> hoaDonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acrivity_danh_sach_hd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        int idPhong = getIntent().getIntExtra("id_phong", -1);
        if (idPhong == -1) {
            Toast.makeText(this, "Không nhận được ID phòng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mydata = new Mydata(this);
        hoaDonList = mydata.getHoaDonTheoPhong(idPhong);

        // 4. Gắn adapter
        adapter = new HoaDonAdapter(this, hoaDonList);
        rvHoaDon.setAdapter(adapter);
        btnQuaylai.setOnClickListener(view -> finish());
    }
    public void ax(){
        rvHoaDon = findViewById(R.id.rvHoaDon);
        rvHoaDon.setLayoutManager(new LinearLayoutManager(this));
        btnQuaylai = findViewById(R.id.btnQuaylai);
    }
}