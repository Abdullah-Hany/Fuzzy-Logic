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
public class Var_info {
    String name;
    double crisp_value;
    int membership_number;
    ArrayList<membership> memberships;

    public Var_info() {
    }

    public Var_info(String name, double crisp_value, int membership_number, ArrayList<membership> memberships) {
        this.name = name;
        this.crisp_value = crisp_value;
        this.membership_number = membership_number;
        this.memberships = memberships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCrisp_value() {
        return crisp_value;
    }

    public void setCrisp_value(double crisp_value) {
        this.crisp_value = crisp_value;
    }

    public int getMembership_number() {
        return membership_number;
    }

    public void setMembership_number(int membership_number) {
        this.membership_number = membership_number;
    }

    public ArrayList<membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(ArrayList<membership> memberships) {
        this.memberships = memberships;
    }
    
    
}
