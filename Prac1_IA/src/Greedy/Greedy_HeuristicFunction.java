package Greedy;

import IA.Energia.Cliente;
import IA.Energia.VEnergia;
import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;

import static prac1_ia.Practica.centrales;
import static prac1_ia.Practica.clientes;

public class Greedy_HeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object o) {

        GreedyBoard board = (GreedyBoard) o;
        ArrayList<ArrayList<Integer>> assignacions = board.getAssignacions();
        int central_vacia = assignacions.size()-1;

        Double coste = 0.0;
        for (int i = 0; i < assignacions.size(); ++i) {

            int n_clients_central = assignacions.get(i).size();
            int tipo_central = centrales.get(i).getTipo();

            if (n_clients_central == 0  && i != central_vacia) {
                try {
                    coste += VEnergia.getCosteParada(tipo_central);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            else {

                for (int j = 0; j < n_clients_central; ++j) {

                    int index_client = assignacions.get(i).get(j);
                    Cliente cl = clientes.get(index_client);

                    if (i != central_vacia) {

                        if (cl.getContrato() == 0) { //Garantizada
                            try {
                                coste -= cl.getConsumo() * VEnergia.getTarifaClienteGarantizada(cl.getTipo());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        else { //No garantizada
                            try {
                                coste -= cl.getConsumo() * VEnergia.getTarifaClienteNoGarantizada(cl.getTipo());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    else {

                        Double indemnicacion = 50 * cl.getConsumo();
                        coste += indemnicacion;
                    }
                }

                if (i != central_vacia) {

                    try {
                        Double produccion = centrales.get(i).getProduccion();
                        coste += VEnergia.getCosteMarcha(tipo_central) + VEnergia.getCosteProduccionMW(tipo_central) * produccion;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return coste;
    }
}
