/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eplrankingsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author suhee
 */
public class EPLRankingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //read data from cvs files and load data into objects
        loadData();
        Scanner sc= new Scanner(System.in);
        int option = 0;
        while(option!=3){
            String homeTeam,awayTeam;
            System.out.println("Select one of the choices");
            System.out.println("1.Predict result between two teams");
            System.out.println("2.Predict EPL Standings for 2019-2020");
            System.out.println("3.Exit");
            option = sc.nextInt();
        
            switch(option){
                case 1: System.out.println("Enter two teams (1st team entered will be considered as the home team)");
                           sc.nextLine();
                           homeTeam=sc.nextLine();
                           awayTeam=sc.nextLine();
                           int value = findWinner(homeTeam,awayTeam,true);
                            switch (value) {
                                case 0:
                                    System.out.println("Match will be draw");
                                    break;
                                case 1:
                                    System.out.println(homeTeam+" will win the match");
                                    break;
                                default:
                                    System.out.println(awayTeam+" will win the match");
                                    break;
                            }
                           break;
                case 2: predictCurrentSeasonRankings();
                            break;
                case 3 : break;
                default: System.out.println("Enter valid choice");;
             }
        }
    }
    
    //retuns 1 if home team wins, -1 if away team wins, 0 if draw
    public static int findWinner(String homeTeam,String awayTeam,boolean printProbability){
        TeamDirectory teamDirectory = TeamDirectory.getInstance();
        teamDirectory.calculateTeamStats();
        Team team1 = teamDirectory.getTeam(homeTeam.toLowerCase());
        Team team2 = teamDirectory.getTeam(awayTeam.toLowerCase());
        //if both teams never plaved in epl before
        if(team1 == null && team2 ==null){
            return 0;
        }
        //if team1 is new team, predict the result based on team2's avrg mean against all oppenents
        if(team1==null){
            if(team2.getAvgMean()>=1)
                return -1;
            else if(team2.getAvgMean()<=-1)
                return 1;
            else
                return 0;
        }
        //if team2 is new team, predict the result based on team1's avrg mean against all oppenents
        if(team2==null){
            if(team1.getAvgMean()>=1)
                return 1;
            else if(team1.getAvgMean()<=-1)
                return -1;
            else
                return 0;
        }
        ProbabilityDensityFunction pdf = team1.getPdfs().get(awayTeam.toLowerCase());
        //if the teams havent played against each other ever in epl but have played against other teams
        if(pdf==null){
            if((team1.getAvgMean()-team2.getAvgMean())>1)
                return 1;
            else if ((team2.getAvgMean()-team1.getAvgMean())>1)
                return -1;
            else 
                return 0;
        }
        double mean = pdf.getMean();
        double sd = pdf.getSd();
        //NormalDistribution functions fail if SD is 0, so intializing it with small value
        if(sd==0)
            sd=0.001;
        NormalDistribution d = new NormalDistribution(mean, sd);
        double winningProbability = d.probability(1, 99); //Area under curve between 1 to 99
        double drawProbability = d.probability(-1, 1); //Area under curve between -1 to 1
        double losingProbabaility = d.probability(-99, -1); //Area under curve between -99 to -1
        //return 1 if home team winning probabality is more
        if(winningProbability>drawProbability && winningProbability>losingProbabaility){
            if(printProbability)
                System.out.println("Winning Probability "+winningProbability);
            return 1;
        }
        //return 0 if draw probabality is more
        else if(drawProbability>losingProbabaility){
            if(printProbability)
                System.out.println("Draw Probability "+drawProbability);
            return 0;
        }
        //return -1 if home team winning probabality is more
        else{
            if(printProbability)
                System.out.println("Losing Probability "+losingProbabaility);
            return -1;
        }
    }
    
    public static void predictCurrentSeasonRankings(){
        //read results from the curent season and update table
        String s ="2019-2020.csv";
        String filePath="files/"+s;
        URL f=Thread.currentThread().getContextClassLoader().getResource(filePath);
        createTableForPlayedMatches(f);
        //get remaining fixtures from csv file
        s ="RemainingFixtures.csv";
        filePath="files/"+s;
        f=Thread.currentThread().getContextClassLoader().getResource(filePath);
        predictRemainingMatches(f);
    }
    
    //read results from the curent season and update table
    public static void createTableForPlayedMatches(URL url){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Table table = Table.getInstance();

        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] record = line.split(cvsSplitBy);
                String homeTeam = record[2].toUpperCase();
                String awayTeam = record[3].toUpperCase();
                int goalDifference = Integer.parseInt(record[4])-Integer.parseInt(record[5]);
                table.updateTable(homeTeam, awayTeam, goalDifference);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    //fetch reamaining matches from csv files and predict the winner for each match and update table
    public static void predictRemainingMatches(URL url){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Table table = Table.getInstance();

        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null) {
                String[] record = line.split(cvsSplitBy);
                String homeTeam = record[0].toUpperCase();
                String awayTeam = record[1].toUpperCase();
                int goalDifference = findWinner(homeTeam,awayTeam,false);
                table.updateTable(homeTeam, awayTeam, goalDifference);
            }
            //sort table based on points scored by the teams
            Collections.sort(table.getTableEntries());
            table.printTable();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    //read data from cvs files and load data into objects 
    public static void loadData(){
        List<String> l = new ArrayList<>();
         l.add("2000-2001.csv");
         l.add("2001-2002.csv");
         l.add("2002-2003.csv");
         l.add("2003-2004.csv");
         l.add("2004-2005.csv");
         l.add("2005-2006.csv");
         l.add("2006-2007.csv");
         l.add("2007-2008.csv");
         l.add("2008-2009.csv");
         l.add("2009-2010.csv");
         l.add("2010-2011.csv");
         l.add("2011-2012.csv");
         l.add("2012-2013.csv");
         l.add("2013-2014.csv");
         l.add("2014-2015.csv");
         l.add("2015-2016.csv");
         l.add("2016-2017.csv");
         l.add("2017-2018.csv");
         l.add("2018-2019.csv");
         l.add("2019-2020.csv");
         Dataloader dataloader =new Dataloader();
         for(int i=0;i<l.size();i++){
             String filePath="files/"+l.get(i);
             URL f=Thread.currentThread().getContextClassLoader().getResource(filePath);
            dataloader.getValues(f);
         }
    }
}