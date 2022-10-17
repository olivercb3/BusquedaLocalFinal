package prac1_ia;

import IA.Energia.Clientes;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
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
                assignaciones.get(centrales.size()-1).add(i);
            }
        }
     }
    
    //compruebo que el consumo no se pase del tope
    public void add(int central,int cliente) {
        double restante = produccionRestante[central]; 
        int indice_cliente = assignaciones.get(assignaciones.size()-1).get(cliente); 
        Cliente c = clientes.get(indice_cliente);
        if (restante > c.getConsumo()) { //comprobacion de que se pueda hacer el add
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central).add(indice_cliente); 
            //lo borro de la central nula
            assignaciones.get(assignaciones.size()-1).remove(cliente); 
        }
    }
    
    //en el remove creo que no hay ninguna condicion concreta, confirmadmelo
    public void remove(int central,int cliente) {
        assignaciones.get(central).remove(cliente); 
        assignaciones.get(assignaciones.size()-1).add(cliente); 
    }
    
    //es el cambio de dos clientes en activo
    public void interchange(int central1,int central2,int cliente1,int cliente2) {
        double restanteCentral1 = produccionRestante[central1]; 
        double restanteCentral2 = produccionRestante[central2]; 
        Cliente c1 = clientes.get(cliente1);
        Cliente c2 = clientes.get(cliente2); 
        if (restanteCentral1 > c1.getConsumo() && restanteCentral2 > c2.getConsumo()) {
            //PRIMER CLIENTE-CENTRAL
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central1).add(cliente2); 
            //lo borro de la central
            assignaciones.get(central2).remove(cliente2); 
            
            //SEGUNDO CLIENTE-CENTRAL
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central2).add(cliente1); 
            //lo borro de la central
            assignaciones.get(central1).remove(cliente1); 
        }
    }
    
    //cambio de un cliente en activo por uno inactivo
    public void swap(int central,int clienteAssignado,int clienteNoAssignado) {
        double restanteCentral1 = produccionRestante[central]; 
        Cliente c = clientes.get(clienteNoAssignado);
        if (restanteCentral1 > c.getConsumo()) {
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central).add(clienteNoAssignado); 
            //lo borro de la central
            assignaciones.get(assignaciones.size()-1).remove(clienteNoAssignado); 
            
            assignaciones.get(assignaciones.size()-1).add(clienteAssignado); 
            assignaciones.get(central).remove(clienteAssignado); 
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
