/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prac1_ia;
import IA.Energia.*;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;


import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

/**
 *
 * @author olivercemelibarron
 */
public class Practica {

    
     private static Centrales centrales; 
     private static Clientes clientes; 
     private static VEnergia venergia;
     private static ArrayList<ArrayList<Integer>> table;
     private static ArrayList<Double> ProduccioRestant;
     
     
     public static void InitialState() { //estat inicial on s'assignen els clients a les centrals a mesura que es van omplint.
        int indice_cliente = 0;
        for (int i = 0; i < centrales.size(); ++i) {
            boolean max_superat = false;
            double produccion_restante = ProduccioRestant.get(i);
            for (int j = indice_cliente; j < clientes.size() && max_superat == false; ++j) {
                if (clientes.get(j).getConsumo() < produccion_restante) {
                    table.get(i).add(j);
                    produccion_restante -= clientes.get(j).getConsumo();
                    ++indice_cliente;
                }
                else {
                    max_superat = true;
                }
                ProduccioRestant.set(i, produccion_restante);
            }
        }
        if (indice_cliente < clientes.size()) {
            for (int i = indice_cliente; i < clientes.size(); ++i) {
                table.get(centrales.size()).add(i);
            }
        }
     }
     
     
    public static void main(String[] args) throws Exception {
        
        int[] ia = {10,20,15}; 
        double[] cl = {0.2, 0.3, 0.5}; 
        
        centrales = new Centrales(ia,1);
        clientes = new Clientes(600, cl, 0.5, 291200) ;
        
        table = new ArrayList<>(centrales.size()+1);
        ProduccioRestant = new ArrayList<>();
        
        for(int i=0; i < clientes.size(); i++) {
            table.add(new ArrayList());
        }
        for(int i=0; i < centrales.size(); i++) {
            ProduccioRestant.add(centrales.get(i).getProduccion()); 
        }
        
        InitialState();
        
        
        for (int i = 0; i < table.size(); ++i) {
            System.out.print(i + ": ");
            for (int j = 0; j < table.get(i).size(); ++j) {
                System.out.print(table.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    private static void TSPHillClimbingSearch(Board TSPB) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem =  new Problem(TSPB,new HillClimbing_SuccesorFunction(), new LocalSearch_GoalTest(),new LocalSeach_HeuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void TSPSimulatedAnnealingSearch(Board TSPB) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(TSPB,new SA_SuccessorFunction(), new LocalSearch_GoalTest(), new LocalSeach_HeuristicFunction());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(2000,100,5,0.001);
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}
