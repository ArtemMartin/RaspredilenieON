/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.raspredilenieon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class GeoManager implements Runnable {

    RaionCelei raionCelei;
    Map<String, double[]> listOP;
    Map<String, double[]> sortedListOP;
    static double[] daFrRaionZelei;
    static int countOP;
    static double frontNaOryd;
    Map<String, Integer> listNameON = new LinkedHashMap<>();

    public GeoManager(RaionCelei raionCelei, Map<String, double[]> listOP) {
        this.raionCelei = raionCelei;
        this.listOP = listOP;
    }

    //формируем карту позывной основное
    public Map<String, Integer> getMapNameON(Map<String, double[]> sortedListOP) {
        List<double[]> listYcastkiXY = getListXYYchastki();
        Map<String, Integer> mapNameON = new LinkedHashMap<>();
        int i = 0;
        String name;
        double[] xyOP;
        double[] xyYchstka;
        double[] osNaprDalOr;
        int osNapr;
        for (Map.Entry<String, double[]> entry : sortedListOP.entrySet()) {
            name = entry.getKey();
            xyOP = entry.getValue();
            xyYchstka = listYcastkiXY.get(i);
            osNaprDalOr = OGZ(xyYchstka[0], xyYchstka[1], xyOP[0], xyOP[1]);
            osNapr = (int) Math.round(osNaprDalOr[1] / 100);
            mapNameON.put(name, osNapr);
            i++;
        }
        return mapNameON;
    }

    //получаем список координат орудийных участков
    public List<double[]> getListXYYchastki() {
        double polYchastkaOryd = (daFrRaionZelei[0] / countOP) / 2;
        frontNaOryd = polYchastkaOryd * 2;
        List<double[]> listYcastkiXY = new ArrayList<>();
        double[] xy;
        double prirashchenYchastka = polYchastkaOryd;
        for (int i = 0; i < countOP; i++) {
            xy = PGZ(raionCelei.getxLev(), raionCelei.getyLev(), daFrRaionZelei[1], prirashchenYchastka);
            listYcastkiXY.add(xy);
            prirashchenYchastka += polYchastkaOryd * 2;
        }
        return listYcastkiXY;
    }

    //сортируем список ОП слева на право относительно центра района целей
    public Map<String, double[]> getSortedList(Map<String, double[]> listOP, RaionCelei raionCelei) {
        Map<String, double[]> sortedListOP = new LinkedHashMap<>();
        //получаем фронт и угол района целей
        daFrRaionZelei = OGZ(raionCelei.getxPrav(), raionCelei.getyPrav(),
                raionCelei.getxLev(), raionCelei.getyLev());
        double d = daFrRaionZelei[0];
        double a = daFrRaionZelei[1];
        //определяем координаты центра района целей
        double[] xyCentra = PGZ(raionCelei.getxLev(), raionCelei.getyLev(), a, d / 2);
        //формируем список ДУ по центру для ОП
        Map<String, Double> listNameYgol = new LinkedHashMap<>();
        OP op;
        double[] dalYgol;
        //для пееребора карты сначала получаем ключи и проходимся по ключам
        Set<String> keys = listOP.keySet();
        for (String key : keys) {
            op = new OP(key, listOP.get(key));
            dalYgol = OGZ(xyCentra[0], xyCentra[1], op.getX(), op.getY());
            listNameYgol.put(key, dalYgol[1]);
        }
        //переопредиляем компаратор для сортировки
        Comparator<Map.Entry<String, Double>> comparator;
        comparator = new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (o2.getValue() >= 4500.0 && o1.getValue() <= 1500.0) {
                    o1.setValue(o1.getValue() + 6000.0);                  
                    return o2.getValue().compareTo(o1.getValue());
                }            
                return o2.getValue().compareTo(o1.getValue());
            }
        };
        //сортируем
        List<Map.Entry<String, Double>> entries = new ArrayList<>(listNameYgol.entrySet());
        Collections.sort(entries, comparator);

        //записываем отсортированный список
        for (Map.Entry<String, Double> e : entries) {
            sortedListOP.put(e.getKey(), listOP.get(e.getKey()));
        }

        return sortedListOP;
    }

    public double[] OGZ(double Xr, double Yr, double Xl, double Yl) {
        double dxc = Xr - Xl;
        double dyc = Yr - Yl;
        double Dc = Math.sqrt(Math.pow(dxc, 2) + Math.pow(dyc, 2));
        double Ac = Math.abs(Math.atan(dyc / (dxc)) / Math.PI * 30) * 100;
        double Ygolc = 0;
        if (dxc > 0 && dyc > 0) {
            Ygolc = Math.round(Ac);
        } else if (dxc < 0 && dyc > 0) {
            Ygolc = Math.round(3000 - Ac);
        } else if (dxc < 0 && dyc < 0) {
            Ygolc = Math.round(3000 + Ac);
        } else if (dxc > 0 && dyc < 0) {
            Ygolc = Math.round(6000 - Ac);
        }
        return new double[]{Dc, Ygolc};
    }

    public static double[] PGZ(double x, double y, double a, double d) {
        double x1 = Math.cos(a / 100 * 6 * 3.141592 / 180) * d + x;
        double y1 = Math.sin(a / 100 * 6 * 3.141592 / 180) * d + y;
        return new double[]{x1, y1};
    }

    @Override
    public void run() {
        sortedListOP = getSortedList(listOP, raionCelei);
        countOP = sortedListOP.size();
        listNameON = getMapNameON(sortedListOP);
        //выводим расчеты
        FrameOutput frameOutput = new FrameOutput();
        frameOutput.getContentPane().setBackground(new Color(153, 153, 0));
        frameOutput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameOutput.setVisible(true);
        String output = "ОП слева на право \n";
        for (Map.Entry<String, double[]> entry : sortedListOP.entrySet()) {
            String name = entry.getKey();
            double[] xyOP = entry.getValue();
            output += name + " Х = " + xyOP[0] + " У = " + xyOP[1] + "\n";
        }
        output += "ОН\n";
        for (Map.Entry<String, Integer> entry : listNameON.entrySet()) {
            String name = entry.getKey();
            Integer val = entry.getValue();
            output += name + " -> " + val + "\n";
        }
        output += "Фронт на орудие -> " + Math.round(frontNaOryd);

        frameOutput.getjTextAreaOutput().setText(output);
    }

}
