package Greedy;

import IA.Energia.VEnergia;

import java.util.ArrayList;

import static prac1_ia.Practica.centrales;
import static prac1_ia.Practica.clientes;

public class GreedyBoard {

    //Cada posicion i representa al cliente i, y su contenido su central assignada
    private static ArrayList<ArrayList<Integer>> assignacions;

    public GreedyBoard(int n_centrales, int n_clientes){

        assignacions = new ArrayList<ArrayList <Integer>>(n_centrales + 1);
        ArrayList<Integer> Central_Vacia = assignacions.get(n_centrales);
        for(int i = 0; i < n_clientes; ++i)
            Central_Vacia.add(i);
    }

    public GreedyBoard(ArrayList<ArrayList <Integer>> a){

        for(int i=0;i<a.size();i++){

            ArrayList<Integer> clientes = a.get(i);
            for(int j=0;j<clientes.size();j++)
                assignacions.get(i).add(clientes.get(j));
        }
    }

    public ArrayList<ArrayList<Integer>> getAssignacions() {
        return assignacions;
    }

    public boolean puede_añadir_cliente (int cliente, int central) {

        Double produccion = centrales.get(central).getProduccion();
        ArrayList<Integer> Central = assignacions.get(central);
        //provisional, se tiene que cambiar
        int distancia = 1;

        for (int i = 0; i < Central.size(); ++i)
            produccion  -= clientes.get(i).getConsumo() / VEnergia.getPerdida(distancia);

        Double consumo = clientes.get(cliente).getConsumo() / VEnergia.getPerdida(distancia);

        if(produccion - consumo > 0 ) return true;
        else return false;

    }

    public void añadir_cliente(int cliente, int central) {

        assignacions.get(central).add(cliente);
    }

    public boolean isGoal(){

        ArrayList<Integer> Central_Vacia = assignacions.get(assignacions.size());
        for (int i = 0; i < Central_Vacia.size();++i){

            int cliente = Central_Vacia.get(i);
            if (clientes.get(cliente).getContrato() == 0) return false;
        }

        return true;
    }


}
