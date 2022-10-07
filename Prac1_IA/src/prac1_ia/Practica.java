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
     
    
    public static void main(String[] args) throws Exception {
        int[] ignasi = {10,20,15}; 
        centrales = new Centrales(ignasi,1);
        System.out.println(centrales.toString()); 
    }
    
}
