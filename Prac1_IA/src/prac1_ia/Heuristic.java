/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import aima.search.framework.HeuristicFunction;
import IA.Energia.VEnergia;
import IA.Energia.Cliente;

import java.util.ArrayList;
import java.util.List;

import static prac1_ia.Practica.distancias;


public class Heuristic implements HeuristicFunction {
    
    /**
     * Instancia de la clase Board
     */
    private static Board state;
    
    /**
     * Función GoalState
     * @param state El objeto para saber si es Goal State o no
     * @return boolean
     */
    @Override
    public double getHeuristicValue(Object state) {
        Heuristic.state = (Board) state;
        try {
            return -heuristicValue();
        }
        catch (Exception e) {
            System.out.print(e);
            return 0;
        }
    }
    
    /**
     * Función que devuelve el valor heuristico del estado.
     * @return double
     * @throws java.lang.Exception
     */
    public double heuristicValue() throws Exception {
        int sum = 0;
        int tipo_central;

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
        System.out.print(sum);
        System.out.println();
        return sum;
    }
    
     private static List<String[]> createCsvDataSimple() {
        String[] header = {"id", "name", "address", "phone"};
        String[] record1 = {"1", "first name", "address 1", "11111"};
        String[] record2 = {"2", "second name", "address 2", "22222"};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);

        return list;
    }

    // heuristica que minimiza la produccion restante priorizando centrales mas grandes (objetivo minimizar)
    // tipo 0 = tipo A, tipo 1 = tipo B, tipo 2 = tipo C
    // tipo A > B > C.
    // coste_marcha/coste_parada (cuanto cuesta de mas tenerlo en marcha respecto parado, sin nadie):
    // A = 1.33333, B = 2, C = 3,3333333
    // (ben_medio*max_prod - max_prod * coste_prod)/coste_parada (margen de beneficio maximo, ben_medio = 500):
    // A = 21.166666, B = 19, C = 20

    /**
     * Función heurística que minimiza la producción restante priorizando centrales más grandes y el numero de clientes sin
     * asignar priotizando a los clientes mas grandes (objetivo minimizar).Para el peso que se le asigna a cada tipo de central se utiliza la division coste_producción/coste_producción_minimo
        y se multiplica la porcion de la producion libre de cada central.Para el de los clientes se usa la division beneficio_Mw/beneficio_min, que beneficia a los clientes pequeños que tienen un mejor ratio.
     * Para la suma de ambos se multiplica el de los clientes por 1.8, para igualar ponderaciones ( 1,8 = 3/(5/3) )
     * @return double
     * @throws java.lang.Exception
     */
    public double p_res() throws Exception {

        double sum = 0;
        double [] p_res = state.getProduccionRestante();
        for (int i = 0; i < p_res.length; ++i){
            int tipo = state.getCentrales().get(i).getTipo();
            double precio = VEnergia.getCosteProduccionMW(tipo);
            double produccion = state.getCentrales().get(i).getProduccion();
            sum += precio/50.0 * p_res[i]/produccion;
            }

        double sum2 = 0;
        ArrayList<Integer> cental_vacia = state.getAssignaciones().get(state.getCentrales().size());
        for (int i = 0; i< cental_vacia.size(); ++i){

            int client = cental_vacia.get(i);
            Cliente cl = state.getClientes().get(client);
            double precio = VEnergia.getCosteProduccionMW(cl.getTipo());
            sum2 += precio/300.0;
        }

        System.out.print(sum);
        System.out.println();
        return sum + 1.8 * sum2;
    }
    
    /**
     * Función heurística que minimiza la suma de distancias de todas las asignaciones (objetivo minimizar).Para penalizar los de la central vacia se les asigna la distancia maxima posible.
     * @return double
     * @throws java.lang.Exception
     */
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
        return sum;
    }
}
