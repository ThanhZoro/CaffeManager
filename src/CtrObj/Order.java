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
public class Order {
    private int idOrder;
    private int idUser;
    private int idBan;
    private int idMon;
    private int soLuong;
    private boolean thanhToan;
    private String thoiGianVao;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(boolean thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getThoiGianVao() {
        return thoiGianVao;
    }

    public void setThoiGianVao(String thoiGianVao) {
        this.thoiGianVao = thoiGianVao;
    }

    public Order() {
    }
    
    public Order(int idOrder, int idBan, int idMon, int soLuong) {
        this.idOrder = idOrder;
        this.idBan = idBan;
        this.idMon = idMon;
        this.soLuong = soLuong;
    }

    public Order(int idOrder, int idUser, int idBan, int idMon, int soLuong, boolean thanhToan) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idBan = idBan;
        this.idMon = idMon;
        this.soLuong = soLuong;
        this.thanhToan = thanhToan;
        this.thoiGianVao = null;
    }
    
    public Order(int idOrder, int idUser, int idBan, int idMon, int soLuong, boolean thanhToan, String thoiGianVao) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idBan = idBan;
        this.idMon = idMon;
        this.soLuong = soLuong;
        this.thanhToan = thanhToan;
        this.thoiGianVao = thoiGianVao;
    }

    @Override
    public String toString() {
        return "Order{" + "idOrder=" + idOrder + ", idUser=" + idUser + ", idBan=" + idBan + ", idMon=" + idMon + ", soLuong=" + soLuong + ", thanhToan=" + thanhToan + ", thoiGianVao=" + thoiGianVao + '}';
    }
    
}
