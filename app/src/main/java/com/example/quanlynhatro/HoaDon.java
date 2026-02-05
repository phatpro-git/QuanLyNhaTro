package com.example.quanlynhatro;

public class HoaDon {
    private int idHoaDon;
    private int idPhong;
    private String thangNam;
    private int tienPhong;
    private int tienDien;
    private int tienNuoc;
    private int tongTien;
    private String trangThai;

    public HoaDon(int idHoaDon, int idPhong, String thangNam,
                  int tienPhong, int tienDien, int tienNuoc,
                  int tongTien, String trangThai) {
        this.idHoaDon = idHoaDon;
        this.idPhong = idPhong;
        this.thangNam = thangNam;
        this.tienPhong = tienPhong;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getIdHoaDon() { return idHoaDon; }
    public int getIdPhong() { return idPhong; }
    public String getThangNam() { return thangNam; }
    public int getTienPhong() { return tienPhong; }
    public int getTienDien() { return tienDien; }
    public int getTienNuoc() { return tienNuoc; }
    public int getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
}
