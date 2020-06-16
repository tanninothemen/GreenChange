package com.newc.greenchange.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int IDSanPham;
    private String TenSanPham;
    private String HinhAnhSanPham;
    private String ChatLieu;
    private String ThongTinSanPham;
    private String DuongLink;
    private int MaLoai;

    public SanPham(int IDSanPham, String tenSanPham, String hinhAnhSanPham, String chatLieu, String thongTinSanPham, String duongLink, int maLoai) {
        this.IDSanPham = IDSanPham;
        TenSanPham = tenSanPham;
        HinhAnhSanPham = hinhAnhSanPham;
        ChatLieu = chatLieu;
        ThongTinSanPham = thongTinSanPham;
        DuongLink = duongLink;
        MaLoai = maLoai;
    }

    public int getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(int IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getHinhAnhSanPham() {
        return HinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        HinhAnhSanPham = hinhAnhSanPham;
    }

    public String getChatLieu() {
        return ChatLieu;
    }

    public void setChatLieu(String chatLieu) {
        ChatLieu = chatLieu;
    }

    public String getThongTinSanPham() {
        return ThongTinSanPham;
    }

    public void setThongTinSanPham(String thongTinSanPham) {
        ThongTinSanPham = thongTinSanPham;
    }

    public String getDuongLink() {
        return DuongLink;
    }

    public void setDuongLink(String duongLink) {
        DuongLink = duongLink;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }
}
