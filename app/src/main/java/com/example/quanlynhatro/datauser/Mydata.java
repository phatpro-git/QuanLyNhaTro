package com.example.quanlynhatro.datauser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.quanlynhatro.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class Mydata extends SQLiteOpenHelper {
    private Context context;
    private static final int VERSION = 1;
    private static final String databaseName = "QLNT.db";
    private static final String TB_PHONG = "PHONG";
    private static final String PHONG_ID = "id_phong";
    private static final String PHONG_TEN = "ten_phong";
    private static final String PHONG_GIA = "gia_phong";
    private static final String PHONG_TRANGTHAI = "trang_thai";
    private static final String PHONG_SODIEN = "so_dien";
    private static final String PHONG_SONUOC = "so_nuoc";

    // ===== BẢNG KHÁCH THUÊ =====
    private static final String TB_KHACH = "KHACHTHUE";
    private static final String KHACH_ID = "id_khach";
    private static final String KHACH_HOTEN = "hoten";
    private static final String KHACH_SDT = "sdt";
    private static final String KHACH_CCCD = "cccd";
    private static final String KHACH_QUE = "quequan";

    // ===== BẢNG HỢP ĐỒNG =====
    private static final String TB_HOPDONG = "HOPDONG";
    private static final String HD_ID = "id_hopdong";
    private static final String HD_PHONG = "id_phong";
    private static final String HD_KHACH = "id_khach";
    private static final String HD_NGAYBD = "ngay_bat_dau";
    private static final String HD_NGAYKT = "ngay_ket_thuc";
    private static final String HD_TRANGTHAI = "trang_thai";

    // ===== BẢNG GIÁ ĐIỆN NƯỚC =====
    private static final String TB_GIADN = "GIADIENNUOC";
    private static final String GIA_ID = "id";
    private static final String GIA_DIEN = "gia_dien";
    private static final String GIA_NUOC = "gia_nuoc";

    // ===== BẢNG HÓA ĐƠN =====
    private static final String TB_HOADON = "HOADON";
    private static final String HDON_ID = "id_hoadon";
    private static final String HDON_PHONG = "id_phong";
    private static final String HDON_THANG = "thang_nam";
    private static final String HDON_DIEN = "tien_dien";
    private static final String HDON_NUOC = "tien_nuoc";
    private static final String HDON_PHONGTIEN = "tien_phong";
    private static final String HDON_TONG = "tong_tien";
    private static final String HDON_TRANGTHAI = "trang_thai";

    public Mydata(@Nullable Context context) {
        super(context, databaseName, null, VERSION);
        this.context = context;
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // PHONG
        db.execSQL("CREATE TABLE " + TB_PHONG + " (" +
                PHONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PHONG_TEN + " TEXT NOT NULL UNIQUE, " +
                PHONG_GIA + " INTEGER NOT NULL, " +
                PHONG_SODIEN + " INTEGER DEFAULT 0, " +
                PHONG_SONUOC + " INTEGER DEFAULT 0, " +
                PHONG_TRANGTHAI + " TEXT)");



        // KHÁCH THUÊ
        db.execSQL("CREATE TABLE " + TB_KHACH + " (" +
                KHACH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KHACH_HOTEN + " TEXT NOT NULL, " +
                KHACH_SDT + " TEXT, " +
                KHACH_CCCD + " TEXT, " +
                KHACH_QUE + " TEXT)");
        // HỢP ĐỒNG
        db.execSQL("CREATE TABLE " + TB_HOPDONG + " (" +
                HD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HD_PHONG + " INTEGER NOT NULL, " +
                HD_KHACH + " INTEGER NOT NULL, " +
                HD_NGAYBD + " TEXT, " +
                HD_NGAYKT + " TEXT, " +
                HD_TRANGTHAI + " TEXT, " +

                "FOREIGN KEY(" + HD_PHONG + ") REFERENCES " +
                TB_PHONG + "(" + PHONG_ID + ") ON DELETE CASCADE, " +

                "FOREIGN KEY(" + HD_KHACH + ") REFERENCES " +
                TB_KHACH + "(" + KHACH_ID + ") ON DELETE CASCADE)");
        // GIÁ ĐIỆN NƯỚC
        db.execSQL("CREATE TABLE " + TB_GIADN + " (" +
                GIA_ID + " INTEGER PRIMARY KEY CHECK(" + GIA_ID + "=1), " +
                GIA_DIEN + " INTEGER, " +
                GIA_NUOC + " INTEGER)");
        // HÓA ĐƠN
        db.execSQL("CREATE TABLE " + TB_HOADON + " (" +
                HDON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HDON_PHONG + " INTEGER NOT NULL, " +
                HDON_THANG + " TEXT, " +
                HDON_PHONGTIEN + " INTEGER, " +
                HDON_DIEN + " INTEGER, " +
                HDON_NUOC + " INTEGER, " +
                HDON_TONG + " INTEGER, " +
                HDON_TRANGTHAI + " TEXT, " +
                "FOREIGN KEY(" + HDON_PHONG + ") REFERENCES " +
                TB_PHONG + "(" + PHONG_ID + ") ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_HOADON);
        db.execSQL("DROP TABLE IF EXISTS " + TB_HOPDONG);
        db.execSQL("DROP TABLE IF EXISTS " + TB_GIADN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_KHACH);
        db.execSQL("DROP TABLE IF EXISTS " + TB_PHONG);
        onCreate(db);
    }
    public long addPhong(String tenPhong, int giaPhong, int soDien, int soNuoc, String trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONG_TEN, tenPhong);
        values.put(PHONG_GIA, giaPhong);
        values.put(PHONG_SODIEN, soDien);
        values.put(PHONG_SONUOC, soNuoc);
        values.put(PHONG_TRANGTHAI, trangThai);
        try {
            return db.insertOrThrow(TB_PHONG, null, values);
        } catch (SQLiteConstraintException e) {
            return -2;
        } catch (Exception e) {
            return -1;
        }
    }

    public long addKhach(String hoten, String sdt, String cccd, String que) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KHACH_HOTEN, hoten);
        cv.put(KHACH_SDT, sdt);
        cv.put(KHACH_CCCD, cccd);
        cv.put(KHACH_QUE, que);

        return db.insert(TB_KHACH, null, cv);
    }
    public long addHopDong(int idPhong, int idKhach, String ngayBatDau, String ngayKetThuc, String trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HD_PHONG, idPhong);
        values.put(HD_KHACH, idKhach);
        values.put(HD_NGAYBD, ngayBatDau);
        values.put(HD_NGAYKT, ngayKetThuc);
        values.put(HD_TRANGTHAI, trangThai);

        long result = db.insert(TB_HOPDONG, null, values);
        db.close();
        return result;
    }
    public Cursor readAllData(){
        String query = "SELECT * FROM "+TB_PHONG;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void insertDN(String dien, String nuoc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GIA_DIEN, dien);
        cv.put(GIA_NUOC, nuoc);
        long result = db.insert(TB_GIADN,null,cv);
        if(result == -1 ){
            Toast.makeText(context, "Nhập thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Nhập thành công!", Toast.LENGTH_SHORT).show();
        }
    }
    public void upDateDN(String dien, String nuoc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GIA_DIEN, dien);
        cv.put(GIA_NUOC, nuoc);
        long result = db.update(TB_GIADN,cv, GIA_ID+" = ?",new String[]{String.valueOf("1")});
        if(result == -1 ){
            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor readDN(){
        String query = "SELECT * FROM "+TB_GIADN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    // LẤY PHÒNG THEO ID
    public Cursor getPhongById(int idPhong) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TB_PHONG + " WHERE " + PHONG_ID + "=?",new String[]{String.valueOf(idPhong)});
    }
    // SỬA PHÒNG
    public boolean updatePhong(int idPhong, String ten, int gia, int soDien, int soNuoc, String trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONG_TEN, ten);
        values.put(PHONG_GIA, gia);
        values.put(PHONG_SODIEN, soDien);
        values.put(PHONG_SONUOC, soNuoc);
        values.put(PHONG_TRANGTHAI, trangThai);

        int row = db.update(TB_PHONG, values, PHONG_ID + "=?", new String[]{String.valueOf(idPhong)});
        return row > 0;
    }

    // XÓA PHÒNG
    public boolean deletePhong(int idPhong) {
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TB_PHONG, PHONG_ID + "=?", new String[]{String.valueOf(idPhong)});
        return row > 0;
    }
    public int getKhachIdByPhong(int idPhong) {
        SQLiteDatabase db = this.getReadableDatabase();
        int idKhach = -1;

        Cursor cursor = db.rawQuery(
                "SELECT " + HD_KHACH +
                        " FROM " + TB_HOPDONG +
                        " WHERE " + HD_PHONG + " = ?",
                new String[]{String.valueOf(idPhong)}
        );

        if (cursor.moveToFirst()) {
            idKhach = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return idKhach; // -1 nếu chưa có khách
    }

    // Lấy id_khach theo id_phong (qua hợp đồng)
    public Integer getIdKhachByIdPhong(int idPhong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + HD_KHACH +
                        " FROM " + TB_HOPDONG +
                        " WHERE " + HD_PHONG + "=?",
                new String[]{String.valueOf(idPhong)}
        );

        Integer idKhach = null;
        if (cursor.moveToFirst()) {
            idKhach = cursor.getInt(0);
        }
        cursor.close();
        return idKhach;
    }

    // Lấy thông tin khách theo id_khach
    public Cursor getKhachById(int idKhach) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TB_KHACH +
                        " WHERE " + KHACH_ID + "=?",
                new String[]{String.valueOf(idKhach)}
        );
    }

    // Cập nhật thông tin khách
    public boolean updateKhach(int idKhach, String hoTen, String sdt,String cccd, String queQuan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KHACH_HOTEN, hoTen);
        cv.put(KHACH_SDT, sdt);
        cv.put(KHACH_CCCD, cccd);
        cv.put(KHACH_QUE, queQuan);

        int result = db.update(
                TB_KHACH,
                cv,
                KHACH_ID + "=?",
                new String[]{String.valueOf(idKhach)}
        );
        return result > 0;
    }
    public boolean phongCoHopDong(int idPhong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT 1 FROM HOPDONG WHERE id_phong = ? LIMIT 1",
                new String[]{String.valueOf(idPhong)}
        );
        boolean co = c.moveToFirst();
        c.close();
        return co;
    }
    public Cursor getHopDongByPhong(int idPhong) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TB_HOPDONG + " WHERE " + HD_PHONG + " = ?",
                new String[]{String.valueOf(idPhong)}
        );
    }
    public boolean updateHopDong(int idHopDong, String ngayBatDau, String ngayKetThuc, String trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HD_NGAYBD, ngayBatDau);
        values.put(HD_NGAYKT, ngayKetThuc);
        values.put(HD_TRANGTHAI, trangThai);

        int result = db.update(
                TB_HOPDONG,
                values,
                HD_ID + "=?",
                new String[]{String.valueOf(idHopDong)}
        );

        return result > 0;
    }
    // Cập nhật trạng thái phòng
    public boolean updateTrangThaiPhong(int idPhong, String trangThai) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONG_TRANGTHAI, trangThai);

        int rows = db.update(TB_PHONG, values, PHONG_ID + " = ?", new String[]{String.valueOf(idPhong)});
        return rows > 0;
    }
    public Cursor getGiaDienNuoc() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TB_GIADN + " WHERE " + GIA_ID + "=1",
                null
        );
    }
    public boolean updateSoDienNuoc(int idPhong, int soDienMoi, int soNuocMoi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONG_SODIEN, soDienMoi);
        values.put(PHONG_SONUOC, soNuocMoi);

        int row = db.update(TB_PHONG, values, PHONG_ID + "=?", new String[]{String.valueOf(idPhong)});
        return row > 0;
    }
    public boolean insertHoaDon(int idPhong, String thangNam, int tienPhong, int tienDien, int tienNuoc, int tongTien) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HDON_PHONG, idPhong);
        cv.put(HDON_THANG, thangNam);
        cv.put(HDON_PHONGTIEN, tienPhong);
        cv.put(HDON_DIEN, tienDien);
        cv.put(HDON_NUOC, tienNuoc);
        cv.put(HDON_TONG, tongTien);
        cv.put(HDON_TRANGTHAI, "Chưa thanh toán");

        long r = db.insert(TB_HOADON, null, cv);
        return r != -1;
    }
    public List<HoaDon> getHoaDonTheoPhong(int idPhong) {
        List<HoaDon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_HOADON + " WHERE " + HDON_PHONG + " = ?" + " ORDER BY " + HDON_THANG + " DESC", new String[]{String.valueOf(idPhong)});
        if (cursor.moveToFirst()) {
            do {
                list.add(new HoaDon(
                        cursor.getInt(cursor.getColumnIndexOrThrow(HDON_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(HDON_PHONG)),
                        cursor.getString(cursor.getColumnIndexOrThrow(HDON_THANG)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(HDON_PHONGTIEN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(HDON_DIEN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(HDON_NUOC)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(HDON_TONG)),
                        cursor.getString(cursor.getColumnIndexOrThrow(HDON_TRANGTHAI))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public HoaDon getHoaDonById(int idHoaDon) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM HOADON WHERE id_hoadon = ?", new String[]{String.valueOf(idHoaDon)});
        HoaDon hd = null;
        if (c.moveToFirst()) {
            hd = new HoaDon(
                    c.getInt(c.getColumnIndexOrThrow("id_hoadon")),
                    c.getInt(c.getColumnIndexOrThrow("id_phong")),
                    c.getString(c.getColumnIndexOrThrow("thang_nam")),
                    c.getInt(c.getColumnIndexOrThrow("tien_phong")),
                    c.getInt(c.getColumnIndexOrThrow("tien_dien")),
                    c.getInt(c.getColumnIndexOrThrow("tien_nuoc")),
                    c.getInt(c.getColumnIndexOrThrow("tong_tien")),
                    c.getString(c.getColumnIndexOrThrow("trang_thai"))
            );
        }
        c.close();
        return hd;
    }
    public void updateTrangThaiHoaDon(int idHoaDon, String trangThai) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("trang_thai", trangThai);
        db.update("HOADON", cv, "id_hoadon = ?", new String[]{String.valueOf(idHoaDon)});
    }
    public void deleteHoaDon(int idHoaDon) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("HOADON", "id_hoadon = ?", new String[]{String.valueOf(idHoaDon)});
    }

}
