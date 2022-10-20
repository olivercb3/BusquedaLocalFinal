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
        //int i=0;
        /*
        if(operators.get(i++)) operatorAdd();
        if(operators.get(i++)) operatorSwap();
        if(operators.get(i++)) operatorInterchange();
        if(operators.get(i)) operatorRemove();
         */
        //operatorRemove();
        //operatorAdd();
        //operatorInterchange();
        //operatorSwap();
        OperatorSwitch();
        OpeartorVaciarCentral();
        return sucesoresCreados; 
        
        
    }
    
    public void setOperators(Boolean opAdd, Boolean opSwap, Boolean opRemove, Boolean opInterchange){
        operators = new ArrayList<>(4);
        operators.add(opAdd);
        operators.add(opSwap);
        operators.add(opRemove);
        operators.add(opInterchange);

    }

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
    
    private void operatorSwap() { //funciona
        for (int i = 0; i < assignaciones.size()-1;++i) {
            for (int j = 0; j < assignaciones.get(i).size();++j) {
                for (int k = 0; k < assignaciones.get(assignaciones.size()-1).size();++k) {
                    Board copiaTablero = tablero; 
                    copiaTablero.swap(i,j,k); 
                    sucesoresCreados.add(new Successor(
                                "Clientes no assignado " + j + "intercambiado por el cliente assignado " + 
                                        k + "de la central" + i,
                                copiaTablero));
                }
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
        for (int i = 0; i < assignaciones.size()-1; ++i) {
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
                    double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                    copiaTablero.getAssignaciones().get(i).remove(j);
                    copiaTablero.getAssignaciones().get(b.size()-1).add(client);
                    copiaTablero.getProduccionRestante()[i] += consumo;
                    
                } 
                sucesoresCreados.add(new Successor(
                                            "Central " + i + " ha sido vaciada",
                                            copiaTablero));
           
        }
    }
    //tener en cuenta aun las distancias a la hora de calcular el consumo.
}


//3 operadores: switch, swap, rellenar central
//proposta que els clients estiguessin ordenats dins de la matriu, no se encara exactament perque pero podria ser util a nivell de temps

