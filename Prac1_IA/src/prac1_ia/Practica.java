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

        /*Scanner capt = new Scanner(System.in);
        System.out.print("Ingrese numero de centrales de tipo A\t: ");
        int nc_tipoA = capt.nextInt();

        System.out.print("Ingrese numero de centrales de tipo B\t: ");
        int nc_tipoB = capt.nextInt();

        System.out.print("Ingrese numero de centrales de tipo C\t: ");
        int nc_tipoC = capt.nextInt();

        int[] numero_centrales = {nc_tipoA,nc_tipoB,nc_tipoC};

        System.out.print("Ingrese numero de clientes\t: ");
        int numero_clientes = capt.nextInt();

        System.out.print("Ingrese proporción de clientes con consumo grande (numero entre 0 y 100) \t:");
        int p1_aux = capt.nextInt();
        double p1 = (double)p1_aux/100.0; 
        System.out.println(p1);

        System.out.print("Ingrese proporción de clientes con consumo muy grande (numero entre 0 y 100) \t:");
        int p2_aux = capt.nextInt();
        double p2 = (double)p2_aux/100.0; 
        System.out.println(p2);

        System.out.print("Ingrese proporción de clientes con consumo extra grande (numero entre 0 y 100) \t:");
        int p3_aux = capt.nextInt();
        double p3 = (double)p3_aux/100.0; 
        System.out.println(p3);

        System.out.print("Ingrese proporción de clientes con consumo servicio garantizadop (numero entre 0 y 100) \t:");
        double proporcion_prioridad = capt.nextDouble();

        double[] proporcion_tipos_clientes = {p1, p2, p3};*/
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
        distancias.CalculaDistancias();
        double[][] d = distancias.getDistancias();

        board = new Board(centrales,clientes);

        //imprimir_estado(board);

        Heuristic H = new Heuristic();
        SuccessorFunction S = new SuccesorFunctionEnergy();

        Problem problem =  new Problem(board,S, new GoalTestEnergy(),H);
        Search search =  new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem,search);
        Board goalState = (Board) search.getGoalState();
        
        imprimir_estado(goalState);
        
        /*System.out.print(goalState.getCentrales().get(30).getTipo());
        
        System.out.print(goalState.getClientes().get(367).getConsumo() + " " + d[39][367] + " ");
        System.out.println();
        System.out.print(goalState.getClientes().get(369).getConsumo() + " " + d[39][369] + " ");
        System.out.println();
        System.out.print(goalState.getClientes().get(371).getConsumo() + " " + d[39][371] + " ");
        System.out.println();
        System.out.print(goalState.getClientes().get(487).getConsumo() + " " + d[39][487] + " ");
        System.out.println();
        System.out.print("sum: " + goalState.getCentrales().get(39).getProduccion());
        */
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

    //Funcion auxiliar

    private static  void imprimir_estado(Object o){

        Board board = (Board) o;
        double[] prod = board.getProduccionRestante();
        assignaciones = board.getAssignaciones();

        for (int i = 0;i < assignaciones.size(); ++i) {
            System.out.print(i + ": ");
            for (int j = 0;j < assignaciones.get(i).size();++j) {
                System.out.print(assignaciones.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();

        for (int j = 0; j < prod.length; ++j) {
            System.out.println(j + ": " + prod[j] + " " + board.getCentrales().get(j).getProduccion());
        }
        System.out.println();
    }
}
