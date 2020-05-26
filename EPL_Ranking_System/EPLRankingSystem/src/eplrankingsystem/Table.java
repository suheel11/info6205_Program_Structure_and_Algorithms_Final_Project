/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eplrankingsystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author suhas
 */
public class Table {
    private List<TableEntry> tableEntries;
    private static final Table table = new Table();

    private Table() {
        tableEntries = new ArrayList();
    }
    
    public static Table getInstance(){ 
        return table; 
    } 
    
    //prints the predicted ranking table
    public void printTable() {
        int count = 1;
        for(TableEntry tableEntry:tableEntries){
            System.out.printf("%-5d%-25s %-5d\n",count,tableEntry.getTeam(),tableEntry.getPoints());
            count++;
        }
    }
    
    public List<TableEntry> getTableEntries() {
        return tableEntries;
    }

    public void setTeamList(List<TableEntry> tableEntries) {
        this.tableEntries = tableEntries;
    }
    
    public TableEntry getTeam(String teamName){
        for(TableEntry tableEntry:tableEntries){
            if(tableEntry.getTeam().equalsIgnoreCase(teamName))
                return tableEntry;
        }
        return null;
    }
    
    //given hometeam awayteam and goaldifference in the match, update the table
    public void updateTable(String homeTeam, String awayTeam, int goalDifference){
        TableEntry homeTableEntry = table.getTeam(homeTeam);
        TableEntry awayTableEntry = table.getTeam(awayTeam);
        if(homeTableEntry ==null){
            homeTableEntry = new TableEntry(homeTeam,0);
            table.getTableEntries().add(homeTableEntry);
        }
        if(awayTableEntry ==null){
            awayTableEntry = new TableEntry(awayTeam,0);
            table.getTableEntries().add(awayTableEntry);
        }
        //home team wins
        if(goalDifference>0)
            homeTableEntry.setPoints(homeTableEntry.getPoints()+3);
        //away team wins
        else if(goalDifference<0)
            awayTableEntry.setPoints(awayTableEntry.getPoints()+3);
        //draw
        else{
            homeTableEntry.setPoints(homeTableEntry.getPoints()+1);
            awayTableEntry.setPoints(awayTableEntry.getPoints()+1);
        }
    }
}
