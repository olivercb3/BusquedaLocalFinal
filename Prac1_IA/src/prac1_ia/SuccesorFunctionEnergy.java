package prac1_ia;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;

import java.util.List;

public class SuccesorFunctionEnergy implements SuccessorFunction {
    
    private static List<Successor> sucesoresCreados;
    private static Board tablero; 
    private static ArrayList<ArrayList<Integer>> assignaciones; 

    @Override
    public List getSuccessors(Object o) {
        tablero = (Board) o;
        assignaciones = tablero.getAssignaciones(); 
        operatorAdd();
        operatorSwap(); 
        operatorRemove();
        operatorInterchange(); 
        return sucesoresCreados; 
    }

    private void operatorAdd() {
        int tamanoNoAssignados = assignaciones.get(assignaciones.size()-1).size(); 
        for (int i = 0; i < assignaciones.size();++i) {
            for (int j = 0; j < tamanoNoAssignados;++j) {
                Board copiaTablero = tablero; 
                copiaTablero.add(i,j);
                sucesoresCreados.add(new Successor(
                                "Cliente añadido " + j + "en la central" + i,
                                copiaTablero));
            }
        }
    }
    
    private void operatorSwap() {
        for (int i = 0; i < assignaciones.size();++i) {
            for (int j = 0; j < assignaciones.get(i).size();++j) {
                
            }
        }
    }
    
    private void operatorRemove() {
        
    }
    
    private void operatorInterchange() {
        
    }
}
