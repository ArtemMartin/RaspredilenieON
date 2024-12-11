/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.raspredilenieon;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class RaspredilenieON {

    static int counOryd;
    static Map<String, double[]> listOP = new LinkedHashMap<>();

    public static void main(String[] args) {
        NewJFrame frame = new NewJFrame();
        frame.getContentPane().setBackground(new Color(153, 153, 0));
        frame.setVisible(true);

        centerAllValuesInJTable(frame.getjTable1());

        //слушаем кнопку Решить
        frame.getBtnReshit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) frame.getjTable1().getModel();
                listOP = getTableData(model);
                RaionCelei raionCelei = new RaionCelei(frame);
                new GeoManager(raionCelei, listOP).run();
            }
        });

        //слушаем к-во орудий и меняем таблицу
        frame.getTfCountOrydii().addActionListener((ActionEvent e) -> {
            counOryd = Integer.parseInt(frame.getTfCountOrydii().getText());
            DefaultTableModel model = (DefaultTableModel) frame.getjTable1().getModel();
            model.setRowCount(counOryd);
        });

    }

    //получаем данные с таблицы
    public static Map<String, double[]> getTableData(DefaultTableModel model) {
        Map<String, double[]> list = new LinkedHashMap<>();
        List<Object> dataList = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, 0) != null) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    dataList.add(model.getValueAt(row, col));
                }
                list.put((String) dataList.get(0),
                        new double[]{(double) dataList.get(1),
                            (double) dataList.get(2)});
                dataList.clear();
            }
        }
        return list;
    }

    //center all elements
    public static void centerAllValuesInJTable(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    return label;
                }
            });
        }
    }
}
