package Greedy;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class Greedy_SuccesorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {

        ArrayList retVal= new ArrayList();
        GreedyBoard board=(GreedyBoard) o;
        ArrayList<ArrayList<Integer>> assig = board.getAssignacions();
        ArrayList<Integer> Central_Vacia = assig.get(assig.size()-1);

        for(int i = 0; i < assig.size(); ++i){

            ArrayList<Integer> Central = assig.get(i);
            for(int j = 0; j < Central_Vacia.size(); ++j){

                if (board.puede_añadir_cliente(i, Central_Vacia.get(j))){

                    GreedyBoard newBoard = new GreedyBoard(assig);
                    newBoard.añadir_cliente(Central_Vacia.get(j), i);
                    retVal.add(new Successor(new String("añadir_cliente: central = " + i + ", cliente = " + Central_Vacia.get(j)),newBoard));
                }
            }
        }

        return retVal;
    }
}
