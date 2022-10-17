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
    public static double[][] distancias;
    private static Centrales centrales; 
    private static Clientes clientes; 
    
    public Distance(Centrales centrales, Clientes clientes) {
        Distance.centrales = centrales; 
        Distance.clientes = clientes; 
        distancias = new double[centrales.size()][clientes.size()]; 
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
    
    private void CalculaDistancias() {
        for (int i= 0; i < centrales.size()-1;++i) {
         for (int j = 0; j < clientes.size()-1;++j) {
             distancias[i][j] = distancia(clientes.get(j).getCoordX(),clientes.get(j).getCoordY(),
                     centrales.get(i).getCoordX(),centrales.get(i).getCoordY());
             //distancias[i][j] = distancia(34,45,67,85);
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
 
}
