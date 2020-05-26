/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eplrankingsystem;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author suhas
 */
public class Team {
    private String teamName;
    //pdfs with key being the away team value being pdfs calculated
    private Map<String, ProbabilityDensityFunction> pdfs;
    private double avgMean;
    private double avgSd;
    
    public Team(String teamName){
        this.teamName = teamName;
        pdfs = new HashMap<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Map<String, ProbabilityDensityFunction> getPdfs() {
        return pdfs;
    }

    public void setPdfs(Map<String, ProbabilityDensityFunction> pdfs) {
        this.pdfs = pdfs;
    }
    //Calculate Mean and SD for all the awayteams
    public void calculateTeamStats(){
        for(Map.Entry map:pdfs.entrySet()){
            ProbabilityDensityFunction pdf = (ProbabilityDensityFunction)map.getValue();
            pdf.calculateTeamStats();
        }
        calculateAvgMean();
    }

    public double getAvgMean() {
        return avgMean;
    }

    public void setAvgMean(double avgmean) {
        this.avgMean = avgmean;
    }

    public double getAvgSd() {
        return avgSd;
    }

    public void setAvgSd(double avgSd) {
        this.avgSd = avgSd;
    }
    
    public void calculateAvgMean(){
        int count=0;
        double avgMeanPdf =0;
        for(Map.Entry map:pdfs.entrySet()){
            count++;
            ProbabilityDensityFunction pdf = (ProbabilityDensityFunction)map.getValue();
            avgMeanPdf+=pdf.getMean();
        }
        this.avgMean=avgMeanPdf/count;
    }
}
