/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrDatabase;

import CtrObj.Ban;
import CtrObj.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Zoro
 */
public class DBConnection {

    public static String Driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/coffee_management";
    public static String user = "root";
    public static String pass = "";

    public static Connection OpenDB() throws ClassNotFoundException, SQLException {
        Class.forName(Driver);
        return DriverManager.getConnection(url, user, pass);
    }

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
                DBConnection.Close(rs, ps, cnn);
                return user;
            } else {
                throw new Exception("Vui lòng kiểm tra lại tên tài khoản hoặc mật khẩu!");
            }
        }
        DBConnection.Close(rs, ps, cnn);
        return null;
    }

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
            DBConnection.Close(rs, ps, cnn);
            return listBanActive;
        }
        DBConnection.Close(rs, ps, cnn);
        return null;
    }
}
