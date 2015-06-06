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
    public static double[] timer;
    public static Mensaje[] colaA;
    public static Mensaje[] ventana;
    public static Mensaje[] colaEnviador;
    public static Mensaje[] colaB;
    public static boolean aLibre;
    public static boolean bLibre;
    public static int ultimoACKRecibido;
    public static int ultimoACKEnviado;

    public static void main(String[] args) {
        new GoBackN();
    }

    GoBackN() {
        interfaz = new InterfazUsuario(this);
        interfaz.imprimirL("Escriba los parametros de simulación y luego presione iniciar");
    }

    public void Simular(double tTimer, double tMax) {
        //Inicializar variables
        interfaz.limpiar();
        reloj = 0;
        aLibre = true;
        bLibre = true;
        timer = new double[8];
        for (double d : timer) {
            d = Double.MAX_VALUE;
        }
        ultimoACKRecibido = 0;
        ultimoACKEnviado = 0;
        colaA = new Mensaje[1000];
        ventana = new Mensaje[1000];
        colaEnviador = new Mensaje[1000];
        colaB = new Mensaje[1000];
        Evento[] evento = {LlegaMsjA.getInstance(), LiberaA.getInstance(), LiberaB.getInstance(), LlegaACKaA.getInstance(), LlegaFrameB.getInstance(), VenceTimer.getInstance()};
        for (Evento e : evento) {
                e.setHoraOcurrencia(Double.MAX_VALUE);
            }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        Evento actual = evento[0];

        //ciclo de simulación
        while (reloj < tMax) {
            //escoger el próximo evento
            for (Evento e : evento) {
                if (e.getHoraOcurrencia() < actual.getHoraOcurrencia()) {
                    actual = e;
                }
            }
            //ejecutar el evento
            actual.ejecutar();
            //desplegar estado de la simulacion

            reloj++;//solo para probar interfaz
        }
        //calcular estadísticas        
        interfaz.imprimirL("Fin");
    }

}
