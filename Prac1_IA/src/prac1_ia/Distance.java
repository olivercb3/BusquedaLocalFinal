/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import IA.Energia.Centrales;
import IA.Energia.Clientes;

//singleton class
public class Distance {
    /**
     * Instancia de la clase Distance
     */
    public static Distance instance;
    /**
     * Matriz con todas las distancias del tablero, en las filas las centrales, y en las columnas los clientes
     */
    private static double[][] distancias;
    /**
     * Vector de las centrales del tablero
     */
    private static Centrales centrales; 
    /**
     * Vector de los clientes del tablero
     */
    private static Clientes clientes;
    /**
     * Distancia maxima en el tablero de 100x100
     */
    private static double max_dist;
    
    
    /**
     * Constructor de la clase Distance.
     * @param centrales Centrales en el tablero
     * @param clientes Clientes en el tablero
     * Genera una matriz con todas las distancias entre clientes y centrales, al ser la clase singleton solo se calculara una vez esta matriz. 
     */
    public Distance(Centrales centrales, Clientes clientes) {
        Distance.centrales = centrales;
        Distance.clientes = clientes;
        distancias = new double[centrales.size()][clientes.size()];
        max_dist = Math.sqrt(2 * Math.pow(100., 2));
        CalculaDistancias();
    }
    
     /**
     * Getter de las distancias.
     * Devuelve las distancias del tablero de centrales y clientes 
     */
    public double[][] getDistancias() {
        return distancias;
    }
    
     /**
     * Obtención de la instancia de la clase Distance.
     * Devuelve las distancias del tablero de centrales y clientes 
     */
    public static Distance getInstance(Centrales centrales,Clientes clientes) {
        if (instance == null) {
            instance = new Distance(centrales, clientes);
        }
        return instance;
    }
    
    /**
     * Calculador de distancias.
     * Calcula la distancia entre todos los clientes y todas las centrales
     */
    public void CalculaDistancias() {
        for (int i= 0; i < centrales.size();++i) {
         for (int j = 0; j < clientes.size();++j) {
             distancias[i][j] = distancia(clientes.get(j).getCoordX(),clientes.get(j).getCoordY(),
                     centrales.get(i).getCoordX(),centrales.get(i).getCoordY());
            }
         }
    }
    
     /**
     * Función privada para el cálculo de distancias.
     * @param x1 Coordenada x del primer elemento.
     * @param y1 Coordenada y del primer elemento.
     * @param x2 Coordenada x del segundo elemento.
     * @param y2 Coordenada y del segundo elemento.
     * Calcula la distancia de dos elementos (cliente y central) dadas las coordenadas x e y de cada uno.
     */
    private double distancia(int x1,int y1, int x2,int y2) {
        double primera_resta = x2-x1; 
        double segunda_resta = y2-y1;
        
        double primer_elemento = Math.pow(primera_resta,2.0); 
        double segundo_elemento = Math.pow(segunda_resta,2.0); 
        
        double result = primer_elemento + segundo_elemento;

        return Math.sqrt(result); 
    }
    
    /**
     * Obtención de distancia entre una central y un cliente específicos.
     * @param central Índice de la central
     * @param cliente Índice del cliente
     * @return double
     */
    public double get_dist(int central, int cliente) {
        return distancias[central][cliente];
    }

    /**
     * Getter de la distancia máxima.
     */
    public double getMax_dist() {
        return max_dist;
    }


    /**
     * Impresión de la matriz de distancias.
     */
    public void Imprimir_matriz() {

        for(int i = 0; i < distancias.length; ++i) {
            System.out.print(i + ": ");
            for (int j = 0; j < distancias[0].length; ++j)
                System.out.print(distancias[i][j] + " ");
            System.out.println();
        }
    }
 
}
