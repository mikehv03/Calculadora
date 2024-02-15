/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

/**
 * Adolfo Yúnez, Patricio Bartolino Miguel Herrera, Bernardo del Río, Tomás Boom
 * Septiembre 2023
 * Esta es una clase que sirve para mandar un mensaje cuando la pila este vacía y se traten de hacer los métodos de peek y pop.
 */
public class ExcepcionColeccionVacia extends RuntimeException {
    public ExcepcionColeccionVacia(){
        
    }
    
    public ExcepcionColeccionVacia(String message){
        super(message);
    }
}
