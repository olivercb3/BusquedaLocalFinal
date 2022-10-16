package Greedy;
import IA.Energia.*;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;


import aima.search.framework.*;
import aima.search.informed.GreedyBestFirstSearch;

import static prac1_ia.Practica.centrales;
import static prac1_ia.Practica.clientes;

public class GreedySolution {

    public static void main(String[] args) throws Exception {
        int[] numero_centrales = {10, 20, 15};
        int numero_clientes = 30000;

        Random myRandom = new Random(numero_clientes);
        myRandom.nextInt(1000);
        double p1 = myRandom.nextInt(1000) / 1000.0;
        double p2 = myRandom.nextInt((int)((1-p1) * 1000)) / 1000.0;
        double p3 = 1-p1-p2;

        double[] proporcion_tipos_clientes = {p1, p2, p3};
        double proporcion_prioridad = myRandom.nextInt(1000) / 1000.0;

        centrales = new Centrales(numero_centrales, 1);
        clientes = new Clientes(numero_clientes, proporcion_tipos_clientes, proporcion_prioridad, 291200);

        int n_centrales = numero_centrales[0] + numero_centrales[1] + numero_centrales[2];

        GreedyBoard board = new GreedyBoard(n_centrales, numero_clientes);
        GreedyBestSearch(board);
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

    private static void GreedyBestSearch(GreedyBoard board) {
        try {
            Problem problem =  new Problem(board,new Greedy_SuccesorFunction(), new Greedy_GoalTest(),new Greedy_HeuristicFunction());
            Search search =  new GreedyBestFirstSearch(new TreeSearch());
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
