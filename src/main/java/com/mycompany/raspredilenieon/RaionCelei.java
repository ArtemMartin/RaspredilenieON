/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.raspredilenieon;

import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class RaionCelei {

    double xLev;
    double yLev;
    double xPrav;
    double yPrav;
    int countOryd;

    public RaionCelei(NewJFrame frame) {
        try {
            this.xLev = Double.parseDouble(frame.getTfXLeft().getText());
            this.yLev = Double.parseDouble(frame.getTfYLeft().getText());
            this.xPrav = Double.parseDouble(frame.getTfXRight().getText());
            this.yPrav = Double.parseDouble(frame.getTfYRight().getText());
            this.countOryd = Integer.parseInt(frame.getTfCountOrydii().getText());
        } catch (Exception ex) {
            System.out.println("In the RaionCelei constructor, getting an empty value from a field: "
                    + ex.getMessage());
        }
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
