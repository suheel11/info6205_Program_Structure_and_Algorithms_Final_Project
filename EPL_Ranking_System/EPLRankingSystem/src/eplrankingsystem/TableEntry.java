/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eplrankingsystem;

/**
 *
 * @author suhas
 */
public class TableEntry implements Comparable<TableEntry>{
    private String team;
    private int points;

    public TableEntry(String team, int points) {
        this.team = team;
        this.points = points;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    //to sort teams in the table according to their points
    @Override
    public int compareTo(TableEntry t) {
        return t.points - this.points;
    }
    
}
