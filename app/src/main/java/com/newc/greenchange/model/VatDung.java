package com.newc.greenchange.model;

import java.io.Serializable;

public class VatDung implements Serializable {
//Nguyễn Hoàng Chương
    private int IDVatDung;
    private String TenVatDung;
    private String HinhAnhVatDung;
    private String ChatLieu;
    private String ThongTinGayHai;
    private int MaLoai;

    public VatDung(int IDVatDung, String tenVatDung, String hinhAnhVatDung, String chatLieu, String thongTinGayHai, int maLoai) {
        this.IDVatDung = IDVatDung;
        TenVatDung = tenVatDung;
        HinhAnhVatDung = hinhAnhVatDung;
        ChatLieu = chatLieu;
        ThongTinGayHai = thongTinGayHai;
        MaLoai = maLoai;
    }

    public int getIDVatDung() {
        return IDVatDung;
    }

    public void setIDVatDung(int IDVatDung) {
        this.IDVatDung = IDVatDung;
    }

    public String getTenVatDung() {
        return TenVatDung;
    }

    public void setTenVatDung(String tenVatDung) {
        TenVatDung = tenVatDung;
    }

    public String getHinhAnhVatDung() {
        return HinhAnhVatDung;
    }

    public void setHinhAnhVatDung(String hinhAnhVatDung) {
        HinhAnhVatDung = hinhAnhVatDung;
    }

    public String getChatLieu() {
        return ChatLieu;
    }

    public void setChatLieu(String chatLieu) {
        ChatLieu = chatLieu;
    }

    public String getThongTinGayHai() {
        return ThongTinGayHai;
    }

    public void setThongTinGayHai(String thongTinGayHai) {
        ThongTinGayHai = thongTinGayHai;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }
}
