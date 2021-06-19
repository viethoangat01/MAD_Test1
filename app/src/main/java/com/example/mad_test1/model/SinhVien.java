package com.example.mad_test1.model;

public class SinhVien {
    private int masv;
    private String hoten;
    private int namsinh;
    private String quequan,namhoc;

    public SinhVien() {
    }

    public SinhVien(int masv, String hoten, int namsinh, String quequan, String namhoc) {
        this.masv = masv;
        this.hoten = hoten;
        this.namsinh = namsinh;
        this.quequan = quequan;
        this.namhoc = namhoc;
    }

    public int getmasv() {
        return masv;
    }

    public void setmasv(int masv) {
        this.masv = masv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getNamhoc() {
        return namhoc;
    }

    public void setNamhoc(String namhoc) {
        this.namhoc = namhoc;
    }
}
