/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItfUser.PanelBanHang;

import CtrDatabase.DBConnection;
import static CtrDatabase.DBConnection.getMon;
import CtrObj.*;
import ItfUser.Frame_User;
import static ItfUser.Frame_User.soBanActive;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zoro
 */
public class PanelBanHang extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form PanelBanHang
     */
    private String tenBan;
    private int maBan;

    private JButton jbtnBan;
    private JButton jbtnMon;
    private JButton jbtnNhomMon;

    private final String ACTIONBAN = "actionBan";
    private final String ACTION_NHOMMON = "actionNhomMon";
    private final String ACTION_MON = "actionMon";
    private final String ACTION_GIAMGIA = "actionGiamGia";
    private final String FONT_TNR = "Times New Roman";

    private final int DONGBAN = 0;
    private final int MOBAN = 1;

    private int coChuyenMoBan;
    private int tongTien;

    private ArrayList<Mon> listMon = new ArrayList<>();
    DefaultListModel<Mon> dtmMon;
    
    SimpleDateFormat sdfNgayThang = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdfThoiGian = new SimpleDateFormat("HH:mm:ss");

//**********************************************************************************************************************
    public PanelBanHang() {
        initComponents();
        //Cấu trúc panel 
        KhoiTaoPanel();

        //Khởi tạo panel Bàn
        KhoiTaoPanelBan();
        KhoiTaoPanelNhomMon();
    }
    
//**********************************************************************************************************************
    private void KhoiTaoPanel() {
        //Set Layout lại cho Panel Chung
        this.setLayout(new BorderLayout());
        jpnlBan.setPreferredSize(new Dimension(385, 609));
        this.add(jpnlBan, BorderLayout.WEST);
        
        //Tạo cái panel chứa 2 panel trong đó
        JPanel jpnl = new JPanel();
        jpnl.setLayout(new BorderLayout());
        jpnlChiTietBan.setPreferredSize(new Dimension(461, 609));
        jpnlNhomMon.setPreferredSize(new Dimension(120, 609));
        jpnl.add(jpnlChiTietBan, BorderLayout.WEST);
        jpnl.add(jpnlNhomMon, BorderLayout.CENTER);
        
        this.add(jpnl, BorderLayout.CENTER);
        jpnlMon.setPreferredSize(new Dimension(250, 609));
        this.add(jpnlMon, BorderLayout.EAST);

        //Tắt các panel nhỏ
        jpnlChiTietBan.setVisible(false);
        jtfGiamGia.setActionCommand(ACTION_GIAMGIA);
        jtfGiamGia.addActionListener(this);
        jpnlMon.setVisible(false);
        jpnlNhomMon.setVisible(false);
    }

//**********************************************************************************************************************
    private void KhoiTaoPanelBan() {
        jpnlBan.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        //Tạo bàn trong panel Bàn
        try {
            int soBan = DBConnection.getSoBan();

            for (int i = 1; i <= soBan; i++) {
                //Tạo button từng bàn
                JButton jbtn = new JButton();
                tenBan = "Bàn " + i;
                jbtn.setPreferredSize(new Dimension(100, 45));
                jbtn.setText(tenBan);
                jbtn.setFont(new Font(FONT_TNR, 1, 16));
                jbtn.setActionCommand(ACTIONBAN + tenBan);
                jbtn.addActionListener(this);
                
                if (DBConnection.checkActiveBan(i)) {
                    jbtn.setBackground(Color.RED);
                } else {
                    jbtn.setBackground(new Color(255, 255, 255));
                }

                jpnlBan.add(jbtn);
                jpnlBan.setPreferredSize(new Dimension(385, 609));
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
//**********************************************************************************************************************
    private void KhoiTaoPanelNhomMon() {
        jpnlNhomMon.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //Lấy danh sách nhóm món để tạo button
        try {
            ArrayList<String> listNhomMon = DBConnection.getListNhomMon();

            for (String nhomMon : listNhomMon) {
                JButton jbtn = new JButton();
                jbtn.setText(nhomMon);
                jbtn.setPreferredSize(new Dimension(145, 50));
                jbtn.setFont(new Font(FONT_TNR, 1, 16));
                jbtn.setActionCommand(ACTION_NHOMMON + nhomMon);
                jbtn.addActionListener(this);

                jpnlNhomMon.add(jbtn);
                jpnlNhomMon.setPreferredSize(new Dimension(120, 609));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
//**********************************************************************************************************************
    private void KhoiTaoPanelMon(ArrayList<Mon> LIST_MON) {
        jpnlMon.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        //Lấy danh sách nhóm món để tạo button
        try {
            for (Mon mon : LIST_MON) {
                JButton jbtn = new JButton();
                jbtn.setText(mon.getTenMon());
                jbtn.setForeground(Color.red);
                jbtn.setBorder(new TitledBorder(new EmptyBorder(1, 1, 1, 1), "Giá tiền: " + ThemDauPhanCach_Tien(String.valueOf(mon.getGiaTien())) + " VNĐ", TitledBorder.CENTER, TitledBorder.ABOVE_BOTTOM, new Font(FONT_TNR, 0, 15)));
                jbtn.setPreferredSize(new Dimension(200, 60));
                jbtn.setFont(new Font(FONT_TNR, 1, 16));
                
                jbtn.setActionCommand(ACTION_MON + mon.getTenMon());
                jbtn.addActionListener(this);

                jpnlMon.add(jbtn);
                jpnlMon.setPreferredSize(new Dimension(250, 609));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
//**********************************************************************************************************************
    private void KhoiTaoListMon() {
        //Tạo giao diện cho JList Món
        dtmMon = new DefaultListModel<>();

        for (Mon mon : listMon) {
            dtmMon.addElement(mon);
        }
        jListMon.setModel(dtmMon);
        jListMon.setCellRenderer(new MonRenderer());
    }
    
//**********************************************************************************************************************
    private void ThemTienVaoLabel() {
        //Thêm tiền vào các label 
        try {
            jlbTongTien.setText(ThemDauPhanCach_Tien(String.valueOf(tongTien)));
            String giamGia = jtfGiamGia.getText();
            int tienGiamGia = Integer.parseInt(giamGia.trim());
            
            if (tongTien == 0) {
                jlbTongTien.setText("0");
                jlbThanhToan.setText("0");
                
            } else if (tienGiamGia >= 1000 && tienGiamGia <= tongTien) {
                int thanhToan = tongTien - tienGiamGia;
                jlbDonVi.setText("VNĐ");
                jlbThanhToan.setText(ThemDauPhanCach_Tien(String.valueOf(thanhToan)));
                
            } else if (tienGiamGia > 0 && tienGiamGia <= 100) {
                int thanhToan = tongTien - ((tongTien * tienGiamGia) / 100);
                jlbDonVi.setText("%");
                jlbThanhToan.setText(ThemDauPhanCach_Tien(String.valueOf(thanhToan)));
                
            } else if (tienGiamGia == 0) {
                int thanhToan = tongTien;
                jlbThanhToan.setText(ThemDauPhanCach_Tien(String.valueOf(thanhToan)));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập sai dữ liệu. Vui lòng nhập lại!");
            jtfGiamGia.setText("0");
            jlbDonVi.setText("");
        }
    }
    
//**********************************************************************************************************************
    public static String ThemDauPhanCach_Tien(String tienMau) {
        StringBuffer strKetQua = new StringBuffer();
        int intTienMau = Integer.parseInt(tienMau);
        int demChuSo = (int) Math.log10((double) intTienMau) + 1;

        //vd 99,999,999
        if (demChuSo >= 7) {
            int soDu = demChuSo % 3;
            strKetQua.append(tienMau.substring(0, soDu)).append(",");
            strKetQua.append(tienMau.substring(soDu, soDu + 3)).append(",");
            strKetQua.append(tienMau.substring(soDu + 3));
        } else if (demChuSo == 6) { //999,999
            strKetQua.append(tienMau.substring(0, 3)).append(",");
            strKetQua.append(tienMau.substring(3));
        } else if (demChuSo >= 4) //99,999
        {
            int soDu = demChuSo % 3;
            strKetQua.append(tienMau.substring(0, soDu)).append(",");
            strKetQua.append(tienMau.substring(soDu));
        }
        return String.valueOf(strKetQua);
    }
    
//**********************************************************************************************************************
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlBan = new javax.swing.JPanel();
        jpnlChiTietBan = new javax.swing.JPanel();
        jlbTenBan = new javax.swing.JLabel();
        jbtnMoBan = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbtnThanhToan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfGiamGia = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListMon = new javax.swing.JList<>();
        jlbTongTien = new javax.swing.JLabel();
        jlbThanhToan = new javax.swing.JLabel();
        jlbDonVi = new javax.swing.JLabel();
        jlbTrangThai = new javax.swing.JLabel();
        jpnlNhomMon = new javax.swing.JPanel();
        jpnlMon = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1280, 630));

        jpnlBan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Bàn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
        jpnlBan.setMaximumSize(new java.awt.Dimension(385, 609));
        jpnlBan.setName(""); // NOI18N
        jpnlBan.setPreferredSize(new java.awt.Dimension(385, 609));

        javax.swing.GroupLayout jpnlBanLayout = new javax.swing.GroupLayout(jpnlBan);
        jpnlBan.setLayout(jpnlBanLayout);
        jpnlBanLayout.setHorizontalGroup(
            jpnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );
        jpnlBanLayout.setVerticalGroup(
            jpnlBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jpnlChiTietBan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Chi Tiết", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
        jpnlChiTietBan.setPreferredSize(new java.awt.Dimension(461, 609));

        jlbTenBan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jlbTenBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTenBan.setText("$Bàn_No");

        jbtnMoBan.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jbtnMoBan.setText("Mở Bàn");
        jbtnMoBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMoBanActionPerformed(evt);
            }
        });

        jbtnThanhToan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jbtnThanhToan.setText("Thanh Toán");
        jbtnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnThanhToanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Tổng tiền:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Giảm giá:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 51));
        jLabel4.setText("Tiền Thanh Toán:");

        jtfGiamGia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jtfGiamGia.setText("0");
        jtfGiamGia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfGiamGiaFocusLost(evt);
            }
        });

        jListMon.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jListMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListMonMouseClicked(evt);
            }
        });
        jListMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jListMonKeyPressed(evt);
            }
        });
        jListMon.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListMonValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListMon);

        jlbTongTien.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jlbTongTien.setText("0");

        jlbThanhToan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jlbThanhToan.setText("0");

        jlbDonVi.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlbThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jbtnThanhToan)))
                .addGap(61, 61, 61))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jtfGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbDonVi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlbThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addComponent(jbtnThanhToan)
                .addGap(10, 10, 10))
        );

        jlbTrangThai.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jlbTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTrangThai.setText("$Trạng_Thái");

        javax.swing.GroupLayout jpnlChiTietBanLayout = new javax.swing.GroupLayout(jpnlChiTietBan);
        jpnlChiTietBan.setLayout(jpnlChiTietBanLayout);
        jpnlChiTietBanLayout.setHorizontalGroup(
            jpnlChiTietBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlChiTietBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpnlChiTietBanLayout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addGroup(jpnlChiTietBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlChiTietBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jbtnMoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlChiTietBanLayout.setVerticalGroup(
            jpnlChiTietBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlChiTietBanLayout.createSequentialGroup()
                .addComponent(jlbTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jbtnMoBan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnlNhomMon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Danh Mục", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
        jpnlNhomMon.setPreferredSize(new java.awt.Dimension(120, 609));

        javax.swing.GroupLayout jpnlNhomMonLayout = new javax.swing.GroupLayout(jpnlNhomMon);
        jpnlNhomMon.setLayout(jpnlNhomMonLayout);
        jpnlNhomMonLayout.setHorizontalGroup(
            jpnlNhomMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );
        jpnlNhomMonLayout.setVerticalGroup(
            jpnlNhomMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jpnlMon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Món", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
        jpnlMon.setPreferredSize(new java.awt.Dimension(250, 609));

        javax.swing.GroupLayout jpnlMonLayout = new javax.swing.GroupLayout(jpnlMon);
        jpnlMon.setLayout(jpnlMonLayout);
        jpnlMonLayout.setHorizontalGroup(
            jpnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );
        jpnlMonLayout.setVerticalGroup(
            jpnlMonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnlBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jpnlChiTietBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnlNhomMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnlMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnlMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlNhomMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlChiTietBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnThanhToanActionPerformed
        // TODO add your handling code here:
        try {
            if (DBConnection.updateActiveBan(maBan, 0)) {
                //Tắt các panel chọn món và mở lại nút mở bàn
                jpnlNhomMon.setVisible(false);
                jpnlMon.setVisible(false);
                jlbTrangThai.setText("Chưa mở!");
                jbtnMoBan.setEnabled(true);
                jbtnThanhToan.setEnabled(false);
                jbtnMoBan.setText("Mở bàn");
                jtfGiamGia.setEnabled(false);
                coChuyenMoBan = MOBAN;

                //Cập nhật lại số bàn active => giảm đi 1
                Frame_User.jlbSoBanActive.setText("Đang phục vụ: " + String.valueOf(--Frame_User.soBanActive) + " bàn");

                //Xét màu lại cho button bàn => đổi thành màu thường là chưa mở
                jbtnBan.setBackground(new Color(255, 255, 255));

                //Xét lại order
                String thoiGianVao_Ban = DBConnection.getThoiGianVao_Ban(maBan);
                String strGiamGia = jtfGiamGia.getText();
                int intGiamGia = Integer.parseInt(strGiamGia);
                
                for (Mon mon : listMon) {
                    DBConnection.setUpdateThanhToan_Order(mon, Frame_User.idUser, maBan, thoiGianVao_Ban.substring(0, 10), thoiGianVao_Ban.substring(10), intGiamGia, true);
                }
                DBConnection.setThoiGianVao_Ban(maBan, null, null);

                //Xóa list danh sách các món
                listMon = new ArrayList<>();
                KhoiTaoListMon();

                //Xóa các label tiền
                jlbTongTien.setText("0");
                jlbThanhToan.setText("0");
                jlbDonVi.setText("");
                jtfGiamGia.setText("0");
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jbtnThanhToanActionPerformed

//**********************************************************************************************************************
    private void jbtnMoBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMoBanActionPerformed
        // TODO add your handling code here:
        if (coChuyenMoBan == MOBAN) {
            try {
                if (DBConnection.updateActiveBan(maBan, 1)) {
                    //Mở các panel chọn món và tắt nút mở bàn
                    jpnlNhomMon.setVisible(true);
                    jpnlMon.setVisible(true);
                    jlbTrangThai.setText("Đang phục vụ...");
                    jbtnThanhToan.setEnabled(true);
                    coChuyenMoBan = DONGBAN;
                    jbtnMoBan.setText("Đóng bàn");

                    //Cập nhật lại số bàn active => tăng thêm 1
                    Frame_User.jlbSoBanActive.setText("Đang phục vụ: " + String.valueOf(++Frame_User.soBanActive) + " bàn");

                    //Xét màu lại cho button bàn => đổi thành màu đỏ là đang phục vụ
                    jbtnBan.setBackground(Color.RED);

                    //Cài đặt thời gian tạm vào bảng bàn
                    String tenBan = jbtnBan.getText();
                    tenBan = tenBan.substring(4);
                    int maBan = Integer.parseInt(tenBan);
                    
                    java.util.Date ngayGio = new java.util.Date(System.currentTimeMillis());
                    String ngayThang = sdfNgayThang.format(ngayGio.getTime());
                    String thoiGian = sdfThoiGian.format(ngayGio.getTime());
                    boolean setThoiGianVao_Ban = DBConnection.setThoiGianVao_Ban(maBan, ngayThang, thoiGian);
                    if (!setThoiGianVao_Ban) {
                        throw new Exception("Bị lỗi nhập liệu thời gian. Xin vui lòng kiểm tra lại!");
                    }
                }
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else if (coChuyenMoBan == DONGBAN) {
            try {
                if (DBConnection.updateActiveBan(maBan, 0)) {
                    //Tắt các panel chọn món và mở lại nút mở bàn
                    jpnlNhomMon.setVisible(false);
                    jpnlMon.setVisible(false);
                    jlbTrangThai.setText("Chưa mở!");
                    jbtnMoBan.setEnabled(true);
                    jbtnThanhToan.setEnabled(false);
                    jbtnMoBan.setText("Mở bàn");
                    coChuyenMoBan = MOBAN;

                    //Cập nhật lại số bàn active => giảm đi 1
                    Frame_User.jlbSoBanActive.setText("Đang phục vụ: " + String.valueOf(--Frame_User.soBanActive) + " bàn");

                    //Xét màu lại cho button bàn => đổi thành màu thường là chưa mở
                    jbtnBan.setBackground(new Color(255, 255, 255));

                    //Cài đặt thời gian tạm vào bảng bàn
                    boolean setThoiGianVao_Ban = DBConnection.setThoiGianVao_Ban(maBan, null, null);
                    if (!setThoiGianVao_Ban) {
                        throw new Exception("Bị lỗi nhập liệu thời gian. Xin vui lòng kiểm tra lại!");
                    }
                }
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jbtnMoBanActionPerformed

//**********************************************************************************************************************
    private void XoaMon(Mon monTemp) {
        //Tạo một JOptionPane để chọn số lượng cần xóa
        Object[] mangSo = new Object[monTemp.getSoLuong() + 1];
        int i = 0;
        while (i <= monTemp.getSoLuong()) {
            mangSo[i] = i;
            i++;
        }

        Object showInputDialog = JOptionPane.showInputDialog(jListMon, "Hãy nhập số lượng cần XÓA: ", "Xóa món " + monTemp.getTenMon(), JOptionPane.INFORMATION_MESSAGE, null, mangSo, mangSo[1]);
        if (showInputDialog != null) {
            //----------------------------------------------------------------------------------------------
            if ((int) showInputDialog >= 1 && (int) showInputDialog <= (monTemp.getSoLuong() - 1)) {
                listMon.remove(monTemp);
                try {
                    monTemp.setSoLuong((monTemp.getSoLuong()) - (int) showInputDialog);
                    DBConnection.setUpdateQuantity_Order(monTemp, Frame_User.idUser, maBan);
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
                listMon.add(monTemp);
                KhoiTaoListMon();
            //----------------------------------------------------------------------------------------------
            } else if ((int) showInputDialog == monTemp.getSoLuong()) {
                try {
                    Order rowOrder = DBConnection.getRowOrder(monTemp, Frame_User.idUser, maBan);
                    int countRowOrder = DBConnection.getCountRowOrder();
                    DBConnection.setDeleteMon_Orders(monTemp, Frame_User.idUser, maBan);
                    boolean setUpdateID_Order = DBConnection.setUpdateID_Order(rowOrder.getIdOrder(), countRowOrder);
                    if (!setUpdateID_Order) {
                        throw new Exception("Đã xảy ra lỗi!");
                    }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
                listMon.remove(monTemp);
                KhoiTaoListMon();
            }
            tongTien -= (monTemp.getGiaTien()) * ((int) showInputDialog);
            ThemTienVaoLabel();
        }
    }
    
//**********************************************************************************************************************
    private void ThemMon(Mon monTemp) {
        //Tạo một JOptionPane để chọn số lượng cần xóa
        Object[] mangSo = new Object[monTemp.getSoLuong() + 30];
        int i = 0;
        while (i <= monTemp.getSoLuong() + 29) {
            if (i == monTemp.getSoLuong() + 29) {
                mangSo[i] = "...";
                break;
            }
            mangSo[i] = String.valueOf(i);
            i++;
        }

        Object showInputDialog = JOptionPane.showInputDialog(jListMon, "Hãy nhập số lượng cần THÊM: ", "Thêm món " + monTemp.getTenMon(), JOptionPane.INFORMATION_MESSAGE, null, mangSo, "1");
        if (showInputDialog != null) {
            String strInput = (String) showInputDialog;
            //----------------------------------------------------------------------------------------------
            if (strInput.equals("...")) {
                String soLuongCanThem = JOptionPane.showInputDialog(jListMon, "Hãy nhập số lượng cần THÊM: ", "Thêm món " + monTemp.getTenMon(), JOptionPane.INFORMATION_MESSAGE);
                listMon.remove(monTemp);
                try {
                    int intSoLuongCanThem = Integer.parseInt(soLuongCanThem);
                    monTemp.setSoLuong((monTemp.getSoLuong()) + intSoLuongCanThem);
                    DBConnection.setUpdateQuantity_Order(monTemp, Frame_User.idUser, maBan);

                    tongTien += (monTemp.getGiaTien()) * (intSoLuongCanThem);
                    ThemTienVaoLabel();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
                listMon.add(monTemp);
                KhoiTaoListMon();
            //----------------------------------------------------------------------------------------------
            } else {
                int intInput = 0;
                try {
                    intInput = Integer.parseInt(strInput);
                    if (intInput < 0) {
                        throw new Exception("Đã xảy ra lỗi!");
                    }
                    listMon.remove(monTemp);
                    if (intInput >= 1 && intInput < monTemp.getSoLuong() + 30) {
                        monTemp.setSoLuong(monTemp.getSoLuong() + intInput);
                        DBConnection.setUpdateQuantity_Order(monTemp, Frame_User.idUser, maBan);
                    }
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
                listMon.add(monTemp);
                KhoiTaoListMon();

                tongTien += (monTemp.getGiaTien()) * (intInput);
                ThemTienVaoLabel();
            }
        }
    }
    
//**********************************************************************************************************************
    private void jListMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListMonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultListModel<Mon> dtmJlistget_Mon = (DefaultListModel<Mon>) jListMon.getModel();
            if (!dtmJlistget_Mon.isEmpty() && (jListMon.getSelectedIndex()) >= 0) {
                Mon monTemp = dtmMon.getElementAt(jListMon.getSelectedIndex());

                //LỰA CHỌN XÓA HAY THÊM
                Object[] options = {"Xóa", "Thêm", "Hủy"};
                int luaChon = JOptionPane.showOptionDialog(jListMon, "Bạn muốn làm gì?", "Chỉnh sửa " + monTemp.getTenMon(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (luaChon == 0) {
                    XoaMon(monTemp);
                } else if (luaChon == 1) {
                    ThemMon(monTemp);
                }
            }
        }
    }//GEN-LAST:event_jListMonMouseClicked

//**********************************************************************************************************************
    private void jtfGiamGiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfGiamGiaFocusLost
        // TODO add your handling code here:
        ThemTienVaoLabel();
    }//GEN-LAST:event_jtfGiamGiaFocusLost

//**********************************************************************************************************************
    private void jListMonValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListMonValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jListMonValueChanged

//**********************************************************************************************************************
    private void jListMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListMonKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DefaultListModel<Mon> dtmJlistget_Mon = (DefaultListModel<Mon>) jListMon.getModel();
            if (!dtmJlistget_Mon.isEmpty() && (jListMon.getSelectedIndex()) >= 0) {
                Mon monTemp = dtmMon.getElementAt(jListMon.getSelectedIndex());

                //LỰA CHỌN XÓA HAY THÊM
                Object[] options = {"Xóa", "Thêm", "Hủy"};
                int luaChon = JOptionPane.showOptionDialog(jListMon, "Bạn muốn làm gì?", "Chỉnh sửa " + monTemp.getTenMon(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (luaChon == 0) {
                    XoaMon(monTemp);
                } else if (luaChon == 1) {
                    ThemMon(monTemp);
                }
            }
        }
    }//GEN-LAST:event_jListMonKeyPressed
//**********************************************************************************************************************

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<Mon> jListMon;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnMoBan;
    private javax.swing.JButton jbtnThanhToan;
    private javax.swing.JLabel jlbDonVi;
    private javax.swing.JLabel jlbTenBan;
    private javax.swing.JLabel jlbThanhToan;
    private javax.swing.JLabel jlbTongTien;
    private javax.swing.JLabel jlbTrangThai;
    private javax.swing.JPanel jpnlBan;
    private javax.swing.JPanel jpnlChiTietBan;
    private javax.swing.JPanel jpnlMon;
    private javax.swing.JPanel jpnlNhomMon;
    private javax.swing.JTextField jtfGiamGia;
    // End of variables declaration//GEN-END:variables

//**********************************************************************************************************************
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        //----------------------------------------------------------------------------------------------
        //Tạo action của các Button Bàn
        if (actionCommand.contains(ACTIONBAN)) {
            actionCommand = actionCommand.substring(ACTIONBAN.length());
            //Mở panel Chi tiết bàn
            jpnlChiTietBan.setVisible(true);
            if (e.getSource() instanceof JButton) {
                jbtnBan = (JButton) e.getSource();
            }
            int i = 1;
            try {
                //Gán tên cho jlbTenBan
                int soBan = DBConnection.getSoBan();
                while (i <= soBan) {
                    if (actionCommand.equals("Bàn " + i)) {
                        jlbTenBan.setText("Bàn " + i);
                        maBan = i;
                        break;
                    } else {
                        i++;
                    }
                }

                //Xét xem Bàn thứ i này có đang được phục vụ không
                if (DBConnection.checkActiveBan(maBan)) { //Đang được phục vụ
                    jbtnMoBan.setEnabled(false);
                    coChuyenMoBan = DONGBAN;
                    jbtnMoBan.setText("Mở bàn");
                    jpnlNhomMon.setVisible(true);
                    jpnlMon.setVisible(true);
                    jlbTrangThai.setText("Đang phục vụ...");
                    jbtnThanhToan.setEnabled(true);
                    jtfGiamGia.setEnabled(true);

                    listMon = new ArrayList<>();
                    tongTien = 0;

                    Map<Integer, Mon> mapSTT_Mon = DBConnection.getMapSTT_Mon(Frame_User.idUser, maBan);
                    Set<Integer> keySet = mapSTT_Mon.keySet();
                    for (Integer key : keySet) {
                        Mon mon = mapSTT_Mon.get(key);
                        tongTien += (mon.getGiaTien() * mon.getSoLuong());
                        listMon.add(mon);
                    }

                    KhoiTaoListMon();
                    ThemTienVaoLabel();

                } else { //Không được phục vụ
                    jbtnMoBan.setEnabled(true);
                    coChuyenMoBan = MOBAN;
                    jbtnMoBan.setText("Mở bàn");
                    jpnlNhomMon.setVisible(false);
                    jpnlMon.setVisible(false);
                    jlbTrangThai.setText("Chưa mở!");
                    jbtnThanhToan.setEnabled(false);
                    jtfGiamGia.setEnabled(false);

                    listMon = new ArrayList<>();
                    KhoiTaoListMon();
                    tongTien = 0;
                    ThemTienVaoLabel();
                }
                return;
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        //----------------------------------------------------------------------------------------------
        //Tạo action của các Button Nhóm Món
        } else if (actionCommand.contains(ACTION_NHOMMON)) {
            jpnlMon.setVisible(false);
            jpnlMon.removeAll();

            if (e.getSource() instanceof JButton) {
                jbtnNhomMon = (JButton) e.getSource();
            }
            actionCommand = actionCommand.substring(ACTION_NHOMMON.length());
            try {
                ArrayList<Mon> listMonTemp = DBConnection.getListMon(actionCommand);

                jpnlMon.setVisible(true);

                KhoiTaoPanelMon(listMonTemp);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        //----------------------------------------------------------------------------------------------
        //Tạo action của các Button Món 
        } else if (actionCommand.contains(ACTION_MON)) {
            jbtnMoBan.setText("Mở bàn");
            jbtnMoBan.setEnabled(false);
            jtfGiamGia.setEnabled(true);
            actionCommand = actionCommand.substring(ACTION_MON.length());
            if (e.getSource() instanceof JButton) {
                jbtnMon = (JButton) e.getSource();
            }

            try {
                Mon mon = DBConnection.getMon(jbtnMon.getText(), jbtnNhomMon.getText());

                if (!listMon.isEmpty()) {
                    boolean equals = false;
                    int soLuong = 1;

                    for (Mon monTemp : listMon) {
                        equals = mon.equals(monTemp);
                        if (equals) {
                            soLuong = monTemp.getSoLuong();
                            break;
                        }
                    }
                    //Viết ở ngoài để tránh ConcurrentModificationException của Collection 
                    if (equals) {
                        listMon.remove(mon);
                        mon.setSoLuong(++soLuong);
                        listMon.add(mon);
                        DBConnection.setUpdateQuantity_Order(mon, Frame_User.idUser, maBan);
                    } else {
                        listMon.add(mon);
                        DBConnection.setMon_Orders(mon, Frame_User.idUser, maBan);
                    }

                } else {
                    listMon.add(mon);
                    DBConnection.setMon_Orders(mon, Frame_User.idUser, maBan);
                }

                tongTien += mon.getGiaTien();
                KhoiTaoListMon();
                ThemTienVaoLabel();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        //----------------------------------------------------------------------------------------------    
        } else if (actionCommand.contains(ACTION_GIAMGIA)) {
            ThemTienVaoLabel();
        }
    }
}
