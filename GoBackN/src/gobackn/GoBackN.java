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
       GoBackN simulacion = new GoBackN();
    }
    GoBackN(){
        interfaz = new InterfazUsuario(this);
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
        interfaz.imprimir("Escriba los parametros de simulación y luego presione iniciar");
    }
    public void iniciarSimulacion(double tTimer, double tMax){
        inicializar(tTimer,tMax);
        
        while(reloj<tMax){
            reloj++;
        }
        
    }
    private void inicializar(double tTimer, double tMax){
        interfaz.limpiar();
        interfaz.imprimir("Inicializando variables...");
        LlegaMsjA llegaMsjA = new LlegaMsjA();
        LiberaA liberaA = new LiberaA();
        LlegaFrameB llegaFrameB = new LlegaFrameB();
        LiberaB liberaB = new LiberaB();
        LlegaACKaA llegaACKaA = new LlegaACKaA();
        VenceTimer venceTimer = new VenceTimer();
        
    }
}
