/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 * Adolfo Yúnez, Patricio Bartolino Miguel Herrera, Bernardo del Río, Tomás Boom
 * Agosto-Septiembre 2023
 * Esta clase sirve para crear las pilas, que nos van a servir en ciertos métodos de la clase Calculadora. La pila es una estructura de datos, en la cual se reconoce un único extremo para insertar/quitar elementos.
 */
public class PilaA <T> implements PilaADT <T>{
    private T[] pila;
    private int tope;
    private final int MAX_PILA=20;
    
    public PilaA(){
        pila = (T[]) new Object[MAX_PILA];
        tope= -1; //Indica pila vacía.
    }
    
    public PilaA(int max){
        pila = (T[]) new Object[max];
        tope= -1; //Indica pila vacía.
    }

    @Override
    public void push(T dato) {
        if(tope==pila.length -1) //No hay espacio
            expande();
        tope++;
        pila[tope] =dato;
    }

    @Override
    public T pop() {
        if(isEmpty())
            throw new ExcepcionColeccionVacia("La pila está vacía");
        T resultado = pila[tope];
        pila[tope]=null;
        tope--;
        return resultado;
    }

    @Override
    public boolean isEmpty() {
        boolean res;
        
        if(tope==-1)
            res=true;
        else
            res=false;
        return res;
    }

    @Override
    public T peek() {
        if(isEmpty())
            throw new ExcepcionColeccionVacia("La pila está vacía");
        return pila[tope];
    }
    
    private void expande(){
        T[] masGrande= (T[]) new Object[pila.length*2];
        for(int i=0; i<pila.length; i++)
            masGrande[i]=pila[i];
        pila=masGrande;
    }
    
    @Override
    public void multiPop(int n) {
        if(n<=tope+1){
            for(int i=tope; i<n; i++)
                this.pop();
        }
    }
    
    public String toString(){
        StringBuilder cad = new StringBuilder();
        
       for(int i=0; i<=tope; i++){
           cad.append(pila[i]+"\n");
       }
       return cad.toString();
    }
}
