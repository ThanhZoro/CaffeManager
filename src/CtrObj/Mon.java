/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrObj;

/**
 *
 * @author Zoro
 */
public class Mon {
    private String tenMon;
    private int giaTien;
    private String loai;
    private int soLuong;

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Mon() {
    }

    public Mon(String tenMon, int giaTien, String loai) {
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.loai = loai;
        this.soLuong = 1;
    }

    @Override
    public String toString() {
        return "Mon{" + "tenMon=" + tenMon + ", giaTien=" + giaTien + ", loai=" + loai + ", soLuong=" + soLuong + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Mon)
        {
            if(((this.getTenMon()).equals(((Mon) obj).getTenMon())) && ((this.getLoai()).equals(((Mon) obj).getLoai())))
            {
                return true;
            }
        }
        return false;
    }

    
}
