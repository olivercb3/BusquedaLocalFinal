/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prac1_ia;
import IA.Energia.*;

import java.util.*;


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

    
     public static Centrales centrales;
     public static Clientes clientes;
     public static VEnergia venergia;
     public static ArrayList<ArrayList<Integer>> table;
    public static double  [] produccion_restante;
     
     public static void InitialState() { //estat inicial on s'assignen els clients a les centrals a mesura que es van omplint.
        int indice_cliente = 0;
        produccion_restante = new double[centrales.size()];

        for (int i = 0; i < centrales.size(); ++i) {
            boolean max_superat = false;
            produccion_restante[i] = centrales.get(i).getProduccion();
            for (int j = indice_cliente; j < clientes.size() && max_superat == false; ++j) {
                if (clientes.get(j).getConsumo() < produccion_restante[i]) {
                    table.get(i).add(j);
                    produccion_restante[i] -= clientes.get(j).getConsumo();
                    ++indice_cliente;
                }
                else {
                    max_superat = true;
                }
            }
        }
        if (indice_cliente < clientes.size()) {
            for (int i = indice_cliente; i < clientes.size(); ++i) {
                table.get(centrales.size()).add(i);
            }
        }
     }
     
     
    public static void main(String[] args) throws Exception {

        int[] numero_centrales = {10,20,15};
        int numero_clientes = 30000;

        Random myRandom = new Random(100);
        myRandom.nextInt(1000);
        double p1 = myRandom.nextInt(1000) / 1000.0;
        double p2 = myRandom.nextInt((int)((1-p1) * 1000)) / 1000.0;
        double p3 = 1-p1-p2;

        double[] proporcion_tipos_clientes = {p1, p2, p3};
        double proporcion_prioridad = myRandom.nextInt(1000) / 1000.0;

        centrales = new Centrales(numero_centrales, 1);
        clientes = new Clientes(numero_clientes, proporcion_tipos_clientes, proporcion_prioridad, 291200) ;

        table = new ArrayList<>(centrales.size()+1);

        for(int i=0; i < clientes.size(); i++) {
            table.add(new ArrayList());
        }

        InitialState();


        for (int i = 0; i < table.size(); ++i) {
            System.out.print(i + ": ");
            for (int j = 0; j < table.get(i).size(); ++j) {
                System.out.print(table.get(i).get(j) + " ");
            }
        }

        System.out.println();

        System.out.println("posicion 1935 1:" + table.get(0).get(1935));
        System.out.println("posicion 1935 1:" + table.get(1).get(1935));

        System.out.println();

        System.out.println("posicion 1936 1:" + table.get(0).get(1936));
        System.out.println("posicion 1936 1:" + table.get(1).get(1936));

        System.out.println();

        for (int i = 0; i < table.size(); ++i) {
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

    private static void TSPSimulatedAnnealingSearch(Board board) {
        System.out.println("\nTSP Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(board,new SA_SuccessorFunction(), new LocalSearch_GoalTest(), new LocalSeach_HeuristicFunction());
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
    
    public static void runSimulation() {
        //
    }
}
