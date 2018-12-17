/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy_logic;

import java.util.ArrayList;

/**
 *
 * @author Beido
 */
public class membership {
String name , type;
ArrayList<Double> value ;
Double crisp_membership;
ArrayList<Point> points;
double centroid;

    public double getCentroid() {
        return centroid;
    }

    public void setCentroid(double centroid) {
        this.centroid = centroid;
    }


    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public Double getCrisp_membership() {
        return crisp_membership;
    }

    public void setCrisp_membership(Double crisp_membership) {
        this.crisp_membership = crisp_membership;
    }

    public membership() {
    }

    public membership(String name, String type, ArrayList<Double> value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Double> getValue() {
        return value;
    }

    public void setValue(ArrayList<Double> value) {
        this.value = value;
    }
}
