/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

/**
 *
 * @author bana
 */
public class GoBackN {

    public static InterfazUsuario interfaz;
    public static double reloj;
    public static double tMax;

    public static void main(String[] args) {
        new GoBackN();
    }

    GoBackN() {
        interfaz = new InterfazUsuario(this);
        interfaz.imprimir("Escriba los parametros de simulación y luego presione iniciar");
    }

    public void Simular(double tTimer, double tMax) {
        interfaz.limpiar();
        reloj = 0;
        
        while (reloj < tMax) {
            reloj++;
        }
        interfaz.imprimirL("Fin de la simulación.");
    }
}
