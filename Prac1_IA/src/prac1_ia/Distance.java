/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import IA.Energia.Centrales;
import IA.Energia.Clientes;

public class Distance {
    public static Distance instance; 
    public static double[][] distancias; 
    
    public Distance(int clientes_size,int centrales_size) {
        distancias = new double[clientes_size][clientes_size]; 
    }
    
    public static Distance getInstance(int clientes_size,int centrales_size) {
        if (instance == null) {
            instance = new Distance(clientes_size,centrales_size);
        }
        return instance;
    }
    
    public void CalculaDistancies(Centrales centrales, 
     Clientes clientes) {
        for (int i= 0; i < centrales.size();++i) {
         for (int j = 0; j < clientes.size();++j) {
             distancias[i][j] = distancia(clientes.get(i).getCoordX(),clientes.get(i).getCoordY(),
                     centrales.get(j).getCoordX(),centrales.get(j).getCoordY());
            }
         }
    }
    
    public double distancia(int x1,int y1, int x2,int y2) {
        double primera_resta = x2-x1; 
        double segunda_resta = y2-y1;
        
        double primer_elemento = Math.pow(primera_resta,2.0); 
        double segundo_elemento = Math.pow(segunda_resta,2.0); 
        
        double result = primer_elemento + segundo_elemento; 
        
        return Math.sqrt(result); 
    }
}
