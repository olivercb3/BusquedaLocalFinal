/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import aima.search.framework.HeuristicFunction;
<<<<<<< HEAD:Prac1_IA/src/prac1_ia/Heuristic.java

import java.util.ArrayList;

=======
>>>>>>> 00665430942d3e6b576be2bda6958db68c1419da:Prac1_IA/src/prac1_ia/LocalSeach_HeuristicFunction.java
/**
 *
 * @author olivercemelibarron
 */
<<<<<<< HEAD:Prac1_IA/src/prac1_ia/Heuristic.java
public abstract class Heuristic implements HeuristicFunction {
    
    private int[][] Asignaciones;
    
    @Override
    public double getHeuristicValue(Object state) {
        this.Asignaciones = (int[][]) state;
        return heuristicValue();//negative value, searches for minimum
    }
    
    public double heuristicValue() {
        int sum = 0;
        for (int i = 0; i < Asignaciones[1].length; ++i) {
            if(Asignaciones[i][1] == Asignaciones[1].length);
            else {
                
            }   
        }    
    }
    
=======
public class LocalSeach_HeuristicFunction implements HeuristicFunction {


    public double getHeuristicValue(Object o) {
        return 0;
    }
>>>>>>> 00665430942d3e6b576be2bda6958db68c1419da:Prac1_IA/src/prac1_ia/LocalSeach_HeuristicFunction.java
}
