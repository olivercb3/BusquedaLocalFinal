/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import IA.Energia.Centrales;
import IA.Energia.Clientes;

public class Distance {
    public static String value; 
    public static Distance instance; 
    public static int[][] distancias; 
    
    public Distance(String value) {
        Distance.value = value; 
        distancias = new int[100][100]; 
    }
    
    public static Distance getInstance(String value) {
        if (instance == null) {
            instance = new Distance(value);
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
    
    public int distancia(int x1,int y1, int x2,int y2) {
        int primera_resta = x2-x1; 
        int segunda_resta = y2-y1;
        
        int primer_elemento = (int) Math.pow(primera_resta,2); 
        int segundo_elemento = (int) Math.pow(segunda_resta,2); 
        
        int result = primer_elemento + segundo_elemento; 
        
        return (int) Math.sqrt(result); 
    }
}
