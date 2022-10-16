/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import aima.search.framework.HeuristicFunction;
import IA.Energia.VEnergia;
import IA.Energia.Cliente;


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
            return -heuristicValue();
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public double heuristicValue() throws Exception {
        int sum = 0;
        int tipo_central;
        for (int i = 0; i < state.getAssignaciones().size(); ++i) {
            int numero_clients = state.getAssignaciones().get(i).size();
            tipo_central = state.getCentrales().get(i).getTipo();
            if (numero_clients == 0) {
                sum -= VEnergia.getCosteParada(tipo_central);
            }
            else {
                int sum_prov = 0;
                for (int j = 0; j < numero_clients; ++j) {
                    int index_client = state.getAssignaciones().get(i).get(j);
                    Cliente cl = state.getClientes().get(index_client);
                    if (cl.getContrato() == 0) { //Garantizada
                        sum += cl.getConsumo()*VEnergia.getTarifaClienteGarantizada(cl.getTipo());
                    }
                    else { //No garantizada
                        sum += cl.getConsumo()*VEnergia.getTarifaClienteNoGarantizada(cl.getTipo());
                    }
                   sum_prov += cl.getConsumo();
                }
                sum -= VEnergia.getCosteMarcha(tipo_central) + VEnergia.getCosteProduccionMW(tipo_central)*sum_prov;
            }
        } 
        return sum;
    }
    
}
