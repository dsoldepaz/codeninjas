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

    InterfazUsuario interfaz;
    public static void main(String[] args) {
       GoBackN simulacion = new GoBackN();
    }
    GoBackN(){
        interfaz = new InterfazUsuario(this);
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
        interfaz.imprimir("Escriba los parametros de simulación y luego presione iniciar");
    }
    public void iniciar(double tTimer, double tMax){
        interfaz.limpiar();
        interfaz.imprimir("tTimer: "+Double.toString(tTimer));
        interfaz.imprimirL("tMax: "+Double.toString(tMax));
        
    }
}
