package prac1_ia;

import IA.Energia.Clientes;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;

import java.util.List;

public class SuccesorFunctionEnergy implements SuccessorFunction {
    
    private static List<Successor> sucesoresCreados;
    private static Board tablero; 
    private static Clientes clientesNoAssignados; 
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
        
    }
    
    private void operatorSwap() {
        
    }
    
    private void operatorRemove() {
        
    }
    
    private void operatorInterchange() {
        
    }
}
