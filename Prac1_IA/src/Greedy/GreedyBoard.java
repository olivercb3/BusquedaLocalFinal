package Greedy;

import static prac1_ia.Practica.centrales;
import static prac1_ia.Practica.clientes;
import static prac1_ia.Practica.venergia;
import static prac1_ia.Practica.produccion_restante;

public class GreedyBoard {

    //Cada posicion i representa al cliente i, y su contenido su central assignada
    private int[] Centrales_Assig;

    public GreedyBoard(int n_clientes){

        Centrales_Assig = new int[n_clientes];
    }

    public GreedyBoard(int [] c){

        for(int i=0;i<c.length;i++) Centrales_Assig[i] = c[i];
    }

    public boolean puede_cambiar_central (int cliente, int central) {

        return (produccion_restante[central] - clientes.get(cliente).getConsumo() > 0);
    }

    public void cambiar_central(int cliente, int central) {

        Centrales_Assig[cliente] = central;
    }

    public boolean isGoal(){
        boolean correcto = true;

        for(int i=0;i<Centrales_Assig.length;i++) {
            if(clientes.get(i).getTipo() == 0 && Centrales_Assig[i] == centrales.size())
                correcto = false;
        }

        return correcto;
    }
}
