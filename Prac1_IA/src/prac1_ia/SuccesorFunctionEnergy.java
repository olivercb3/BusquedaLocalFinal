package prac1_ia;

import IA.Energia.Cliente;
import IA.Energia.VEnergia;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;

import java.util.List;

import static prac1_ia.Practica.distancias;

public class SuccesorFunctionEnergy implements SuccessorFunction {
    
    private static List<Successor> sucesoresCreados;
    private static Board tablero;
    private static ArrayList<Boolean> operators;
    ArrayList<ArrayList<Integer>> assignaciones; 

    
    @Override
    public List getSuccessors(Object state) {
        sucesoresCreados = new ArrayList<>(); 
        tablero = (Board) state;
        assignaciones = tablero.getAssignaciones();

        int i=0;
        if(operators.get(i++)) operatorSwap();
        if(operators.get(i++)) OperatorSwitch();
        if(operators.get(i++)) OpeartorVaciarCentral();

        return sucesoresCreados;
    }
    
    public void setOperators(Boolean opSwap, Boolean opSwitch, Boolean opVaciarCentral){
        operators = new ArrayList<>(3);
        operators.add(opSwap);
        operators.add(opSwitch);
        operators.add(opVaciarCentral);

    }


    private void operatorSwap() { 
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        for (int i = 0; i < b.size()-1;++i) {
            for (int j = 0; j < b.get(i).size();++j) {
                for (int k = i+1; k < b.size();++k) {
                    for (int s = 0; s < b.get(k).size(); ++s) {
                        
                        int client1 = b.get(i).get(j);
                        Cliente cl1 = tablero.getClientes().get(client1);
                        
                        int client2 = b.get(k).get(s);
                        Cliente cl2 = tablero.getClientes().get(client2);
                        
                        if (k == b.size()-1) {
                            double dist = distancias.get_dist(i, client1);
                            double consumo = cl1.getConsumo() + cl1.getConsumo() * VEnergia.getPerdida(dist);
                            double n_dist = distancias.get_dist(i, client2);
                            double n_consumo = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(n_dist);
                          
                            
                            if (n_consumo <= ((tablero.getProduccionRestante())[i]+consumo)) {
                                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                                copiaTablero.getAssignaciones().get(i).remove(j);
                                copiaTablero.getAssignaciones().get(k).remove(s);
                                copiaTablero.getAssignaciones().get(i).add(client2);
                                copiaTablero.getAssignaciones().get(k).add(client1);
                                
                                copiaTablero.getProduccionRestante()[i] += consumo;
                                copiaTablero.getProduccionRestante()[i] -= n_consumo;
                                
         
                                
                                sucesoresCreados.add(new Successor(
                                        "Cliente " + b.get(i).get(j) + " ha sido intercambiado con " + b.get(k).get(s),
                                        copiaTablero));
                            }
                        }
                        
                        else if(tablero.getClientes().get(client1).getContrato() == 0) { //No es prioritario, el cliente de la central i
                            double dist1 = distancias.get_dist(i, client1);
                            double consumo1 = cl1.getConsumo() + cl1.getConsumo() * VEnergia.getPerdida(dist1);
                            double n_dist1 = distancias.get_dist(k, client1);
                            double n_consumo1 = cl1.getConsumo() + cl1.getConsumo() * VEnergia.getPerdida(n_dist1);
                            
                            double dist2 = distancias.get_dist(k, client2);
                            double consumo2 = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(dist2);
                            double n_dist2 = distancias.get_dist(i, client2);
                            double n_consumo2 = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(n_dist2);
                            
                            if (n_consumo1 <= ((tablero.getProduccionRestante())[k]+consumo2) && n_consumo2 <= ((tablero.getProduccionRestante())[i])+consumo1) {
                                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                                copiaTablero.getAssignaciones().get(i).remove(j);
                                copiaTablero.getAssignaciones().get(k).remove(s);
                                copiaTablero.getAssignaciones().get(i).add(client2);
                                copiaTablero.getAssignaciones().get(k).add(client1);
                                
                                copiaTablero.getProduccionRestante()[i] += consumo1;
                                copiaTablero.getProduccionRestante()[k] -= n_consumo1;
                                
                                copiaTablero.getProduccionRestante()[k] += consumo2;
                                copiaTablero.getProduccionRestante()[i] -= n_consumo2;
                                
                                sucesoresCreados.add(new Successor(
                                        "Cliente " + b.get(i).get(j) + " ha sido intercambiado con " + b.get(k).get(s),
                                        copiaTablero));
                            }
                        }
                    }
                }
            }
        }
    }

    private void OperatorSwitch() { //funciona
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        for (int i = 0; i < b.size(); ++i) {
            for (int c = 0; c < b.get(i).size();++c) {
                for (int j = 0; j < b.size(); ++j) {
                    if (j == i);
                    else {

                        int client = b.get(i).get(c);
                        Cliente cl = tablero.getClientes().get(client);

                        if(i != tablero.getCentrales().size() && j != tablero.getCentrales().size()) {

                            double dist = distancias.get_dist(i, client);
                            double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                            double n_dist = distancias.get_dist(j, client);
                            double n_consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(n_dist);
                            if (n_consumo < (tablero.getProduccionRestante())[j]) {
                                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                                copiaTablero.getAssignaciones().get(i).remove(c);
                                copiaTablero.getAssignaciones().get(j).add(client);
                                copiaTablero.getProduccionRestante()[i] += consumo;
                                copiaTablero.getProduccionRestante()[j] -= n_consumo;
                                sucesoresCreados.add(new Successor(
                                        "Cliente " + c + " ha sido movido de la central " + i + " a la central " + j,
                                        copiaTablero));
                            }
                        }

                        else if(j != tablero.getCentrales().size())  {
                            double dist = distancias.get_dist(j, client);
                            double n_consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                            if (n_consumo < (tablero.getProduccionRestante())[j]) {
                                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                                copiaTablero.getAssignaciones().get(i).remove(c);
                                copiaTablero.getAssignaciones().get(j).add(client);
                                copiaTablero.getProduccionRestante()[j] -= n_consumo;
                                sucesoresCreados.add(new Successor(
                                        "Cliente " + c + " ha sido movido de la central " + i + " a la central " + j,
                                        copiaTablero));
                            }
                        }
                        else {

                            if (cl.getContrato() == 0) {
                                double dist = distancias.get_dist(i, client);
                                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                                copiaTablero.getAssignaciones().get(i).remove(c);
                                copiaTablero.getAssignaciones().get(j).add(client);
                                copiaTablero.getProduccionRestante()[i] += consumo;
                                sucesoresCreados.add(new Successor(
                                        "Cliente " + c + " ha sido movido de la central " + i + " a la central " + j,
                                        copiaTablero));
                            }
                        }
                    }
                }
             }
         }
    }
    
    private void OpeartorVaciarCentral() {
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        for (int i = 0; i < b.size()-1; ++i) {
            
                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                for (int j = b.get(i).size()-1; j >= 0; --j) {
                    
                    int client = b.get(i).get(j);
                    double dist = distancias.get_dist(i, client);
                    Cliente cl = tablero.getClientes().get(client);
                    if(cl.getContrato() == 0){

                        double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                        copiaTablero.getAssignaciones().get(i).remove(j);
                        copiaTablero.getAssignaciones().get(b.size()-1).add(client);
                        copiaTablero.getProduccionRestante()[i] += consumo;
                    }
                } 
                sucesoresCreados.add(new Successor(
                                            "Central " + i + " ha sido vaciada",
                                            copiaTablero));
           
        }
    }

    /*
    private void operatorAdd() { //funciona
        for (int i = 0; i < assignaciones.size()-1;++i) {
            for (int j = 0;j < assignaciones.get(assignaciones.size()-1).size();++j) { //recorre los no asignados
                int no_assignado = assignaciones.get(assignaciones.size()-1).get(j);
                Board copia = tablero;
                double prod_res = copia.getProduccionRestante()[i];
                double consumo_cliente = copia.getClientes().get(no_assignado).getConsumo();
                if (prod_res > consumo_cliente) {
                    copia.getProduccionRestante()[i] -= consumo_cliente;
                    copia.afegeix(i, no_assignado);
                    copia.esborra(assignaciones.size()-1, j);
                }
                sucesoresCreados.add(new Successor(
                                "HOLA",
                                copia));
            }
        }
    }

    private void operatorRemove() { //funciona
        for (int i = 0; i < assignaciones.size();++i) {
            for (int j = 0; j < assignaciones.get(i).size();++j) {
                Board copiaTablero = tablero;
                int indice_cliente = assignaciones.get(i).get(j);
                if (copiaTablero.getClientes().get(indice_cliente).getContrato() == 0) {
                    copiaTablero.remove(i,j);
                }
                sucesoresCreados.add(new Successor(
                                "Cliente borrado " + j + "en la central" + i,
                                copiaTablero));
            }
        }
    }

    private void operatorInterchange() { //funciona
        for (int i = 0; i < assignaciones.size()-2; ++i) {
            for (int c = 0; c < assignaciones.get(i).size();++c) {
                for (int j = i+1; j < assignaciones.size()-1; ++j) {
                     for (int k = 0; k < assignaciones.get(j).size();++k) {
                        Board copiaTablero = tablero;
                        copiaTablero.interchange(i,j,c,k);
                        sucesoresCreados.add(new Successor(
                                    "Intercambio entre los clientes" + c + "y " + k +
                                            "de las centrales" + i + "y " + j + "respectivamente",
                                    copiaTablero));
                    }
                }
             }
         }
      }

    */
}


//3 operadores: switch, swap, rellenar central
//proposta que els clients estiguessin ordenats dins de la matriu, no se encara exactament perque pero podria ser util a nivell de temps

