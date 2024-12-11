/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.raspredilenieon;

/**
 *
 * @author user
 */
public class RaionCelei {

    final double xLev;
    final double yLev;
    final double xPrav;
    final double yPrav;
    final int countOryd;

    public RaionCelei(NewJFrame frame) {
        this.xLev =Double.parseDouble( frame.getTfXLeft().getText());
        this.yLev = Double.parseDouble( frame.getTfYLeft().getText());
        this.xPrav = Double.parseDouble( frame.getTfXRight().getText());
        this.yPrav = Double.parseDouble( frame.getTfYRight().getText());
        this.countOryd = Integer.parseInt(frame.getTfCountOrydii().getText());
    }

    public double getxLev() {
        return xLev;
    }

    public double getyLev() {
        return yLev;
    }

    public double getxPrav() {
        return xPrav;
    }

    public double getyPrav() {
        return yPrav;
    }

    public int getCountOryd() {
        return countOryd;
    }
    
}
