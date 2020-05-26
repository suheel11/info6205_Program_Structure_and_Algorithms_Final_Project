#Overview

Created a JAVA system to predict the EPL 2019-20 rankings. Used data from 2000-01 to 2019-20, predicted the remaining matches using probability 
density function and formed the final standings table. Given two teams xi and xj, the system evaluates P(xi, xj) where P(xi, xj) 
is the probability that xi would beat xj if they met in a head to head matchup at neutral territory

## Execution
1. Clone the repository `git clone https://github.com/suheel11/info6205_Program_Structure_and_Algorithms_Final_Project.git`.
2. Open project `EPLRankingSystem` in IDE.
3. Add jar `commons-math3-3.6.1.jar` to the build path. It's present under `Ranking-System/jar`.
4. main method is present in Class EPLRankingSystem
5. Compile and Run the program
6. Select Option 1 to predict the outcome between two teams
7. Select Option 2 to predict the final standings of EPL 2019-2020