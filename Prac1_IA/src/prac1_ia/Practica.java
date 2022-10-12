/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prac1_ia;
import IA.Energia.*;

/**
 *
 * @author olivercemelibarron
 */
public class Practica {

    
     private static Centrales centrales; 
     private static Clientes clientes; 
     private static VEnergia venergia;
     private static int[][] table;

    
     
     
     public static void InitialState() {
        int indice_cliente = 0;
        for (int i = 0; i < centrales.size(); ++i) {
            boolean max_superat = false;
            double max_produccion = centrales.get(i).getProduccion();
            for (int j = indice_cliente; j < clientes.size() && max_superat == false; ++j) {
                if (clientes.get(j).getConsumo() < max_produccion) {
                    table[j][i] = 1;
                    max_produccion -= clientes.get(j).getConsumo();
                    ++indice_cliente;
                }
                else {
                    max_superat = true;
                }
            }
        }
     }
     
     
    public static void main(String[] args) throws Exception {
        
        int[] ia = {10,20,15}; 
        double[] cl = {0.2, 0.3, 0.5}; 
        centrales = new Centrales(ia,1);
        clientes = new Clientes(30000, cl, 0.5, 291200) ;
        table = new int[clientes.size()][centrales.size()];
        InitialState();
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[0].length; ++j) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(centrales.get(centrales.size()-1).getProduccion());
        
        int consumo_total = 0;
        for (int i = clientes.size()-2; i < clientes.size(); ++i) {
            consumo_total += clientes.get(i).getConsumo();
        }
        System.out.println(consumo_total);
        
        
    }
    
}
