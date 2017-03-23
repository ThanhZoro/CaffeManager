/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrDatabase;

import CtrObj.Ban;
import CtrObj.Mon;
import CtrObj.User;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zoro
 */
public class DBConnection {

    public static String Driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/coffee_management";
    public static String user = "root";
    public static String pass = "";

    //Mở kết nối
    public static Connection OpenDB() throws ClassNotFoundException, SQLException {
        
        Class.forName(Driver);
        return DriverManager.getConnection(url + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&user=" + user + "&password=" + pass);
    }

    //Đóng kết nối
    public static void Close(ResultSet rs, PreparedStatement ps, Connection cnn) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }

        if (ps != null && !ps.isClosed()) {
            ps.close();
        }

        if (cnn != null && !cnn.isClosed()) {
            cnn.close();
        }
    }

    //Lấy User để kiểm tra => Sử dụng cho Frame_Login
    public static User getUser(String name, String pass) throws ClassNotFoundException, SQLException, Exception {
        User user;
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "Select * from users where Username = ? and Password = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, pass);
            rs = ps.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("Username");
                String passWord = rs.getString("Password");
                int role = rs.getInt("Role");

                user = new User(userName, passWord, role);

                Close(rs, ps, cnn);
                return user;
            } else {
                throw new Exception("Vui lòng kiểm tra lại tên tài khoản hoặc mật khẩu!");
            }
        }
        return null;
    }

    //Lấy tổng số bàn trong quán
    public static int getSoBan() throws ClassNotFoundException, SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT COUNT(IDtable) as SoBan FROM `tables`";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                int soBan = rs.getInt("SoBan");
                Close(rs, ps, cnn);

                return soBan;
            }
        }
        return 0;
    }

    //Lấy số bàn đang được phục vụ
    public static int getSoBanActive() throws ClassNotFoundException, SQLException, Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT COUNT(IDtable) as SoBanActive FROM `tables` WHERE Trangthai = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, "1"); //Trạng thái active bằng 1
            rs = ps.executeQuery();

            if (rs.next()) {
                int soBanActive = rs.getInt("SoBanActive");
                Close(rs, ps, cnn);

                return soBanActive;
            }
        }
        return 0;
    }

    //(Viết thừa) //Lấy danh sách các bàn đang được hoạt động
    public static ArrayList<Ban> getBanActive() throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Ban> listBanActive = new ArrayList<>();
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "Select * from tables where Trangthai = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, "1"); //Trạng thái active bằng 1
            rs = ps.executeQuery();

            while (rs.next()) {
                int maBan = rs.getInt("MaBan");
                int khuVuc = rs.getInt("KhuVuc");
                int trangThai = rs.getInt("Trangthai");

                listBanActive.add(new Ban(maBan, khuVuc, trangThai));
            }

            Close(rs, ps, cnn);
            return listBanActive;
        }
        return null;
    }

    //Kiểm tra bàn nào đó có đang được phục vụ không
    public static boolean checkActiveBan(int MABAN) throws ClassNotFoundException, SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "Select * from tables where Trangthai = ? and MaBan = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, 1); //Trạng thái active bằng 1
            ps.setInt(2, MABAN);
            rs = ps.executeQuery();

            if (rs.next()) {
                Close(rs, ps, cnn);

                return true;
            }
        }

        return false;
    }

    //Cập nhật lại trạng thái cho bàn nào đó
    public static boolean updateActiveBan(int MABAN, int TRANGTHAI) throws ClassNotFoundException, SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "UPDATE tables SET Trangthai = ? WHERE MaBan = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, TRANGTHAI);
            ps.setInt(2, MABAN);
            int rowEff = ps.executeUpdate();
            if (rowEff > 0) {
                Close(null, ps, cnn);
                return true;
            }
        }
        return false;
    }
    
    //Lấy danh sách các Nhóm món
    public static ArrayList<String> getListNhomMon() throws ClassNotFoundException, SQLException, Exception {
        ArrayList<String> listNhomMon = new ArrayList<>();
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT `Loai` FROM `monan` GROUP BY `Loai`";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tenNhom = rs.getString("Loai");
                listNhomMon.add(tenNhom);
            }

            Close(rs, ps, cnn);
            return listNhomMon;
        }
        return null;
    }
    
    //Lấy danh sách các Nhóm món
    public static ArrayList<Mon> getListMon(String NHOMMON) throws ClassNotFoundException, SQLException, Exception {
        ArrayList<Mon> listMon = new ArrayList<>();
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT * FROM `monan` WHERE `Loai` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1,NHOMMON);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tenMon = rs.getString("Tenmonan");
                int giaTien = rs.getInt("GiaTien");
                String loai = rs.getString("Loai");
                listMon.add(new Mon(tenMon, giaTien, loai));
            }

            Close(rs, ps, cnn);
            return listMon;
        }
        return null;
    }

    //Lấy thông tin một món ăn
    public static Mon getMon(String TENMON, String NHOMMON) throws ClassNotFoundException, SQLException, Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT * FROM `monan` WHERE `Tenmonan` = ? AND `Loai` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1,TENMON);
            ps.setString(2,NHOMMON);
            rs = ps.executeQuery();

            if (rs.next()) {
                String tenMon = rs.getString("Tenmonan");
                int giaTien = rs.getInt("GiaTien");
                String loai = rs.getString("Loai");
                Close(rs, ps, cnn);
                return new Mon(tenMon, giaTien, loai);
            }
        }
        return null;
    }
}
