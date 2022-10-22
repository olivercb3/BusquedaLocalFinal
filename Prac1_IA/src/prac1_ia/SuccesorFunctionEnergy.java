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
        if (operators.get(i++)) OpeartorRellenarCentral();

        return sucesoresCreados;
    }
    
    public void setOperators(Boolean opSwap, Boolean opSwitch, Boolean opVaciarCentral, Boolean opRellenarCentral){
        operators = new ArrayList<>(4);
        operators.add(opSwap);
        operators.add(opSwitch);
        operators.add(opVaciarCentral);
        operators.add(opRellenarCentral);

    }


    private void operatorSwap() {

        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        ArrayList<Integer> cental_vacia = b.get(tablero.getCentrales().size());
        double[] p_restante = tablero.getProduccionRestante();

        for (int i = 0; i < b.size()-1;++i) {
            for (int j = 0; j < b.get(i).size();++j) {

                int client1 = b.get(i).get(j);
                Cliente cl1 = tablero.getClientes().get(client1);

                double dist1 = distancias.get_dist(i, client1);
                double consumo1 = cl1.getConsumo() + cl1.getConsumo() * VEnergia.getPerdida(dist1);

                for (int s = 0; s < cental_vacia.size(); ++s) {
                        int client2 = cental_vacia.get(s);
                        Cliente cl2 = tablero.getClientes().get(client2);

                        double n_dist2 = distancias.get_dist(i, client2);
                        double n_consumo2 = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(n_dist2);

                        if (tablero.getClientes().get(client1).getContrato() == 0) {
                            if (n_consumo2 <= (p_restante[i]+consumo1)) {

                                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                                copiaTablero.getAssignaciones().get(i).remove(j);
                                copiaTablero.getAssignaciones().get(copiaTablero.getCentrales().size()).remove(s);
                                copiaTablero.getAssignaciones().get(i).add(client2);
                                copiaTablero.getAssignaciones().get(copiaTablero.getCentrales().size()).add(client1);
                                
                                copiaTablero.getProduccionRestante()[i] += consumo1;
                                copiaTablero.getProduccionRestante()[i] -= n_consumo2;

                                sucesoresCreados.add(new Successor(
                                        "Cliente " + client1 + " ha sido intercambiado con " + client2,
                                        copiaTablero));
                            }
                        }
                }
            }
        }
    }

    private void OperatorSwitch() {
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        double[] p_restante = tablero.getProduccionRestante();

        for (int i = 0; i < b.size(); ++i) {
            for (int c = 0; c < b.get(i).size();++c) {

                int client = b.get(i).get(c);
                Cliente cl = tablero.getClientes().get(client);

                for (int j = 0; j < b.size(); ++j) {
                    if (j == i);
                    else {

                        if(i != tablero.getCentrales().size() && j != tablero.getCentrales().size()) { //Ninguna de as centrales es la vacia

                            double dist = distancias.get_dist(i, client);
                            double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);

                            double n_dist = distancias.get_dist(j, client);
                            double n_consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(n_dist);

                            if (n_consumo < p_restante[j]) {
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

                        else if(j != tablero.getCentrales().size())  { //La central de origen es la vacia

                            double n_dist = distancias.get_dist(j, client);
                            double n_consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(n_dist);

                            if (n_consumo < p_restante[j]) {
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

                            double dist = distancias.get_dist(i, client);
                            double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);

                            if (cl.getContrato() == 0) { //La central destino es la vacia

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

    // Vacia una central y la pone en la vacia, no se escojera porque los lleva a la central vacia por lo que tendran que
    // pagar la penalizacion y no se consigue ningun beneficio. Se deberia enviar a qualquier central no solo a la vacia
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
    
    private void OpeartorRellenarCentral() {
        
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        int client;
        double dist, consumo;
        Cliente cl;
        
        for (int i = 0; i < b.size()-1; ++i) {
            ArrayList<Integer> provisional = new  ArrayList<Integer>();
            double prod_central = tablero.getCentrales().get(i).getProduccion(), prod_res = tablero.getProduccionRestante()[i];
            double sum = prod_res;
            if ((prod_res/prod_central) > 0.7){
                //System.out.print("------------------------" + i + " " + (b.size()-1) + " ");
                for (int j = 0; j < b.get(b.size()-1).size() && (sum/prod_central > 0.1); ++j) {
                     client = b.get(b.size()-1).get(j);
                     dist = distancias.get_dist(i, client);
                     cl = tablero.getClientes().get(client);
                     consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                     if ((sum - consumo) < 0);
                     //else if ((sum - consumo)/prod_central > 0.1) {
                     else {
                         sum -= consumo;
                         provisional.add(j);
                     }
                    // }
                }
                System.out.print("Aqui: " +  provisional.size() +  " " + sum + " ");
                
                if (sum/prod_central <= 0.1) {
                     System.out.print("entro");
                     Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                    for (int j = 0; j < provisional.size(); ++j) {
                        client = b.get(b.size()-1).get(provisional.get(j));
                        dist = distancias.get_dist(i, client);
                        cl = tablero.getClientes().get(client);
                        consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                        //Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                        copiaTablero.getAssignaciones().get(b.size()-1).remove(provisional.get(j));
                        copiaTablero.getAssignaciones().get(i).add(client);
                       // System.out.print("Consumoooo: " + consumo + " ");
                        copiaTablero.getProduccionRestante()[i] -= consumo;
                    }
                    sucesoresCreados.add(new Successor(
                                "Grupo de clientes ha sido añadido a la central " + i,
                                copiaTablero));
                }
            }  
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

