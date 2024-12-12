/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.raspredilenieon;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 *
 * @author user
 */
public class ListenerTextFieldFrame implements Runnable {

    final JTextField txtXLeft;
    final JTextField txtYLeft;
    final JTextField txtXRight;
    final JTextField txtYRight;

    public ListenerTextFieldFrame(NewJFrame frame) {
        txtXLeft = frame.getTfXLeft();
        txtYLeft = frame.getTfYLeft();
        txtXRight = frame.getTfXRight();
        txtYRight = frame.getTfYRight();
    }

    @Override
    public void run() {
        txtXLeft.addActionListener((ActionEvent e) -> {
            txtYLeft.requestFocus();
        });
        txtYLeft.addActionListener((ActionEvent e) -> {
            txtXRight.requestFocus();
        });
        txtXRight.addActionListener((ActionEvent e) -> {
            txtYRight.requestFocus();
        });
    }

}
