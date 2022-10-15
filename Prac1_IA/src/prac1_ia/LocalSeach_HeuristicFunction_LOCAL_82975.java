/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;

/**
 *
 * @author olivercemelibarron
 */
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
    
}
