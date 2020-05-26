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
public class ProbabilityDensityFunction {
    private double mean;
    private double sd;
    Map<Integer,Integer> occurence;
    Map<Integer,Double> probability;

    public ProbabilityDensityFunction() {
        occurence = new HashMap();
        probability = new HashMap();
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getSd() {
        return sd;
    }

    public void setSd(double sd) {
        this.sd = sd;
    }

    public Map<Integer, Integer> getOccurence() {
        return occurence;
    }

    public void setOccurence(Map<Integer, Integer> occurence) {
        this.occurence = occurence;
    }

    public Map<Integer, Double> getProbility() {
        return probability;
    }

    public void setProbility(Map<Integer, Double> probility) {
        this.probability = probility;
    }
    
    //get values from occurence map and calculate the probability for each goal difference 
    public void calculateProbability(){
        int totalMatches = occurence.values().stream().reduce(0, Integer::sum);
        for(Map.Entry map:occurence.entrySet()){
            int goalDifference = (int) map.getKey();
            int noOfMatches = (int) map.getValue();
            double prob = (double) noOfMatches/totalMatches;
            probability.put(goalDifference, prob);
        }
    }
    
    //calculate mean for the pdf
    public void calculateMean(){
        int totalMatches = occurence.values().stream().reduce(0, Integer::sum);
        double totalGoals =0;
        for(Map.Entry map:occurence.entrySet()){
            int goalDifference = (int) map.getKey();
            int noOfMatches = (int) map.getValue();
            totalGoals+= goalDifference*noOfMatches;
        }
        this.mean = totalGoals/totalMatches;
    }
    
    //calculate sd for the pdf
    public void caclulateSD(){
        int totalMatches = occurence.values().stream().reduce(0, Integer::sum);
        double difference=0;
        for(Map.Entry map:occurence.entrySet()){
            int goalDifference = (int) map.getKey();
            int noOfMatches = (int) map.getValue();
            difference+=Math.pow(goalDifference-mean,2)*noOfMatches;
        }
        double variance = difference/(totalMatches-1);
        this.sd = Math.sqrt(variance);
    }
    
    public void calculateTeamStats(){
        calculateProbability();
        calculateMean();
        caclulateSD();
    }
}
