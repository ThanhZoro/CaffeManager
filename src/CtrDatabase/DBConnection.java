/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrDatabase;

import CtrObj.*;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Zoro
 */
public class DBConnection {

    public static String Driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/coffee_management";
    public static String user = "root";
    public static String pass = "";
    
    private static SimpleDateFormat sdfNgayThang = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat sdfThoiGian = new SimpleDateFormat("HH:mm:ss");
    
//**********************************************************************************************************************
    //Mở kết nối
    public static Connection OpenDB() throws ClassNotFoundException, SQLException {
        Class.forName(Driver);
        return DriverManager.getConnection(url + "?useSSL=false&useUnicode=true&characterEncoding=UTF-8&user=" + user + "&password=" + pass);
    }
    
//**********************************************************************************************************************
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

//**********************************************************************************************************************
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
                int idUser = rs.getInt("IDuser");
                String userName = rs.getString("Username");
                String passWord = rs.getString("Password");
                int role = rs.getInt("Role");

                user = new User(idUser, userName, passWord, role);

                Close(rs, ps, cnn);
                return user;
            } else {
                throw new Exception("Vui lòng kiểm tra lại tên tài khoản hoặc mật khẩu!");
            }
        }
        return null;
    }

//**********************************************************************************************************************
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
    
//**********************************************************************************************************************
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

//**********************************************************************************************************************
    //Kiểm tra bàn nào đó có đang được phục vụ không
    public static boolean checkActiveBan(int IDBAN) throws ClassNotFoundException, SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "Select * from tables where Trangthai = ? and IDtable = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, 1); //Trạng thái active bằng 1
            ps.setInt(2, IDBAN);
            rs = ps.executeQuery();

            if (rs.next()) {
                Close(rs, ps, cnn);

                return true;
            }
        }

        return false;
    }
    
//**********************************************************************************************************************
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
    
//**********************************************************************************************************************
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
    
//**********************************************************************************************************************
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
                int idMon = rs.getInt("IDmonan");
                String tenMon = rs.getString("Tenmonan");
                int giaTien = rs.getInt("GiaTien");
                String loai = rs.getString("Loai");
                listMon.add(new Mon(idMon, tenMon, giaTien, loai));
            }

            Close(rs, ps, cnn);
            return listMon;
        }
        return null;
    }

//**********************************************************************************************************************
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
                int idMon = rs.getInt("IDmonan");
                String tenMon = rs.getString("Tenmonan");
                int giaTien = rs.getInt("GiaTien");
                String loai = rs.getString("Loai");
                Close(rs, ps, cnn);
                return new Mon(idMon, tenMon, giaTien, loai);
            }
        }
        return null;
    }
    
//**********************************************************************************************************************
    //Lấy thông tin một món ăn
    public static Mon getMon(int IDMON) throws ClassNotFoundException, SQLException, Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT * FROM `monan` WHERE `IDmonan` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, IDMON);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idMon = rs.getInt("IDmonan");
                String tenMon = rs.getString("Tenmonan");
                int giaTien = rs.getInt("GiaTien");
                String loai = rs.getString("Loai");
                Close(rs, ps, cnn);
                return new Mon(idMon, tenMon, giaTien, loai);
            }
        }
        return null;
    }
    
//**********************************************************************************************************************
    //Ghi thời gian tạm vào Bảng Bàn
    public static boolean setThoiGianVao_Ban(int IDBAN, String NGAYTHANG, String THOIGIAN) throws ClassNotFoundException, SQLException, ParseException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "UPDATE `tables` SET `NgayThang` = ?, `ThoiGian` = ? WHERE `IDtable` = ?";
            ps = cnn.prepareStatement(sql);

            if (NGAYTHANG != null){
                java.util.Date ngayThang = sdfNgayThang.parse(NGAYTHANG);
                java.sql.Date ngayThangSql = new java.sql.Date(ngayThang.getTime());
                ps.setDate(1, ngayThangSql);
            }
            else {
                ps.setDate(1, null);
            }
            
            if (THOIGIAN != null){
                java.util.Date thoiGian = sdfThoiGian.parse(THOIGIAN);
                java.sql.Time thoiGianSql = new java.sql.Time(thoiGian.getTime());
                ps.setTime(2, thoiGianSql);
            }
            else {
                ps.setTime(2, null);
            }
            
            ps.setInt(3, IDBAN);
            int rowEff = ps.executeUpdate();
            if (rowEff > 0) {
                Close(null, ps, cnn);
                return true;
            }
        }
        return false;
    }
    
//**********************************************************************************************************************
    //Lấy thời gian từ Bảng Bàn
    public static String getThoiGianVao_Ban(int IDBAN) throws ClassNotFoundException, SQLException, ParseException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT `NgayThang`, `ThoiGian` FROM `tables` WHERE `IDtable` = ? AND `Trangthai` = 0";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, IDBAN);
            rs = ps.executeQuery();
            if(rs.next())
            {
                StringBuffer strKetQua = new StringBuffer();
                
                java.sql.Date ngayThangSql = rs.getDate("NgayThang");
                strKetQua.append(sdfNgayThang.format(ngayThangSql));
                
                java.sql.Time thoiGianSql = rs.getTime("ThoiGian");
                strKetQua.append(sdfThoiGian.format(thoiGianSql));
                
                return strKetQua.toString();
            }
        }
        return null;
    }
    
//**********************************************************************************************************************
    //Thêm một dòng mới vào bảng orders => dòng này sẽ lưu dữ liệu 
    public static boolean setMon_Orders(Mon mon, int IDUSER, int IDBAN) throws ClassNotFoundException, SQLException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "INSERT INTO `orders`(`IDorder`, `IDuser`, `IDban`, `IDmonan`, `Quantity`, `ThanhToan`, `NgayThang`, `ThoiGian`) VALUES (?,?,?,?,?,?,null,null)";
            ps = cnn.prepareStatement(sql);
            int countRowOrder = getCountRowOrder();
            ps.setInt(1, ++countRowOrder);
            ps.setInt(2, IDUSER);
            ps.setInt(3, IDBAN);
            ps.setInt(4, mon.getIdMon());
            ps.setInt(5, mon.getSoLuong());
            ps.setBoolean(6, false); //Chưa thanh toán
            int rowEff = ps.executeUpdate();
            if (rowEff > 0) {
                Close(null, ps, cnn);
                return true;
            }
        }
        return false;
    }
    
//**********************************************************************************************************************
    //Sửa lại dòng orders đã thêm khi tăng số lượng
    public static boolean setUpdateQuantity_Order(Mon mon, int IDUSER,  int IDBAN) throws ClassNotFoundException, SQLException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "UPDATE `orders` SET `Quantity`= ? WHERE `IDuser` = ? AND `IDban` = ? AND `IDmonan` = ? AND `ThanhToan` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, mon.getSoLuong());
            ps.setInt(2, IDUSER);
            ps.setInt(3, IDBAN);
            ps.setInt(4, mon.getIdMon());
            ps.setBoolean(5, false);
            int rowEff = ps.executeUpdate();
            if (rowEff > 0) {
                Close(null, ps, cnn);
                return true;
            }
        }
        return false;
    }
    
//**********************************************************************************************************************
    //Sửa lại dòng orders khi thanh toán rồi
    public static boolean setUpdateThanhToan_Order(Mon mon, int IDUSER, int IDBAN, String NGAYTHANG, String THOIGIAN, int GIAMGIA, boolean THANHTOAN) throws ClassNotFoundException, SQLException, ParseException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "UPDATE `orders` SET `ThanhToan`= ?, `NgayThang` = ?, `ThoiGian`= ?, `GiamGia` = ? WHERE `IDuser` = ? AND `IDban` = ? AND `IDmonan`= ? AND `ThanhToan`= false";
            ps = cnn.prepareStatement(sql);
            ps.setBoolean(1, THANHTOAN);
            
            if (NGAYTHANG != null){
                java.util.Date ngayThang = sdfNgayThang.parse(NGAYTHANG);
                java.sql.Date ngayThangSql = new java.sql.Date(ngayThang.getTime());
                ps.setDate(2, ngayThangSql);
            }
            else {
                ps.setDate(2, null);
            }
            
            if (THOIGIAN != null){
                java.util.Date thoiGian = sdfThoiGian.parse(THOIGIAN);
                java.sql.Time thoiGianSql = new java.sql.Time(thoiGian.getTime());
                ps.setTime(3, thoiGianSql);
            }
            else {
                ps.setTime(3, null);
            }
            
            ps.setInt(4, GIAMGIA);
            ps.setInt(5, IDUSER);
            ps.setInt(6, IDBAN);
            ps.setInt(7, mon.getIdMon());
            
            int rowEff = ps.executeUpdate();
            if (rowEff > 0) {
                Close(null, ps, cnn);
                return true;
            }
        }
        return false;
    }
    
//**********************************************************************************************************************
    //Xóa một dòng 
    public static boolean setDeleteMon_Orders(Mon mon, int IDUSER,  int IDBAN) throws ClassNotFoundException, SQLException
    {Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "DELETE FROM `orders` WHERE `IDuser` = ? AND `IDban` = ? AND `IDmonan` = ? AND `ThanhToan` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, IDUSER);
            ps.setInt(2, IDBAN);
            ps.setInt(3, mon.getIdMon());
            ps.setBoolean(4, false);
            int rowEff = ps.executeUpdate();
            if (rowEff > 0) {
                Close(null, ps, cnn);
                return true;
            }
        }
        return false;
    }
    
//**********************************************************************************************************************
    //Lấy số lượng dòng order 
    public static int getCountRowOrder() throws ClassNotFoundException, SQLException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT COUNT(IDorder) AS IDCount FROM `orders`";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
            {
                int idCount = rs.getInt("IDCount");
                Close(rs, ps, cnn);
                return idCount;
            }
        }
        return -1;
    }
    
//**********************************************************************************************************************
    //Lấy thông tin một dòng order
    public static Order getRowOrder(Mon mon, int IDUSER,  int IDBAN) throws ClassNotFoundException, SQLException, Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            String sql = "SELECT * FROM `orders` WHERE `IDuser` = ? AND `IDban` = ? AND `IDmonan` = ? AND `ThanhToan` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1, IDUSER);
            ps.setInt(2, IDBAN);
            ps.setInt(3, mon.getIdMon());
            ps.setBoolean(4, false);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idOrder = rs.getInt("IDorder");
                int idUser = rs.getInt("IDuser");
                int idBan = rs.getInt("IDban");
                int idMon = rs.getInt("IDmonan");
                int soLuong = rs.getInt("Quantity");
                boolean thanhToan = rs.getBoolean("ThanhToan");
                Close(rs, ps, cnn);
                return new Order(idOrder, idUser, idBan, idMon, soLuong, thanhToan);
            }
        }
        return null;
    }
    
//**********************************************************************************************************************
    //Lấy map danh sách các món đã đặt nhưng chưa thanh toán
    public static Map<Integer, Mon> getMapSTT_Mon(int IDUSER, int IDBAN) throws ClassNotFoundException, SQLException, Exception {
        Map<Integer, Mon> mapStt_Mon = new HashMap<>();
        Map<Integer, Integer> mapIDMon_SoLuong = new HashMap<>();
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        cnn = OpenDB();

        if (cnn != null) {
            //Lấy map gồm danh sách idMon và số lượng
            String sql = "SELECT `IDmonan` , `Quantity` FROM `orders` WHERE `IDuser` = ? AND `IDban` = ? AND `ThanhToan` = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(1,IDUSER);
            ps.setInt(2,IDBAN);
            ps.setBoolean(3, false);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idMon = rs.getInt("IDmonan");
                int soLuong = rs.getInt("Quantity");
                mapIDMon_SoLuong.put(idMon, soLuong);
            }

            //Lấy danh sách idMon
            int i = 0;
            Set<Integer> keySet = mapIDMon_SoLuong.keySet();
            for (Integer key : keySet) {
                Mon mon = getMon(key);
                mon.setSoLuong(mapIDMon_SoLuong.get(key));
                
                mapStt_Mon.put(i, mon);
                i++;
            }
            
            Close(rs, ps, cnn);
            return mapStt_Mon;
        }
        return null;
    }
    
//**********************************************************************************************************************
    //Sửa các dòng order
    public static boolean setUpdateID_Order(int IDORDER, int COUNTID) throws ClassNotFoundException, SQLException
    {
        Connection cnn = null;
        PreparedStatement ps = null;
        cnn = OpenDB();

        if (cnn != null) {
            for (int i = IDORDER; i < COUNTID; i++)
            {
                String sql = "UPDATE `orders` SET `IDorder` = ? WHERE `IDorder` = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, i);
                int giaTri2 = i + 1;
                ps.setInt(2, giaTri2);
                int rowEff = ps.executeUpdate();
                if(rowEff <= 0)
                {
                    Close(null, ps, cnn);
                    return false;
                }
            }
            Close(null, ps, cnn);
            return true;
        }
        return false;
    }
    
}
