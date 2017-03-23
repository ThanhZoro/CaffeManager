/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItfUser.PanelBanHang;

import CtrDatabase.DBConnection;
import CtrObj.Mon;
import ItfUser.Frame_User;
import static ItfUser.Frame_User.soBanActive;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
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
    private final String FONT_TNR = "Times New Roman";
    private final int DONGBAN = 0;
    private final int MOBAN = 1;
    private int coChuyenMoBan;
    private ArrayList<Mon> listMon = new ArrayList<>();

    public PanelBanHang() {
        initComponents();
        //Cấu trúc panel 
        KhoiTaoPanel();

        //Khởi tạo panel Bàn
        KhoiTaoPanelBan();
        KhoiTaoPanelNhomMon();
        
    }

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
        jpnlMon.setVisible(false);
        jpnlNhomMon.setVisible(false);
    }

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
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void KhoiTaoPanelMon(ArrayList<Mon> LIST_MON) {
        jpnlMon.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        //Lấy danh sách nhóm món để tạo button
        try {
            for (Mon mon : LIST_MON) {
                JButton jbtn = new JButton();
                jbtn.setText(mon.getTenMon());
                jbtn.setForeground(Color.red);
                jbtn.setBorder(new TitledBorder(new EmptyBorder(1, 1, 1, 1), "Giá tiền: " + mon.getGiaTien() + " VNĐ", TitledBorder.CENTER, TitledBorder.ABOVE_BOTTOM, new Font(FONT_TNR, 0, 15)));
                jbtn.setPreferredSize(new Dimension(200, 60));
                jbtn.setFont(new Font(FONT_TNR, 1, 16));
                jbtn.setActionCommand(ACTION_MON + mon.getTenMon());
                jbtn.addActionListener(this);

                jpnlMon.add(jbtn);
                jpnlMon.setPreferredSize(new Dimension(250, 609));
            }

        } catch (Exception ex) {
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void KhoiTaoListMon() {
        DefaultListModel<Mon> dtmMon = new DefaultListModel<>();
        
        for (Mon mon : listMon) {
            dtmMon.addElement(mon);
        }
        jListMon.setModel(dtmMon);
    }

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
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListMon = new javax.swing.JList<>();
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

        jpnlChiTietBan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Bàn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
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
        jLabel4.setText("Thanh Toán:");

        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jTextField1.setText("0");

        jListMon.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(jListMon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jbtnThanhToan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnThanhToan)
                .addGap(13, 13, 13))
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

        jpnlNhomMon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Bàn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
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

        jpnlMon.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), "Bàn", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N
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
                coChuyenMoBan = MOBAN;

                //Cập nhật lại số bàn active => giảm đi 1
                Frame_User.jlbSoBanActive.setText("Đang phục vụ: " + String.valueOf(--Frame_User.soBanActive) + " bàn");

                //Xét màu lại cho button bàn => đổi thành màu thường là chưa mở
                jbtnBan.setBackground(new Color(255, 255, 255));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbtnThanhToanActionPerformed

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
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
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
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jbtnMoBanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<Mon> jListMon;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbtnMoBan;
    private javax.swing.JButton jbtnThanhToan;
    private javax.swing.JLabel jlbTenBan;
    private javax.swing.JLabel jlbTrangThai;
    private javax.swing.JPanel jpnlBan;
    private javax.swing.JPanel jpnlChiTietBan;
    private javax.swing.JPanel jpnlMon;
    private javax.swing.JPanel jpnlNhomMon;
    // End of variables declaration//GEN-END:variables

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

                } else { //Không được phục vụ
                    jbtnMoBan.setEnabled(true);
                    coChuyenMoBan = MOBAN;
                    jbtnMoBan.setText("Mở bàn");
                    jpnlNhomMon.setVisible(false);
                    jpnlMon.setVisible(false);
                    jlbTrangThai.setText("Chưa mở!");
                    jbtnThanhToan.setEnabled(false);
                }
                return;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            //----------------------------------------------------------------------------------------------
            //Tạo action của các Button Món
        } else if (actionCommand.contains(ACTION_MON)) {
            jbtnMoBan.setText("Mở bàn");
            jbtnMoBan.setEnabled(false);
            actionCommand = actionCommand.substring(ACTION_MON.length());
            if (e.getSource() instanceof JButton) {
                jbtnMon = (JButton) e.getSource();
            }

            try {
                Mon mon = DBConnection.getMon(jbtnMon.getText(), jbtnNhomMon.getText());
                if (!listMon.isEmpty()) {
                    boolean equals = false;
                    Mon monDaDat = null;
                    for (Mon monTemp : listMon) {
                        equals = mon.equals(monTemp);
                        if (equals) {
                            monDaDat = monTemp;
                            break;
                        }
                    }
                    //Viết ở ngoài để tránh ConcurrentModificationException của Collection 
                    if (equals) {
                        listMon.remove(mon);
                        int soLuong = monDaDat.getSoLuong();
                        mon.setSoLuong(++soLuong);
                        listMon.add(mon);
                    } else if (!equals) {
                        listMon.add(mon);
                    }
                    
                } else {
                    listMon.add(mon);
                }
                
                KhoiTaoListMon();
            } catch (SQLException ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(PanelBanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
