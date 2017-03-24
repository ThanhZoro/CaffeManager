/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CtrObj;

import ItfUser.PanelBanHang.PanelBanHang;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Zoro
 */
public class MonRenderer extends JPanel implements ListCellRenderer<Mon>,ActionListener{

    private JLabel jlbTenMon = new JLabel();
    private JLabel jlbGiaTien = new JLabel();
    private JLabel jlbSoLuong = new JLabel();
    private JPanel jpnl = new JPanel(new GridLayout(0, 1, 5, 5));

    public MonRenderer() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBorder(new TitledBorder(new LineBorder(Color.yellow, 5, true), "***oOo***", TitledBorder.TOP, TitledBorder.CENTER));
        jlbTenMon.setFont(new Font("Times New Roman", 3, 18));
        jlbTenMon.setForeground(Color.red);
        this.add(jlbTenMon,BorderLayout.WEST);
        jlbGiaTien.setFont(new Font("Times New Roman", 0, 15));
        jlbSoLuong.setFont(new Font("Times New Roman", 0, 15));
        jpnl.add(jlbGiaTien);
        jpnl.add(jlbSoLuong);
        this.add(jpnl,BorderLayout.CENTER);
        
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Mon> list, Mon mon, int index, boolean isSelected, boolean cellHasFocus) {
        jlbTenMon.setText(mon.getTenMon());
        jlbGiaTien.setText("Giá tiền: " + PanelBanHang.ThemDauPhanCach_Tien(String.valueOf(mon.getGiaTien())));
        jlbSoLuong.setText("Số lượng đặt: " + String.valueOf(mon.getSoLuong()));
        
        jlbTenMon.setOpaque(true);
        jlbGiaTien.setOpaque(true);
        jlbSoLuong.setOpaque(true);
        
        if(isSelected)
        {
            jlbTenMon.setBackground(new Color(255,255,204));
            jlbGiaTien.setBackground(new Color(255,255,204));
            jlbSoLuong.setBackground(new Color(255,255,204));
            jpnl.setBackground(new Color(255,255,204));
            this.setBackground(new Color(255,255,204));
        }
        else
        {
            jlbTenMon.setBackground(new Color(204,255,204));
            jlbGiaTien.setBackground(new Color(204,255,204));
            jlbSoLuong.setBackground(new Color(204,255,204));
            jpnl.setBackground(new Color(204,255,204));
            this.setBackground(new Color(204,255,204));
        }
        
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Xóa đối tượng");
    }
    
}
