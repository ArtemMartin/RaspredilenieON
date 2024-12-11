/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.raspredilenieon;

/**
 *
 * @author user
 */
public class OP {

    final double x;
    final double y;
    final String name;

    public OP(String name, double[] xy) {
        this.name = name;
        this.x = xy[0];
        this.y = xy[1];
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
