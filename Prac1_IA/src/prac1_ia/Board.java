package prac1_ia;

import IA.Energia.Clientes;
import IA.Energia.Centrales;
import java.util.ArrayList;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Board {

    private static Distance distance;

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
    private static ArrayList<ArrayList<Integer>> assignacions;

    /**
     * Array with the production left for each power Center
     */
    private ArrayList<Double> ProduccioRestant;

    /**
     * Constructor for an empty TruckSchedule
     * @param cen Support structure representing all distribution centers
     * @param cl Support structure representing all gas stations
     * @param assig Support structure representing all petitions
     * @param prod_res
     * Creates an empty truck schedule
     */
    public Board(Centrales cen, Clientes cl, ArrayList<ArrayList<Integer>> assig, ArrayList<Double> prod_res) {
        centrales = cen;
        clientes = cl;
        assignacions = assig;
        ProduccioRestant = prod_res;
        //initialize();
    }
    /*
    private void initialize(){
        distance = Distance.getInstance(this);
    }
     */
    
    public ArrayList<ArrayList<Integer>> getAssignacions() {
        return assignacions;
    }
    
    public ArrayList<Double> getProduccioRestant() {
        return ProduccioRestant;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public Centrales getCentrales() {
        return centrales;
    }
}
