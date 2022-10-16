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
     public static ArrayList<ArrayList<Integer>> assignacions;
     public static  Distance distancias;

    //estat inicial on s'assignen els clients a les centrals a mesura que es van omplint.
     public static void InitialState(int n_centrales, int n_clientes) {

        int indice_central = 0;
        double [] produccion_restante = new double[centrales.size()];

        for (int i = indice_central; i < centrales.size(); ++i) {

            boolean max_superat = false;
            int indice_clientes = 0;
            produccion_restante[i] = centrales.get(i).getProduccion();
            for (int j = indice_clientes; j < clientes.size() && max_superat == false; ++j) {

                Cliente cl = clientes.get(j);
                if (cl.getContrato() == 0 && cl.getConsumo() < produccion_restante[i]) {
                    assignacions.get(i).add(j);
                    produccion_restante[i] -= clientes.get(j).getConsumo();
                    ++indice_clientes;
                }
                else {
                    max_superat = true;
                    ++indice_central;
                }
            }
        }

        for (int i = 0; i < clientes.size(); ++i)
                assignacions.get(centrales.size()).add(i);

         //En este punto es solucion

         for (int i = indice_central; i < centrales.size(); ++i) {

             boolean max_superat = false;
             int indice_clientes = 0;
             for (int j = indice_clientes; j < clientes.size() && max_superat == false; ++j) {

                 Cliente cl = clientes.get(j);
                 if (cl.getContrato() == 1 && cl.getConsumo() < produccion_restante[i]) {
                     assignacions.get(i).add(j);
                     produccion_restante[i] -= clientes.get(j).getConsumo();
                     ++indice_clientes;
                 }
                 else {
                     max_superat = true;
                     ++indice_central;
                 }
             }
         }
     }
     
     
    public static void main(String[] args) throws Exception {

        Random myRandom = new Random(4388);

        int nc_tipoA = myRandom.nextInt(50);
        int nc_tipoB = myRandom.nextInt(50);
        int nc_tipoC = myRandom.nextInt(50);

        int[] numero_centrales = {nc_tipoA,nc_tipoB,nc_tipoC};
        int numero_clientes = myRandom.nextInt(5000);


        myRandom.nextInt(1000);
        double p1 = myRandom.nextInt(1000) / 1000.0;
        double p2 = myRandom.nextInt((int)((1-p1) * 1000)) / 1000.0;
        double p3 = 1-p1-p2;

        double[] proporcion_tipos_clientes = {p1, p2, p3};
        double proporcion_prioridad = myRandom.nextInt(1000) / 1000.0;

        centrales = new Centrales(numero_centrales, 1);
        clientes = new Clientes(numero_clientes, proporcion_tipos_clientes, proporcion_prioridad, 291200);

        assignacions = new ArrayList<>(centrales.size()+1);
        int n_centrales = numero_centrales[0] + numero_centrales[1] + numero_centrales[2];

        distancias = Distance.getInstance(n_centrales, numero_clientes);
        distancias.CalculaDistancies(centrales, clientes);

        InitialState(n_centrales, numero_clientes);


        for (int i = 0; i < assignacions.size(); ++i) {
            System.out.print(i + ": ");
            for (int j = 0; j < assignacions.get(i).size(); ++j) {
                System.out.print(assignacions.get(i).get(j) + " ");
            }
        }

        System.out.println();
        

        /*System.out.println("posicion 1935 1:" + table.get(0).get(1935));
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
        }*/
    }

    private static void TSPHillClimbingSearch(Board TSPB) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem =  new Problem(TSPB,new HillClimbing_SuccesorFunction(), new LocalSearch_GoalTest(),new Heuristic());
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
            Problem problem =  new Problem(board,new SA_SuccessorFunction(), new LocalSearch_GoalTest(), new Heuristic());
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
