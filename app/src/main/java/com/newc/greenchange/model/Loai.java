package com.newc.greenchange.model;

public class Loai {
//Nguyễn Hoàng Chương
    private int maloai;
    private String tenloai;
    private String hinhanhloai;

    public Loai(int maloai, String tenloai, String hinhanhloai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.hinhanhloai = hinhanhloai;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanhloai() {
        return hinhanhloai;
    }

    public void setHinhanhloai(String hinhanhloai) {
        this.hinhanhloai = hinhanhloai;
    }
}
