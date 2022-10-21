/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import IA.Energia.Centrales;
import IA.Energia.Clientes;

//singleton class
public class Distance {
    public static Distance instance;
    private static double[][] distancias;
    private static Centrales centrales; 
    private static Clientes clientes;
    private static double max_dist;
    
    public Distance(Centrales centrales, Clientes clientes) {
        Distance.centrales = centrales;
        Distance.clientes = clientes;
        distancias = new double[centrales.size()][clientes.size()];
        max_dist = Math.sqrt(2 * Math.pow(100., 2));
        CalculaDistancias();
    }
    
    public double[][] getDistancias() {
        return distancias;
    }
    
    public static Distance getInstance(Centrales centrales,Clientes clientes) {
        if (instance == null) {
            instance = new Distance(centrales, clientes);
        }
        return instance;
    }
    
    public void CalculaDistancias() {
        for (int i= 0; i < centrales.size();++i) {
         for (int j = 0; j < clientes.size();++j) {
             distancias[i][j] = distancia(clientes.get(j).getCoordX(),clientes.get(j).getCoordY(),
                     centrales.get(i).getCoordX(),centrales.get(i).getCoordY());
            }
         }
    }
    
    private double distancia(int x1,int y1, int x2,int y2) {
        double primera_resta = x2-x1; 
        double segunda_resta = y2-y1;
        
        double primer_elemento = Math.pow(primera_resta,2.0); 
        double segundo_elemento = Math.pow(segunda_resta,2.0); 
        
        double result = primer_elemento + segundo_elemento;

        return Math.sqrt(result); 
    }
    
    public double get_dist(int central, int cliente) {
        return distancias[central][cliente];
    }

    public double getMax_dist() {
        return max_dist;
    }

    //Funcion Auxiliar

    public void Imprimir_matriz() {

        for(int i = 0; i < distancias.length; ++i) {
            System.out.print(i + ": ");
            for (int j = 0; j < distancias[0].length; ++j)
                System.out.print(distancias[i][j] + " ");
            System.out.println();
        }
    }
 
}
