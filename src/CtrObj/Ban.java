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
public class Ban {
    private int maBan;
    private int khuVuc;
    private int trangThai;

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(int khuVuc) {
        this.khuVuc = khuVuc;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Ban() {
    }

    public Ban(int maBan, int khuVuc, int trangThai) {
        this.maBan = maBan;
        this.khuVuc = khuVuc;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Ban{" + "maBan=" + maBan + ", khuVuc=" + khuVuc + ", trangThai=" + trangThai + '}';
    }
    
    
}
