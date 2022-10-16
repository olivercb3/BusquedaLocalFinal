package prac1_ia;

import IA.Energia.Clientes;
import IA.Energia.Centrales;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Board {

    /**
     * List with all the clients
     */
    private static Clientes clientes;
    /**
     * List with all the centrals
     */
    private static Centrales centrales;
    /**
     * All Client to power Centers Assignments
     */
    private static ArrayList<ArrayList<Integer>> assignaciones;

    /**
     * Array with the production left for each power Center
     */
    private static double [] produccionRestante;

    /**
     * Constructor for an empty Board
     * @param cen Support structure representing all distribution centers
     * @param cl Support structure representing all gas stations
     * Creates an empty board
     */
    public Board(Centrales cen, Clientes cl) {
        centrales = cen;
        clientes = cl;
        assignaciones = null;
        produccionRestante = null; 
        InitialState(); 
    }
    
    private static void InitialState() {
        int indice_cliente = 0;
        produccionRestante = new double[centrales.size()];

        for (int i = 0; i < centrales.size(); ++i) {
            boolean max_superat = false;
            produccionRestante[i] = centrales.get(i).getProduccion();
            for (int j = indice_cliente; j < clientes.size() && max_superat == false; ++j) {
                if (clientes.get(j).getConsumo() < produccionRestante[i]) {
                    assignaciones.get(i).add(j);
                    produccionRestante[i] -= clientes.get(j).getConsumo();
                    ++indice_cliente;
                }
                else {
                    max_superat = true;
                }
            }
        }
        if (indice_cliente < clientes.size()) {
            for (int i = indice_cliente; i < clientes.size(); ++i) {
                assignaciones.get(centrales.size()).add(i);
            }
        }
     }

    
    public ArrayList<ArrayList<Integer>> getAssignaciones() {
        return assignaciones;
    }
    
    public double[] getProduccionRestante() {
        return produccionRestante;
    }
    
    public Centrales getCentrales() {
        return centrales; 
    }
    
    public Clientes getClientes() {
        return clientes; 
    }
}
