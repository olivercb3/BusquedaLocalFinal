package prac1_ia;

import IA.Energia.Clientes;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

import java.util.ArrayList;

import static prac1_ia.Practica.distancias;
/**
 * Clase que representa el estado.
 */
public class Board {

    /**
     * Instancia dee la clase proporcionada de clientes, con array de todas las clientes
     */
    private static Clientes clientes;
    /**
     * Instancia dee la clase proporcionada de centrales, con array de todas las centrales
     */
    private static Centrales centrales;
    /**
     * Matriz con todas las centrales en las filas y en las columnas de cada fila los clientes asignados a esta.
     * La última fila representa la central vacía, que simboliza una central no existente para representar los clientes no asignados.
     */
    private ArrayList<ArrayList<Integer>> assignaciones;

    /**
     * Vector con la producción disponible para cada central
     */
    private double [] produccionRestante;

    /**
     * Constructor del estado.
     * @param cen Centrales que se utilizaran en el problema
     * @param cl Centrales que se utilizaran en el problema
     * Genera un estado con todos los clientes sin asignar, o aplicando alguna de las estrategias de generación de solución inicial
     */
    public Board(Centrales cen, Clientes cl) {
        centrales = cen;
        clientes = cl;
        assignaciones = new ArrayList<ArrayList<Integer>>();
        produccionRestante = new double[cen.size()];
        for (int i = 0; i < cen.size()+1; ++i) {
            assignaciones.add(new ArrayList<Integer>());
        }
        //Ponemos todos los clientes en la central vacia
        for (int i = 0; i < cl.size(); ++i) {
            assignaciones.get(cen.size()).add(i);
        }
        InitialState();
    }

    /**
     * Constructor copia del estado.
     * @param cen Centrales que se utilizaran en el problema
     * @param cl Centrales que se utilizaran en el problema
     * @param a Assignaciones del estado en el momento se quiere copiar
     * @param p Prododucciones restantes de las centrales en el momento que se quiere copiar
     * Genera un estado con las mismas características especificadas en los parámetros
     */
    public Board(Centrales cen, Clientes cl, ArrayList<ArrayList<Integer>> a, double [] p) {
        centrales = cen;
        clientes = cl;
        assignaciones = new ArrayList<ArrayList<Integer>>();
        produccionRestante = new double[p.length];
        for (int i = 0; i < a.size(); ++i) {
            assignaciones.add(new ArrayList<Integer>());
            for (int j = 0; j < a.get(i).size();++j)
                assignaciones.get(i).add(a.get(i).get(j));
        }

        for (int i = 0; i < p.length; ++i)
            produccionRestante[i] = p[i];
    }

    /**
     * Estrategia simple de generación de solución inicial. Simplemente, asigna primero los clientes prioritarios, luego en las centrales
     * con producción sobrante asigna los clientes restantes. La estrategia de asignación es seguir el orden de número de centrales
     * y de clientes.
     */

    private void InitialState() {

        int indice_central = 0;
        ArrayList<Integer> cental_vacia = assignaciones.get(centrales.size());
        for (int i = 0; i < centrales.size(); ++i) {

            boolean max_superat = false;
            produccionRestante[i] = centrales.get(i).getProduccion();
            for (int j = 0; j < cental_vacia.size() && max_superat == false; ++j) {

                int client = cental_vacia.get(j);
                Cliente cl = clientes.get(client);
                double dist = distancias.get_dist(i, client);
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);

                if (consumo < produccionRestante[i]) {

                    if (cl.getContrato() == 1 && consumo < produccionRestante[i]) {
                        assignaciones.get(i).add(client);
                        assignaciones.get(centrales.size()).remove(j);
                        --j;
                        produccionRestante[i] -= consumo;
                    }
                    else if (cl.getContrato() == 1) {
                        max_superat = true;
                        ++indice_central;
                    }
                }
            }

        }
        //En este punto es solucion, el comprobar se puede comentar para aumentar rendimiento
        comprobar_solucion();

        for (int i = indice_central; i < centrales.size(); ++i) {

            boolean max_superat = false;
            for (int j = 0; j < cental_vacia.size() && max_superat == false; ++j) {

                int client = cental_vacia.get(j);
                Cliente cl = clientes.get(client);
                double dist = distancias.get_dist(i, client);
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dist);
                if (cl.getContrato() == 0 && consumo < produccionRestante[i]) {
                    assignaciones.get(i).add(client);
                    cental_vacia.remove(j);
                    --j;
                    produccionRestante[i] -= consumo;
                }
                else if(cl.getContrato() == 0){
                    max_superat = true;
                    ++indice_central;
                }
            }
        }
    }

    /**
     * Estrategia greedy de generación de solución inicial. Simplemente, asigna primero los clientes prioritarios, luego en las centrales
     * con producción sobrante asigna los clientes restantes. La estrategia de asignación es intentar asignar a los clientes prioritarios
     * a su central más cercana, si no es posible en la primera disponible que su distancia sea inferior a la media de las distancias de
     * ese cliente con todas las centrales. Cuando acabe con los clientes prioritarios, en las centrales con producción disponible incluirá
     * los clientes que se encuentren por debajo de la media de distancias de esa central.
     */

    private void InitialState_Greedy() {

        for (int i = 0; i < centrales.size(); ++i)
            produccionRestante[i] = centrales.get(i).getProduccion();

        ArrayList<Integer> cental_vacia = assignaciones.get(centrales.size());
        for (int j = 0; j < cental_vacia.size(); ++j) {

            int client = cental_vacia.get(j);
            Cliente cl = clientes.get(client);
            double[][] d = distancias.getDistancias();
            if (cl.getContrato() == 1) {

                double [] centrales_cliente = getColumn(d,client, centrales.size());
                int min_distance = index_minimumArray(centrales_cliente);
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[min_distance][client]);

                if (consumo < produccionRestante[min_distance]) {
                    produccionRestante[min_distance] -= consumo;
                    assignaciones.get(min_distance).add(client);
                    assignaciones.get(centrales.size()).remove(j);
                    --j;
                }
                else  {

                    double media_distancia = meanArray(centrales_cliente);
                    boolean encontrado = false;

                    for (int i = 0; encontrado == false && i < centrales.size(); ++i) {

                        if( consumo < produccionRestante[i] && d[i][client] < media_distancia ) {
                            produccionRestante[i] -= consumo;
                            assignaciones.get(i).add(client);
                            assignaciones.get(centrales.size()).remove(j);
                            --j;
                            encontrado = true;
                        }
                    }
                }
            }
        }
        //En este punto es solucion, el comprobar se puede comentar para aumentar rendimiento
        comprobar_solucion();

        for (int i = 0; i < centrales.size(); ++i) {

            boolean max_superat = false;
            double[][] d = distancias.getDistancias();
            double media = meanArray(d[i]);
            for (int j = 0; j < cental_vacia.size() && max_superat == false; ++j) {

                int client = cental_vacia.get(j);
                Cliente cl = clientes.get(client);
                double dis = d[i][client];
                double consumo = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(dis);
                if (cl.getContrato() == 0 && consumo < produccionRestante[i] && dis < media) {
                    assignaciones.get(i).add(client);
                    produccionRestante[i] -= consumo;
                    assignaciones.get(centrales.size()).remove(j);
                    --j;
                }
                else if(1 > produccionRestante[i])
                    max_superat = true;
            }
        }
    }

    //Getters

    /**
     * Getter de la matriz con las asignaciones
     */
    public ArrayList<ArrayList<Integer>> getAssignaciones() {
        return assignaciones;
    }

    /**
     * Getter del vector con las producciones restantes de las matrizes
     */
    public double[] getProduccionRestante() {
        return produccionRestante;
    }

    /**
     * Getter de las centrales usadas en el problema
     */
    public Centrales getCentrales() {
        return centrales; 
    }

    /**
     * Getter de los clientes usadas en el problema
     */
    public Clientes getClientes() {
        return clientes; 
    }

    //Funciones Auxiliares

    /**
     * Función auxiliar para sacar el valor medio de un vector de double
     * @param array Vector del que se quiere obtener la media
     */
    public static double meanArray(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value;
        }
        return sum/array.length;
    }
    /**
     * Función auxiliar para obtener el la posicion que tiene el valor minimo de un vector
     * @param array Vector del que se quiere obtener el minimo
     */
    public static int index_minimumArray(double[] array) {
        double res = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < array.length;++i) {
            if(array[i] < res) {
                index = i;
                res = array[i];
            }
        }
        return index;
    }

    /**
     * Función auxiliar para obtener una columna de una matriz de double
     * @param array Matriz de la que se quiere obtener la columna
     * @param index Indice de la columna que se quiere obtener
     * @param size_column Tamaño de la columna que se quiere obtener
     */
    public static double[] getColumn(double[][] array, int index, int size_column){
        double[] column = new double[size_column];
        for(int i=0; i < size_column; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    /**
     * Función auxiliar para comprobar que la matriz de asignaciones contiene una solución válida
     */
    private void comprobar_solucion() {

        ArrayList<Integer> cental_vacia = assignaciones.get(centrales.size());
        boolean solucion = true;
        for (int j = 0;solucion && j < cental_vacia.size(); ++j) {

            int client = cental_vacia.get(j);
            Cliente cl = clientes.get(client);
            if(cl.getContrato() == 1){
                solucion = false;
                //System.out.println("NO ES SOLUCION");
                System.out.println();
            }
        }
    }

    /*
    void afegeix(int central, int no_assignado) {
        assignaciones.get(central).add(no_assignado);
    }

    void esborra(int central, int no_assignado) {
        assignaciones.get(central).remove(no_assignado);
    }

    //en el remove creo que no hay ninguna condicion concreta, confirmadmelo
    //haze falta comprobar que no vayas a quitar un prioritario, porque dejaria de ser solucion
    public void remove(int central,int cliente) {
        int indice_cliente = assignaciones.get(central).get(cliente);
        produccionRestante[central] += clientes.get(indice_cliente).getConsumo();
        assignaciones.get(central).remove(cliente);
        assignaciones.get(assignaciones.size()-1).add(indice_cliente);
    }

    //es el cambio de dos clientes en activo
    //No has controlado la central vacia, esta no tiene produccion restante i no se puede assignar un
    //prioritario a ella
    public void interchange(int central1,int central2,int cliente1,int cliente2) {
        double restanteCentral1 = produccionRestante[central1];
        double restanteCentral2 = produccionRestante[central2];
        //System.out.println(restanteCentral1 + " " + restanteCentral2);
        Cliente c1 = clientes.get(cliente1);
        Cliente c2 = clientes.get(cliente2);

        if (restanteCentral1+c1.getConsumo() > c2.getConsumo() && restanteCentral2+c2.getConsumo() > c1.getConsumo()) {
            //PRIMER CLIENTE-CENTRAL
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central1).add(cliente2);
            //lo borro de la central
            assignaciones.get(central2).remove(cliente2);

            //SEGUNDO CLIENTE-CENTRAL
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central2).add(cliente1);
            //lo borro de la central
            assignaciones.get(central1).remove(cliente1);
        }
    }

    public void swap(int central,int clienteAssignado,int clienteNoAssignado) {
        int indice_assignado = assignaciones.get(central).get(clienteAssignado);
        int indice_no_assignado = assignaciones.get(assignaciones.size()-1).get(clienteNoAssignado);
        Cliente cl = clientes.get(indice_no_assignado);
        Cliente cl2 = clientes.get(indice_assignado);
        //entiendo que el 1 es tarifa obligatoria
        if (cl2.getTipo() != 0) {
            double restanteCentral1 = produccionRestante[central];
            double[][] d = distancias.getDistancias();
            double consumo1 = cl.getConsumo() + cl.getConsumo() * VEnergia.getPerdida(d[central][clienteNoAssignado]);
            double consumo2 = cl2.getConsumo() + cl2.getConsumo() * VEnergia.getPerdida(d[central][clienteAssignado]);
            if (restanteCentral1+consumo2 > consumo1) {
            produccionRestante[central] += consumo2;
            produccionRestante[central] -= consumo1;
            //añado el nuevo cliente a la central en cuestión
            assignaciones.get(central).remove(clienteAssignado);
            assignaciones.get(central).add(indice_no_assignado);
            //lo borro de la central

            assignaciones.get(assignaciones.size()-1).remove(clienteNoAssignado);
            assignaciones.get(assignaciones.size()-1).add(indice_assignado);
            }
        }
    }
    */
}
