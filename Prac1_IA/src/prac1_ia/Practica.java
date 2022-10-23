package prac1_ia;
import IA.Energia.*;
import java.util.*;


import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import com.opencsv.CSVWriter;
import java.io.FileWriter;

public class Practica {
    
    /**
     * Instancia de la clase Board.
     */
    private static Board board; 

    /**
     * Vector de las centrales.
     */
     public static Centrales centrales;
     
     /**
     * Vector de los clientes.
     */
     public static Clientes clientes;
     
     /**
     * Instancia de la clase Distance.
     */
     public static  Distance distancias;
     
    /**
     * Clase principal.
     * @param args argumentos main
     * @throws java.lang.Exception Si algo falla
     */
    public static void main(String[] args) throws Exception {

        generar_aleatorio();
        distancias = Distance.getInstance(centrales,clientes);
        board = new Board(centrales,clientes);
        imprimir_estado(board);
        HillClimbingSearch(board);
        //SimulatedAnnealingSearch(board);    
    }
    
    /**
     * Búsqueda Hill Climbing.
     * @param board Tablero de centrales y clientes.
     */
    private static void HillClimbingSearch(Board board) {
        System.out.println("\nHillClimbing  -->");
        try {
            Heuristic H = new Heuristic();
            SuccesorFunctionEnergy S = new SuccesorFunctionEnergy();
            S.setOperators(false, true, false, true);

            Problem problem =  new Problem(board,S, new GoalTestEnergy(),H);
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            Board goalState = (Board) search.getGoalState();

            imprimir_estado(goalState);

            printActions(agent.getActions());                 //Imprime todos los operadores usados
            printInstrumentation(agent.getInstrumentation()); //Imprimero el numero de nodos expandidos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecución del algoritmo de Simulated Annealing.
     * @param board Tablero de centrales y clientes.
     */
    private static void SimulatedAnnealingSearch(Board board) {
        System.out.println("\nSimulated Annealing  -->");
        try {

            //valores por defecto, esto cuando se llamen se definen
            int steps = 2000;
            int steps_cambioT = 100;
            int k = 5;
            double lamb = 0.001;

            Heuristic H = new Heuristic();
            SuccesorFunctionSimulatedAnnealing S = new SuccesorFunctionSimulatedAnnealing();
            

            Problem problem =  new Problem(board,S, new GoalTestEnergy(),H);
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(steps,steps_cambioT,k,lamb);
            SearchAgent agent = new SearchAgent(problem,search);
            Board goalState = (Board) search.getGoalState();

            imprimir_estado(goalState);

            printActions(agent.getActions());                 //Imprime todos los operadores usados
            printInstrumentation(agent.getInstrumentation()); //Imprimero el numero de nodos expandidos
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Printa los nodos que ha expandido
     * @param properties parametro necesario para la ejecución de la función.
     */
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        } 
    }

    /**
     * Printa los operadores que ha ido aplicando.
     * @param actions Acciones en cuestión.
     */
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

    /**
     * Impresión del estado por terminal.
     * @param o Tablero de centrales y clientes.
     */
    private static  void imprimir_estado(Object o){

        Board board = (Board) o;
        double[] prod = board.getProduccionRestante();

        for (int i = 0;i < board.getAssignaciones().size(); ++i) {
            System.out.print(i + ": ");
            for (int j = 0;j < board.getAssignaciones().get(i).size();++j) {
                System.out.print(board.getAssignaciones().get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();

        for (int j = 0; j < prod.length; ++j) {
            System.out.println(j + ": " + prod[j] + " " + board.getCentrales().get(j).getProduccion());
        }
        System.out.println();
    }
    
    /**
     * Función con los inputs aleatorios a través de la terminal.
     */
    private static void generar_aleatorio() throws Exception{

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
    }
    
    /**
     * Función con los inputs a mano a través de la terminal.
     */
    private static void generar_manual() throws Exception {

        Scanner capt = new Scanner(System.in);
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

        double[] proporcion_tipos_clientes = {p1, p2, p3};

        centrales = new Centrales(numero_centrales, 1);
        clientes = new Clientes(numero_clientes, proporcion_tipos_clientes, proporcion_prioridad, 291200);
    }
}
