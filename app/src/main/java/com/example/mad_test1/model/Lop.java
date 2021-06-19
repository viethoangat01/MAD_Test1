package com.example.mad_test1.model;

public class Lop {
    private int malop;
    private String ten;
    private String mota;

    public Lop() {
    }

    public Lop(int malop, String ten, String mota) {
        this.malop = malop;
        this.ten = ten;
        this.mota = mota;
    }

    public int getMalop() {
        return malop;
    }

    public void setMalop(int malop) {
        this.malop = malop;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
