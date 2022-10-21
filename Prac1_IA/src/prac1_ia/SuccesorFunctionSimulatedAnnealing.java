/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import IA.Energia.Cliente;
import IA.Energia.VEnergia;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static prac1_ia.Practica.distancias;

/**
 *
 * @author olivercemelibarron
 */
public class SuccesorFunctionSimulatedAnnealing implements SuccessorFunction{
    
    
    private static List<Successor> sucesoresCreados;
    private static Board tablero;
    private static Random rand = new Random(202122);

    
    @Override
    public List getSuccessors(Object state) { 
        tablero = (Board) state;
        int i = rand.nextInt(3); 

        if(i == 0) operatorSwap();
        if(i == 1) OperatorSwitch();
        if(i == 2) OpeartorVaciarCentral();
        
        return sucesoresCreados;

    }

    private void operatorSwap() { 
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        double[] p_restante = tablero.getProduccionRestante();
        
        int i = rand.nextInt(b.size()-1); 
        int j = rand.nextInt(b.get(i).size()); 
        int k = rand.nextInt((b.size())-i+1)+(i+1); 
        int s = rand.nextInt(b.get(k).size());
        //return random.nextInt(max - min) + min;

        int client1 = b.get(i).get(j);
        Cliente cl1 = tablero.getClientes().get(client1);
        double dist1 = distancias.get_dist(i, client1);
        double consumo1 = cl1.getConsumo() + cl1.getConsumo() * VEnergia.getPerdida(dist1);              
        int client2 = b.get(k).get(s);
        Cliente cl2 = tablero.getClientes().get(client2);
        double n_dist2 = distancias.get_dist(i, client2);
        double n_consumo2 = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(n_dist2);
        if (k == b.size()-1) {
            if (n_consumo2 <= (p_restante[i]+consumo1)) {

                Board copiaTablero = new Board(tablero.getCentrales(), tablero.getClientes(), tablero.getAssignaciones(), tablero.getProduccionRestante());
                copiaTablero.getAssignaciones().get(i).remove(j);
                copiaTablero.getAssignaciones().get(k).remove(s);
                copiaTablero.getAssignaciones().get(i).add(client2);
                copiaTablero.getAssignaciones().get(k).add(client1);

                copiaTablero.getProduccionRestante()[i] += consumo1;
                copiaTablero.getProduccionRestante()[i] -= n_consumo2;

                sucesoresCreados.add(new Successor(
                        "Cliente " + b.get(i).get(j) + " ha sido intercambiado con " + b.get(k).get(s),
                        copiaTablero));
            }
        }
                        
        else if(tablero.getClientes().get(client1).getContrato() == 0) { //No es prioritario, el cliente de la central i

            double n_dist1 = distancias.get_dist(k, client1);
            double n_consumo1 = cl1.getConsumo() + cl1.getConsumo() * VEnergia.getPerdida(n_dist1);

            double dist2 = distancias.get_dist(k, client2);
            double consumo2 = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(dist2);

            if (n_consumo1 <= (p_restante[k]+consumo2) && n_consumo2 <= (p_restante[i])+consumo1) {
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

   private void OperatorSwitch() {
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        int i = rand.nextInt(b.size()); 
        int j = rand.nextInt(b.size()); 
        int c = rand.nextInt(b.get(i).size()); 
        double[] p_restante = tablero.getProduccionRestante();
        int client = b.get(i).get(c);
        Cliente cl = tablero.getClientes().get(client);
 
        if (j == i) OperatorSwitch();
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

    // Vacia una central y la pone en la vacia, no se escojera porque los lleva a la central vacia por lo que tendran que
    // pagar la penalizacion y no se consigue ningun beneficio. Se deberia enviar a qualquier central no solo a la vacia
    private void OpeartorVaciarCentral() {
        ArrayList<ArrayList<Integer>> b = tablero.getAssignaciones();
        int i = rand.nextInt(b.size()-1);
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
