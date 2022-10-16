package prac1_ia;

import IA.Energia.Clientes;
import IA.Energia.Centrales;
import java.util.ArrayList;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Board {

    private static DistanceCalculator distanceCalculator;

    /**
     * List with all the clients
     */
    private static Clientes clientes;
    /**
     * List with all the power centers
     */
    private static Centrales centrales;
    /**
     * All Client to power Centers Assignments
     */
    private static ArrayList<Integer> assignacions;

    /**
     * Array with the production left for each power Center
     */
    private ArrayList<Integer> ProduccioRestant;

    /**
     * Constructor for an empty TruckSchedule
     * @param dc Support structure representing all distribution centers
     * @param gs Support structure representing all gas stations
     * @param p Support structure representing all petitions
     * Creates an empty truck schedule
     */
    public Board(Centrales cen, Clientes cl, ArrayList<Integer> assig, ArrayList<Integer> prod_res) {
        centrales = cen;
        clientes = cl;
        assignacions = assig;
        ProduccioRestant = prod_res;
        initialize();
    }
    
    private void initialize(){
        distanceCalculator = DistanceCalculator.getInstance(this);
    }
}
