package com.newc.greenchange.model;

import java.io.Serializable;

public class TaiKhoan implements Serializable {
    public static int idtaikhoan;
    private String tentaikhoan;
    private String matkhau;
    private String hoten;
    private String ngaysinh;
    private int sodt;
    private String email;
    private String tinhtp;

    public TaiKhoan(int idtaikhoan, String tentaikhoan, String matkhau, String hoten, String ngaysinh, int sodt, String email, String tinhtp) {
        this.idtaikhoan = idtaikhoan;
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sodt = sodt;
        this.email = email;
        this.tinhtp = tinhtp;
    }

    public int getIdtaikhoan() {
        return idtaikhoan;
    }

    public void setIdtaikhoan(int idtaikhoan) {
        this.idtaikhoan = idtaikhoan;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public int getSodt() {
        return sodt;
    }

    public void setSodt(int sodt) {
        this.sodt = sodt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTinhtp() {
        return tinhtp;
    }

    public void setTinhtp(String tinhtp) {
        this.tinhtp = tinhtp;
    }
}
