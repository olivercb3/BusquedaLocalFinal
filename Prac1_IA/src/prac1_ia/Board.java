package prac1_ia;

import IA.Energia.Clientes;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

import java.util.ArrayList;

import static prac1_ia.Practica.distancias;
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
    private ArrayList<ArrayList<Integer>> assignaciones;

    /**
     * Array with the production left for each power Center
     */
    private double [] produccionRestante;

    /**
     * Constructor for an empty Board
     * @param cen Support structure representing all distribution centers
     * @param cl Support structure representing all gas stations
     * Creates an empty board
     */
    public Board(Centrales cen, Clientes cl) {
        centrales = cen;
        clientes = cl;
        assignaciones = new ArrayList<ArrayList<Integer>>(cen.size()+1);
        produccionRestante = new double[cen.size()];
        //Ponemos todos los clientes en la central vacia
        for (int i = 0; i < clientes.size(); ++i) {
            assignaciones.get(centrales.size()).add(i);
        }
        InitialState(); 
    }

    private void InitialState_Greedy() {

        for (int j = 0; j < clientes.size(); ++j) {

            Cliente cl = clientes.get(j);
            double[][] d = distancias.getDistancias();
            if (cl.getContrato() == 0) {

                double [] centrales_cliente = getColumn(d,j, clientes.size());
                int min_distance = index_minimumArray(centrales_cliente);
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[min_distance][j]);

                if (consumo < produccionRestante[min_distance]) {
                    produccionRestante[min_distance] -= consumo;
                    assignaciones.get(min_distance).add(j);
                }
                else {
                    boolean encontrado = false;
                    for (int i = 0; encontrado = true && i < centrales.size(); ++i) {

                        if(produccionRestante[i] < consumo) {
                            produccionRestante[i] -= consumo;
                            assignaciones.get(i).add(j);
                        }
                    }
                }
            }
        }
        //En este punto es solucion
        for (int i = 0; i < centrales.size(); ++i) {

            boolean max_superat = false;
            double[][] d = distancias.getDistancias();
            double media = meanArray(d[i]);
            for (int j = 0; j < clientes.size() && max_superat == false; ++j) {

                Cliente cl = clientes.get(j);
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[i][j]);
                double dis = d[i][j];
                if (cl.getContrato() == 1 && consumo < produccionRestante[i] && dis < media) {
                    assignaciones.get(i).add(j);
                    produccionRestante[i] -= consumo;
                }
                else
                    max_superat = true;
            }
        }
    }

    private void InitialState() {

        int indice_central = 0;
        for (int i = 0; i < centrales.size(); ++i) {

            boolean max_superat = false;
            produccionRestante[i] = centrales.get(i).getProduccion();
            for (int j = 0; j < clientes.size() && max_superat == false; ++j) {

                if (clientes.get(j).getConsumo() < produccionRestante[i]) {
                    Cliente cl = clientes.get(j);
                    double[][] d = distancias.getDistancias();
                    double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[i][j]);
                    if (cl.getContrato() == 0 && consumo < produccionRestante[i]) {
                        assignaciones.get(i).add(j);
                        produccionRestante[i] -= consumo;
                    } else {
                        max_superat = true;
                        ++indice_central;
                    }
                }
            }
        }
        //En este punto es solucion
        for (int i = indice_central; i < centrales.size(); ++i) {

            boolean max_superat = false;
            for (int j = 0; j < clientes.size() && max_superat == false; ++j) {

                Cliente cl = clientes.get(j);
                double[][] d = distancias.getDistancias();
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[i][j]);
                if (cl.getContrato() == 1 && consumo < produccionRestante[i]) {
                    assignaciones.get(i).add(j);
                    produccionRestante[i] -= consumo;
                }
                else {
                    max_superat = true;
                    ++indice_central;
                }
            }
        }
    }
    
    //compruebo que el consumo no se pase del tope
    public void add(int central,int cliente) {
        double restante = produccionRestante[central]; 
        int indice_cliente = assignaciones.get(assignaciones.size()-1).get(cliente); 
        Cliente cl = clientes.get(indice_cliente);
        double[][] d = distancias.getDistancias();
        double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[central][cliente]);
        if (restante > consumo) { //comprobacion de que se pueda hacer el add
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central).add(indice_cliente); 
            //lo borro de la central nula
            assignaciones.get(assignaciones.size()-1).remove(cliente); 
        }
    }
    
    //en el remove creo que no hay ninguna condicion concreta, confirmadmelo
    //haze falta comprobar que no vayas a quitar un prioritario, porque dejaria de ser solucion
    public void remove(int central,int cliente) {
        assignaciones.get(central).remove(cliente); 
        assignaciones.get(assignaciones.size()-1).add(cliente); 
    }
    
    //es el cambio de dos clientes en activo
    //No has controlado la central vacia, esta no tiene produccion restante i no se puede assignar un
    //prioritario a ella
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
    //No has controlado la central vacia, esta no tiene produccion restante i no se puede assignar un
    //prioritario a ella
    public void swap(int central,int clienteAssignado,int clienteNoAssignado) {
        double restanteCentral1 = produccionRestante[central]; 
        Cliente cl = clientes.get(clienteNoAssignado);
        double[][] d = distancias.getDistancias();
        double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[central][clienteNoAssignado]);
        if (restanteCentral1 > consumo) {
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

    //Funciones Auxiliar

    public static double meanArray(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum/array.length;
    }

    public static int index_minimumArray(double[] array) {
        double res = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < array.length;++i) {
            if(array[i] < res) index = i;
        }
        return index;
    }

    public static double[] getColumn(double[][] array, int index, int size_column){
        double[] column = new double[size_column];
        for(int i=0; i < size_column; i++){
            column[i] = array[i][index];
        }
        return column;
    }
}
