package prac1_ia;
import IA.Energia.*;
import java.util.*;


import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Practica {
    
    private static Board board; 
    private static ArrayList<ArrayList<Integer>> assignaciones; 

     public static Centrales centrales;
     public static Clientes clientes;
     public static ArrayList<ArrayList<Integer>> assignacions;
     public static  Distance distancias;
     
    public static void main(String[] args) throws Exception {

        Random myRandom = new Random(4388);

        int nc_tipoA = 5; //myRandom.nextInt(5);
        int nc_tipoB = 10; //myRandom.nextInt(10);
        int nc_tipoC = 25;//myRandom.nextInt(25);

        int[] numero_centrales = {nc_tipoA,nc_tipoB,nc_tipoC};
        int numero_clientes = 1000;//myRandom.nextInt(1000);


        myRandom.nextInt(1000);
        double p1 = 0.25;//myRandom.nextInt(1000) / 1000.0;
        double p2 = 0.3;//myRandom.nextInt((int)((1-p1) * 1000)) / 1000.0;
        double p3 = 1-p1-p2;

        double[] proporcion_tipos_clientes = {p1, p2, p3};
        double proporcion_prioridad = 0.75;//myRandom.nextInt(1000) / 1000.0;

        centrales = new Centrales(numero_centrales, 1);
        clientes = new Clientes(numero_clientes, proporcion_tipos_clientes, proporcion_prioridad, 291200);

        distancias = Distance.getInstance(centrales,clientes);

        board = new Board(centrales,clientes);
        double[] prod = board.getProduccionRestante(); 
        /*for (int j = 0; j < prod.length; ++j) {
                System.out.print(prod[j] + " ");
            }
        
        assignaciones = board.getAssignaciones();
                
        for (int i = 0;i < assignaciones.size(); ++i) {
        System.out.print(i + ": "); 
            for (int j = 0;j < assignaciones.get(i).size();++j) {
                System.out.print(assignaciones.get(i).get(j) + " "); 
            }
        System.out.println(i); 
        }
        
        System.out.println(); */
        double[][] d = distancias.getDistancias();
        System.out.print(d[39][367]);
        Heuristic H = new Heuristic();
        SuccessorFunction S = new SuccesorFunctionEnergy();

        Problem problem =  new Problem(board,S, new GoalTestEnergy(),H);
        Search search =  new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem,search);
        Board goalState = (Board) search.getGoalState();
        
        ArrayList<ArrayList<Integer>> assignaciones = goalState.getAssignaciones(); 
        
        /*for (int i = 0;i < assignaciones.size(); ++i) {
        System.out.print(i + ": "); 
            for (int j = 0;j < assignaciones.get(i).size();++j) {
                System.out.print(assignaciones.get(i).get(j) + " "); 
            }
        //System.out.println(); 
        }
        /*for (int i = 0; i < goalState.getProduccionRestante().length; ++i) {
            System.out.print(i + ": " + goalState.getProduccionRestante()[i] + " " + goalState.getCentrales().get(i).getProduccion());
             System.out.println();
        }*/
        
        //System.out.print(goalState.getCentrales().get(30).getTipo());
        /*double[][] d = distancias.getDistancias();
        
        System.out.print(goalState.getClientes().get(367).getConsumo() + " " + d[39][367] + " ");
        System.out.println();
        System.out.print(goalState.getClientes().get(369).getConsumo() + " " + d[39][367] + " ");
        System.out.println();
        System.out.print(goalState.getClientes().get(371).getConsumo() + " " + d[39][367] + " ");
        System.out.println();
        System.out.print(goalState.getClientes().get(487).getConsumo() + " " + d[39][367] + " ");
        System.out.println();
        System.out.print("sum: " + goalState.getCentrales().get(39).getProduccion());*/
        
        
        
    }
    
    private static void TSPHillClimbingSearch(Board TSPB) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem =  new Problem(TSPB,new SuccesorFunctionEnergy(), new GoalTestEnergy(),new Heuristic());
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
            Problem problem =  new Problem(board,new SuccesorFunctionEnergy(), new GoalTestEnergy(), new Heuristic());
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

    }
}
