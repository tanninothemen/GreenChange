package com.newc.greenchange.model;

public class DanhGia {
    private int idtaikhoan;
    private int idsanpham;
    private String noidungdanhgia;
    private int iddanhgia;
    private String tentaikhoan;

    public DanhGia(int idtaikhoan, int idsanpham, String noidungdanhgia, int iddanhgia, String tentaikhoan) {
        this.idtaikhoan = idtaikhoan;
        this.idsanpham = idsanpham;
        this.noidungdanhgia = noidungdanhgia;
        this.iddanhgia = iddanhgia;
        this.tentaikhoan = tentaikhoan;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getNoidungdanhgia() {
        return noidungdanhgia;
    }

    public void setNoidungdanhgia(String noidungdanhgia) {
        this.noidungdanhgia = noidungdanhgia;
    }

    public int getIddanhgia() {
        return iddanhgia;
    }

    public void setIddanhgia(int iddanhgia) {
        this.iddanhgia = iddanhgia;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }
}
