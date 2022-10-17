package prac1_ia;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;

import java.util.List;

public class SuccesorFunctionEnergy implements SuccessorFunction {
    
    private static List<Successor> sucesoresCreados;
    private static Board tablero; 
    private static ArrayList<ArrayList<Integer>> assignaciones; 
    private static ArrayList<Boolean> operators = new ArrayList<>();


    @Override
    public List getSuccessors(Object o) {
        sucesoresCreados = new ArrayList<>(); 
        tablero = (Board) o;
        assignaciones = tablero.getAssignaciones(); 
        int i=0;
        if(operators.get(i++)) operatorAdd();
        if(operators.get(i++)) operatorSwap();
        if(operators.get(i++)) operatorInterchange();
        if(operators.get(i)) operatorRemove();
        return sucesoresCreados; 
    }
    
    public void setOperators(Boolean opAdd, Boolean opSwap, Boolean opRemove, Boolean opInterchange){
        operators = new ArrayList<>(4);
        operators.add(opAdd);
        operators.add(opSwap);
        operators.add(opRemove);
        operators.add(opInterchange);

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
    
    private void operatorRemove() {
        for (int i = 0; i < assignaciones.size();++i) {
            for (int j = 0; j < assignaciones.get(i).size();++j) {
                Board copiaTablero = tablero; 
                copiaTablero.remove(i,j);
                sucesoresCreados.add(new Successor(
                                "Cliente borrado " + j + "en la central" + i,
                                copiaTablero));
            }
        }
    }
    
    private void operatorInterchange() {
        for (int i = 0; i < assignaciones.size(); ++i) {
            for (int c = 0; c < assignaciones.get(i).size();++c) {
                for (int j = i+1; j < assignaciones.size(); ++i) {
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
}
