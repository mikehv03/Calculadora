/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proyecto;

/**
 * Adolfo Yúnez, Patricio Bartolino Miguel Herrera, Bernardo del Río, Tomás Boom
 * Agosto 2023
 * Esta es una interface para las pilas, para que se vuelvan más generales los métodos en la clase Calculadora
 */
public interface PilaADT <T>{
    public void push(T dato);
    public T pop();
    public boolean isEmpty();
    public T peek();
    public void multiPop(int n);
}
