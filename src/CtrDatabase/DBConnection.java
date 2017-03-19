/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrDatabase;

import ItfLogin.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    public static User getUser(String name, String pass) throws ClassNotFoundException, SQLException, Exception
    {
        User user;
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();
        if(cnn != null)
        {
            String sql = "Select * from users where Username = ? and Password = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if(rs.next())
            {
                String userName = rs.getString("Username");
                int role = rs.getInt("Role");
                user = new User(userName,role);
                return user;
            }
            else
                throw new Exception("Vui lòng kiểm tra lại tên tài khoản hoặc mật khẩu!");
        }
        return null;
    }
}
