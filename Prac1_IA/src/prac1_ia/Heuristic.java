/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import aima.search.framework.HeuristicFunction;
import IA.Energia.VEnergia;
import IA.Energia.Cliente;

import java.util.ArrayList;

import static prac1_ia.Practica.distancias;


/**
 *
 * @author olivercemelibarron
 */
public class Heuristic implements HeuristicFunction {
    
    private static Board state;
    
    @Override
    public double getHeuristicValue(Object state) {
        Heuristic.state = (Board) state;
        try {
            return p_res();
        }
        catch (Exception e) {
            System.out.print(e);
            return 0;
        }
    }
    
    public double heuristicValue() throws Exception {
        int sum = 0;
        int tipo_central;
        //System.out.print(state.getAssignaciones().size());
        //System.out.println();
        for (int i = 0; i < state.getAssignaciones().size()-1; ++i) {
            
            int numero_clients = state.getAssignaciones().get(i).size();
            //System.out.print(numero_clients + " ");
            tipo_central = state.getCentrales().get(i).getTipo();
            if (numero_clients == 0) {
                sum -= VEnergia.getCosteParada(tipo_central);
            }
            else {
                for (int j = 0; j < numero_clients; ++j) {
                    int index_client = state.getAssignaciones().get(i).get(j);
                    Cliente cl = state.getClientes().get(index_client);
                    if (cl.getContrato() == 1) { //Garantizada
                        sum += cl.getConsumo()*VEnergia.getTarifaClienteGarantizada(cl.getTipo());
                    }
                    else { //No garantizada
                        sum += cl.getConsumo()*VEnergia.getTarifaClienteNoGarantizada(cl.getTipo());
                    }
                }
                sum -= VEnergia.getCosteMarcha(tipo_central) + VEnergia.getCosteProduccionMW(tipo_central)*state.getCentrales().get(i).getProduccion();
            }
        } 
        //System.out.print(sum);
        //System.out.println();
        return sum;
    }

    // heuristica que minimiza la produccion restante priorizando centrales mas grandes (objetivo minimizar)
    // tipo 0 = tipo A, tipo 1 = tipo B, tipo 2 = tipo C
    // tipo A > B > C.
    // coste_marcha/coste_parada (cuanto cuesta de mas tenerlo en marcha respecto parado, sin nadie):
    // A = 1.33333, B = 2, C = 3,3333333
    // (ben_medio*max_prod - max_prod * coste_prod)/coste_parada (margen de beneficio maximo, ben_medio = 500):
    // A = 21.166666, B = 19, C = 20
    public double p_res() throws Exception {

        double sum = 0;
        double [] p_res = state.getProduccionRestante();
        for (int i = 0; i < p_res.length; ++i){
            int tipo = state.getCentrales().get(i).getTipo();
            double precio = VEnergia.getCosteProduccionMW(tipo);
            sum += precio/50.0 * p_res[i];
            }

        System.out.print(sum);
        System.out.println();
        return sum;
    }

    //heuristica suma de distancias (objetivo minimizar)
    public double sum_ditancias() throws Exception {

        double sum = 0;
        ArrayList<ArrayList<Integer>> assignaciones = state.getAssignaciones();
        for (int i = 0; i< assignaciones.size(); ++i)
            for (int j = 0; j < assignaciones.get(i).size(); ++j){

                if (i != assignaciones.size()-1) {
                    int client = assignaciones.get(i).get(j);
                    sum += distancias.get_dist(i, client);
                }
                else sum += distancias.getMax_dist();
            }

        //System.out.print(sum);
        //System.out.println();
        return sum;
    }
}
