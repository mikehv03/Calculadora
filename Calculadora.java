/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto;

import java.util.ArrayList;

/**
 * Adolfo Yúnez, Patricio Bartolino Miguel Herrera, Bernardo del Río, Tomás Boom
 * Agosto-Septiembre 2023
 * Esto es un programa el cual a través de una cadena con operaciones, va a convertir las operaciones de infijo a postfijo para después evaluarlas y respetar el PEMDAS.
 */

public class Calculadora {
    private String operaciones; //Vamos a crear una clase calculadora y su único atributo va a ser la cadena.
    
    public Calculadora(){
        
    }
    
    public Calculadora(String operaciones){
        this.operaciones=operaciones;
    }
    
    public void setOperaciones(String operaciones){
        this.operaciones=operaciones;
    }
    
    public String getOperaciones(){
        return operaciones;
    }
    
    //Este es un método para analizar si el carácter es operador y mejorar la sintáxis de los programas. No se incluye el !, que simboliza (-), porque este operador trabaja diferente.
    private boolean esOperador (Character ch){
        boolean res=false;
        
        if(ch == '+' || ch == '*' || ch == '/' || ch == '^' || ch == '-')
            res=true;
        return res;
    }

    //Los siguientes métodos son para la revisión de sintáxis
    //Este primer método va a revisar la puntuación, es decir, evitar que el usuario ponga 1..0, ya que esto no es un número.
     public boolean revisadorPuntos(){
        boolean correcto= true;
        int contPuntos=0;
        int i=0;
        
        while(i< operaciones.length() && correcto){ //Se utiliza un while porque hay una bandera.
            //Si hay puntos, se van a contar todos los puntos que existen antes de un operador.
            if (operaciones.charAt(i) == '.'){ 
                 contPuntos+=1;
            }           
            else{
                //Cuando encuentre un operador, la cuenta se va a reiniciar, ya que significa que luego se va a utilizar otro número.
                if(esOperador(operaciones.charAt(i)) || operaciones.charAt(i) == '!') //El símbolo '!' que significa (-) también se vuelve parámetro.
                    contPuntos=0;
            }           
            if(contPuntos > 1) //Si el contador es mayor a 1, significa que hay dos puntos antes de un operador, por ende la sintáxis está mal.
                correcto= false;
            i++;
        }                
        return correcto;
    }
    
     //El siguiente método revisa que los parámetros estén bien balanceados, es decir, que cada '(' tenga un ')'.
    public boolean revisadorParentesis() {
        PilaA<Character> pila = new PilaA<>(); //Se utiliza una pila, porque es lo más eficiente.
        boolean res = false;
        boolean ban = true;
        int i = 0;
        Character ch;

        while (i < operaciones.length() && ban) {
            if (operaciones.charAt(i) == '(') {
                ch = operaciones.charAt(i); //Cuando encuentre un paréntesis '(' se agrega a la pila.
                pila.push(ch);
            } else if (operaciones.charAt(i) == ')') { //Cuando encuentra el contrario, i.e, ')' entonces:
                if (pila.isEmpty()) {//Pregunta si existió un paréntesis '(' antes.
                    ban = false; //Si no, arroja un error, ya que no está balanceado.
                } else {
                    pila.pop(); //Si sí, saca el paréntesis de la pila, porque ya encontró a su pareja.
                }
            }
            i++;
        }
        if (pila.isEmpty() && ban) { //La pila tiene que terminar vacía para que si estén bien balanceados.
            res = true;
        }
        return res;
    }
    
    //Este método va a revisar que no hayan dos signos seguidos, i.e, un 1++1. Solo se va a poder que existan dos símbolos seguidos, los cuales incluyan al ! que simboliza (-).
    //También que la cadena no empiece con níngun operador, el único permitido es el !.
    //Y que la cadena no acabe con ningun operador, en esta ocasión tampoco se permite el !
    public boolean revisadorSignos() {
        boolean correcto = true; //Esta es la bandera.
        PilaA<Character> signos = new PilaA();
        PilaA<Character> negativo = new PilaA(); //Se crea una pila aparte para revisar que no existan dos !!, ni tampoco un !+, !-, !*, etc.
        int tamanho, i=0;
        Character ch;
        
        ch=operaciones.charAt(0);
        if(ch == '+' || ch == '*' || ch == '/' || ch == '^') //Este if es necesario, ya que el primer char no puede ser un operador, a excepción del -.
            correcto=false;
        else
            i++;
        tamanho=operaciones.length(); 
        while(i<tamanho && correcto){
            ch=operaciones.charAt(i);
            if(esOperador(ch)) 
                    if(!negativo.isEmpty() || !signos.isEmpty()) //Si hay un operador, las pilas de signos y negativo tienen que estar vacías, esto para evitar que hayan dos oepradores seguidos.
                        correcto=false;
                    else //Si están vacías, se agrega el operador.
                        signos.push(ch);
            else{
                if(ch.equals('!')){ 
                    if(!negativo.isEmpty()) //Si la pila negativo no está vacía, significa que hay dos ! seguidos, i.e, !!. Por tanto es un error.
                        correcto=false;
                    else
                        negativo.push(ch);
                }
                else{
                    if(ch != '('){ //Esta condición es importante para también incluir el caso donde el usuario pueda poner !(*5+2)
                        if (!signos.isEmpty()) 
                            signos.pop();
                        if(!negativo.isEmpty())
                            negativo.pop();
                    }
                }
            }
            i++;
        }
        //Ambas pilas tienen que terminar vacías, ya que si no esto significa que la cadena acabo con algún operador o con el símbolo !
        if (!signos.isEmpty() && correcto) 
            correcto = false;
        if(!negativo.isEmpty() && correcto)
            correcto =false;
        return correcto;
    }

    //Este método ya no es para revisar, pero sirve para respetar las jerarquías de operaciones cuando se cambie a postfijo.
    private int prioridades(Character op) {
        int ans = -1; //Se inicia la variable con un -1, para que los números tengan la prioridad más baja.

        switch (op) {
            case '^':
                ans = 3;
                break;
            case '*':
            case '/':
                ans = 2;
                break;
            case '+':
            case '-':
            case '!':
                ans = 1;
                break;
            case '(':
            case ')':
                ans = 0;
                break;
            default:
                break;
        }
        return ans;
    }

    //Este método convierte la operación de infijo a postfijo, la cual la almacena en un ArrayList para facilitar su evaluación.
    public ArrayList convertidor() {
        double numero;
        ArrayList postfijo = new ArrayList();
        Character ch;
        PilaA pila = new PilaA();
        StringBuilder sb = new StringBuilder(); //Para poder pasar los números de String a Double se va a utilizar un StringBuilder.
        StringBuilder aux = new StringBuilder();
        boolean bandera;
        
        ch=operaciones.charAt(0);
        if(ch=='-'){ //Se va a cambiar el primer símbolo '-' a '!', para que resultados que den en negativo se puedan seguir usando.
            aux.append(operaciones);
            aux.setCharAt(0, '!');
            operaciones=aux.toString();
        }  
        for(int i=0; i<operaciones.length(); i++) {
            ch = operaciones.charAt(i);
            if (prioridades(ch) < 0) { //Esto significa que es un número, por lo que se agrega al StringBuilder.
                sb.append(ch);
            } else {
                //Significa que es un operador
                switch (ch) {
                    case '(':
                        pila.push(ch); //Esto va a servir para respetar la jerarquía de operaciones
                        break;
                    case ')':
                        //Se va a buscar el otro paréntesis para que se balancee la operación.
                        while ((Character) pila.peek() != '(') {
                            if(sb.length() != 0){ //Es necesario porque pueden haber más de una operación entre los paréntesis, lo que haría que algunas veces no hayan números guardados.
                                numero = Double.parseDouble(sb.toString()); //El número se convierte a un double.
                                sb.setLength(0); //Se reinicia el StringBuilder.
                                postfijo.add(numero);
                            }
                            postfijo.add(pila.pop()); //Como ya fueron ordenados por el caso default, entonces se van a ir agregando todos los operadores que faltaron hasta encontrar en donde incio el paréntesis.
                        }
                        pila.pop(); //Sacar el paréntesis.
                        break;
                    case '!':
                        i++; //Se agrega el i++, porque ya se sabe que hay un !, y se quiere saber todo lo que va a modificar este símbolo.
                        ch = operaciones.charAt(i);
                        if(operaciones.charAt(i)!= '('){ //Esto significa que solo va a modificar a un número.
                            bandera=true;
                            while(prioridades(ch) < 0 && bandera){ //La condición significa que se va a buscar hasta que exista otro operador.
                                sb.append(ch);
                                i++;
                                if(i >= operaciones.length()) //Es necesario porque si el número que contenía el !, era el último el ciclo se iba a salir de rango.
                                    bandera=false;
                                else
                                    ch=operaciones.charAt(i);
                            }   
                            numero = Double.parseDouble(sb.toString()); //Se pasa todo el número que está siendo modificado por el !, a un double.
                            sb.setLength(0);
                            numero = numero*-1; //Se multiplica por -1, porque eso hace ese símbolo.
                            postfijo.add(numero);
                        }
                        else{
                            pila.push('!'); //Si va a modificar a un paréntesis solo se agrega a la pila, para después hacer la evaluación.
                        }
                        i--; //Se tiene que restar uno, porque al empezar esta parte del código con un i++ para observar si lo que sigue es un paréntesis o un número, se está perdiendo cierta información.
                        break;
                    default:
                        if (sb.length() != 0) { //Mismo caso que en el ')'.
                            numero = Double.parseDouble(sb.toString());
                            sb.setLength(0);
                            postfijo.add(numero);
                        }
                        if (pila.isEmpty()) { //Significa que no hay operadores guardados
                            pila.push(ch);
                        } else {
                            while (!pila.isEmpty() && prioridades((Character) pila.peek()) >= prioridades(ch)) { 
                                //Esto va a comparar la prioridad del operador con las de los operadores que ya estaban guardados
                                //Para que agregue todos los operadores que tienen más jerarquías que el nuevo o los operadores que estaban antes en la operación.
                                postfijo.add(pila.pop());
                            }
                            pila.push(ch); //Se agrega ese operador a la pila.
                        }
                        break;
                }
            }
        }
        if (sb.length() != 0) { //Esto significa que hay un número el cual no fue guardado en la notación posfija. 
            numero = Double.parseDouble(sb.toString());
            postfijo.add(numero);
        }
        while (!pila.isEmpty()) { //Esto significa que falta un último operador para poner en la notación posfija.
            postfijo.add(pila.pop());
        }
        return postfijo;
    }
    
    //Después de pasar la operación a postfijo, ahora se va a evaluar tomando los dos números anterior y el símbolo.
    public double evalua(ArrayList postfijo) {
        double ans;
        double v;
        double x, y;
        PilaA<Double> pila = new PilaA();
        Character p;
        
        //El ciclo for, va a estar operaciones hasta que se acabe el postfijo, y eso por el algoritmo que hay detrás, va a hacer que solo quede un número en la pila.
        for (int i = 0; i < postfijo.size(); i++){
            //Se utiliza un try catch, para diferenciar cuando sí es un número y cuando es un operador.
            try {
                pila.push((Double) postfijo.get(i)); //Si es un número se guarda directamente en una pila.
            } catch (ClassCastException err) { 
                p = (Character) postfijo.get(i); //Cuando es un operador.
                switch (p) {
                    //Se observa que operador es, y se sacan los dos números que están guardados en la pila (esto se sabe porque la operación está puesta en notación postfija).
                    //Se realiza la operación.
                    //Es importante notar los casos de '-', '!', '/' y '^'
                    case '+':
                        x = (double) pila.pop();
                        y = (double) pila.pop();
                        v = x + y;
                        pila.push(v);
                        break;
                    case '-':
                        //El segundo número de la notación postfija es en realidad el número que se le resta al primer número.
                        //Por lo tanto el número que está hasta arriba de la pila, es el que se le va a restar.
                        x = (double) pila.pop();
                        y = (double) pila.pop();
                        v = y - x;
                        pila.push(v);
                        break;
                    case '!':
                        //Este símbolo multiplica por -1, a el número que estaba anterior a este símbolo. Por lo tanto solo se multiplica el primer número de la pila.
                        x = (double) pila.pop();
                        v = x*-1;
                        pila.push(v);
                        break;
                    case '*':
                        x = (double) pila.pop();
                        y = (double) pila.pop();
                        v = x * y;
                       pila.push(v);
                        break;
                    case '/':
                        //Mismo caso que con la resta.
                        x = (double) pila.pop();
                        y = (double) pila.pop();
                        v = y / x;
                        pila.push(v);
                        break;
                    case '^':
                        //Mismo caso que con la resta.
                        x = (double) pila.pop();
                        y = (double) pila.pop();
                        v = Math.pow(y, x);
                        pila.push(v);
                        break;
                    default:
                        break;
                }
            }
        }
        ans = (double) pila.pop(); //El único número que quedo en la pila es el resultado.
        return ans;
    }

    public static void main(String[] args) {
        Calculadora calc;
        ArrayList convertidor;
        
        calc=new Calculadora("((7^4)-(3^3))*(2^5)+(9^2)/10");
        System.out.println(calc.revisadorSignos());
        if(calc.revisadorParentesis() && calc.revisadorPuntos() && calc.revisadorSignos()){
            convertidor=calc.convertidor();
            System.out.println(convertidor);
            System.out.println(calc.evalua(convertidor));
        }
        else
            System.out.println("ERROR");
    }
}
