package prac1_ia;
import IA.Energia.*;
import java.util.*;


import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Practica {
    
    private static Board board; 
    private static ArrayList<ArrayList<Integer>> assignaciones; 
     
    public static void main(String[] args) throws Exception {

        int[] numero_centrales = {10,20,15};
        int numero_clientes = 3000;

        Random myRandom = new Random(numero_clientes);
        myRandom.nextInt(1000);
        double p1 = myRandom.nextInt(1000) / 1000.0;
        double p2 = myRandom.nextInt((int)((1-p1) * 1000)) / 1000.0;
        double p3 = 1-p1-p2;

        double[] proporcion_tipos_clientes = {p1, p2, p3};
        double proporcion_prioridad = myRandom.nextInt(1000) / 1000.0;

        Centrales centrales = new Centrales(numero_centrales, 1);
        Clientes clientes = new Clientes(numero_clientes, proporcion_tipos_clientes, proporcion_prioridad, 291200);

        
        board = new Board(centrales,clientes);
        
        assignaciones = board.getAssignaciones();

        for (int i = 0; i < assignaciones.size(); ++i) {
            System.out.print(i + ": ");
            for (int j = 0; j < assignaciones.get(i).size(); ++j) {
                System.out.print(assignaciones.get(i).get(j) + " ");
            }
        }
        System.out.println();
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
