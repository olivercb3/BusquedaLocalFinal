/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prac1_ia;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.List;

public class SuccesorFunctionEnergy implements SuccessorFunction {
    
    private List<Successor> sucesores;
    

    @Override
    public List getSuccessors(Object o) {
        sucesores = new List<Successor> (o); 
    }
    
    public 
}
