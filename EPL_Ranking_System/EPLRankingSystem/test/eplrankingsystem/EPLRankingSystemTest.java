/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eplrankingsystem;

import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author suhee
 */
public class EPLRankingSystemTest {
    
    public EPLRankingSystemTest() {
    }
    
    @Test
    public void testcase1() {
        System.out.println("testcase1");
        String homeTeam = "Liverpool";
        String awayTeam = "Everton";
        boolean printProbability = false;
        int expResult = 1;
        EPLRankingSystem.loadData();
        int result = EPLRankingSystem.findWinner(homeTeam, awayTeam, printProbability);
        System.out.println("HomeTeam :"+homeTeam+"AwayTeam :"+awayTeam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testcase2(){
        System.out.println("testcase2");
        String homeTeam = "Everton";
        String awayTeam = "Liverpool";
        boolean printProbability = false;
        int expResult = -1;
        EPLRankingSystem.loadData();
        int result = EPLRankingSystem.findWinner(homeTeam, awayTeam, printProbability);
        System.out.println("HomeTeam :"+homeTeam+"AwayTeam :"+awayTeam);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testcase3(){
        System.out.println("testcase3");
        String homeTeam = "Wolves";
        String awayTeam = "Man City";
        boolean printProbability = false;
        int expResult = -1;
        EPLRankingSystem.loadData();
        int result = EPLRankingSystem.findWinner(homeTeam, awayTeam, printProbability);
        System.out.println("HomeTeam :"+homeTeam+"AwayTeam :"+awayTeam);
        assertEquals(expResult, result);
    }
    
}
