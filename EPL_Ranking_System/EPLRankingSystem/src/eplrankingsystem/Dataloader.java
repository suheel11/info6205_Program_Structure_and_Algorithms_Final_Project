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

/**
 *
 * @author suhee
 */
public class Dataloader {
    public void getValues(URL url){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        TeamDirectory teamDirectory = TeamDirectory.getInstance();

        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] record = line.split(cvsSplitBy);
                String homeTeam = record[2].toLowerCase(); //get home team name
                String awayTeam = record[3].toLowerCase();  //get away team name
                int goalDifference = Integer.parseInt(record[4])-Integer.parseInt(record[5]); //get goal difference
                // get home team object, if not present create one
                Team team = teamDirectory.getTeam(homeTeam);
                if(team ==null){
                    team = new Team(homeTeam);
                    teamDirectory.getTeamList().add(team);
                }
                /*get the pdf between home team vs away team, find the entry with this particular goalDifferrence, 
                if found increase the count else create new entry*/
                ProbabilityDensityFunction pdf = team.getPdfs().get(awayTeam);
                if(pdf!=null){
                    if(pdf.getOccurence().get(goalDifference) == null)
                        pdf.getOccurence().put(goalDifference, 1);
                    else
                        pdf.getOccurence().put(goalDifference,pdf.getOccurence().get(goalDifference)+1);
                }
                // if pdf not found, create one
                else{
                    pdf = new ProbabilityDensityFunction();
                    pdf.getOccurence().put(goalDifference, 1);
                    team.getPdfs().put(awayTeam, pdf);
                }
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
    
}
