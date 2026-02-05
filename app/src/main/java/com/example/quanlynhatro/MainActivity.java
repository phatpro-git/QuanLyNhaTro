package com.example.quanlynhatro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatDelegate;


import com.example.quanlynhatro.datauser.Mydata;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    FloatingActionButton add;
    Button giaDN, tinh;
    EditText edtdien, edtnuoc;
    Mydata mydb;
    LinearLayout viewDN;
    ArrayList<String> ten, trangthai, tenFull, trangthaiFull;
    ArrayList<Integer> gia, idPhong, giaFull, idPhongFull;
    recleview recleview;
    ImageView empty;
    TextView no_data;
    EditText textSearch;
    ImageButton search1, search2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ax();
        mydb = new Mydata(MainActivity.this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, add_activity.class);
                startActivity(i);
            }
        });
        idPhong = new ArrayList<>();
        ten = new ArrayList<>();
        gia = new ArrayList<>();
        trangthai = new ArrayList<>();

        showAlldb();
        idPhongFull = new ArrayList<>(idPhong);
        tenFull = new ArrayList<>(ten);
        giaFull = new ArrayList<>(gia);
        trangthaiFull = new ArrayList<>(trangthai);

        recleview = new recleview(MainActivity.this,idPhong, ten, gia, trangthai);
        list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        list.setAdapter(recleview);
        giaDN.setOnClickListener(v -> {
            if (viewDN.getVisibility() == View.GONE) {
                viewDN.setVisibility(View.VISIBLE);// hiện form
                showDataDN();
            } else {
                viewDN.setVisibility(View.GONE);    // ẩn lại nếu đã hiện
            }
        });
        tinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String giaD = edtdien.getText().toString().trim();
                String giaN = edtnuoc.getText().toString().trim();
                mydb.upDateDN(giaD,giaN);
                viewDN.setVisibility(View.GONE);
            }
        });
        search1.setOnClickListener(v -> {
            if (textSearch.getVisibility() == View.GONE) {
                textSearch.setVisibility(View.VISIBLE);
                textSearch.requestFocus();
            } else {
                textSearch.setText("");
                textSearch.setVisibility(View.GONE);

                // reset danh sách
                resetData();
            }
        });
        textSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPhong(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

    }
    public void ax(){
        list = findViewById(R.id.recyclerview);
        add = findViewById(R.id.btn_add);
        giaDN = findViewById(R.id.btngdn);
        viewDN = findViewById(R.id.formLayout);
        tinh = findViewById(R.id.btnLuu);
        edtdien = findViewById(R.id.edtdien);
        edtnuoc = findViewById(R.id.edtnuoc);
        empty = findViewById(R.id.empty);
        no_data = findViewById(R.id.no_data);
        textSearch = findViewById(R.id.edtSearch);
        search1 = findViewById(R.id.btnSearch);
        //search2 = findViewById(R.id.search2);
    }
    void showDataDN(){
        Cursor cursor = mydb.readDN();
        if(cursor.moveToFirst()==false) {
            edtdien.setText("3000");
            edtnuoc.setText("10000");
            mydb.insertDN("3000","10000");
        }else{
            edtdien.setText(cursor.getString(1));
            edtnuoc.setText(cursor.getString(2));
        }
    }
    void showAlldb(){
        Cursor cursor = mydb.readAllData();
        if(cursor.getCount()==0){
            empty.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                idPhong.add(cursor.getInt(0));
                ten.add(cursor.getString(1));
                gia.add(cursor.getInt(2));
                trangthai.add(cursor.getString(5));
            }
        }
    }
    void filterPhong(String keyword) {
        idPhong.clear();
        ten.clear();
        gia.clear();
        trangthai.clear();

        if (keyword.isEmpty()) {
            resetData();
            return;
        }

        for (int i = 0; i < tenFull.size(); i++) {
            if (tenFull.get(i).toLowerCase().contains(keyword.toLowerCase())) {
                idPhong.add(idPhongFull.get(i));
                ten.add(tenFull.get(i));
                gia.add(giaFull.get(i));
                trangthai.add(trangthaiFull.get(i));
            }
        }

        recleview.notifyDataSetChanged();
    }
    void resetData() {
        idPhong.clear();
        ten.clear();
        gia.clear();
        trangthai.clear();

        idPhong.addAll(idPhongFull);
        ten.addAll(tenFull);
        gia.addAll(giaFull);
        trangthai.addAll(trangthaiFull);

        recleview.notifyDataSetChanged();
    }

}