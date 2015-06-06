/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bana
 */
public class GoBackN {

    public static double reloj;
    public static double tMax;
    public static double tTimer;
    public static double[] timer;
    List<Mensaje> colaA;
    List<Mensaje> ventana;
    List<Mensaje> colaEnviador;
    List<Mensaje> colaB;
    public static boolean aLibre;
    public static boolean bLibre;
    public static int ultimoACKRecibido;
    public static int ultimoACKEnviado;
    Evento[] evento;
    Evento actual;

    public static void main(String[] args) {
        new GoBackN(1, 11, 111, true);//corre la simulación 1 vez, con timer 11 y tiempo maximo 111, en modo lento
    }

    GoBackN(int veces, double tTimer, double tMax, boolean modoLento) {
        for (int i = 0; i < veces; i++) {
            inicializar(tTimer, tMax);
            while (reloj < tMax) {
                //escoger el próximo evento
                for (Evento e : evento) {
                    if (e.getHoraOcurrencia() < actual.getHoraOcurrencia()) {
                        actual = e;
                    }
                }
                //ejecutar el evento
                actual.ejecutar();
                reloj++;//solo para probar
                //actualizar estado
                actualizarEstado(modoLento);
            }
            //guardar estadisticas de esta simulación
            
        }
        //imprimir estadisticas de todas

    }

    public void inicializar(double tTimer, double tMax) {
        //Inicializar variables
        reloj = 0;
        aLibre = true;
        bLibre = true;
        timer = new double[8];
        this.tMax = tMax;
        this.tTimer = tTimer;
        for (double d : timer) {
            d = Double.MAX_VALUE;
        }
        ultimoACKRecibido = 0;
        ultimoACKEnviado = 0;
        colaA = new ArrayList<Mensaje>();
        ventana = new ArrayList<Mensaje>();
        colaEnviador = new ArrayList<Mensaje>();
        colaB = new ArrayList<Mensaje>();
        evento = new Evento[6];
        evento[0] = LlegaMsjA.getInstance();
        evento[1] = LiberaA.getInstance();
        evento[2] = LiberaB.getInstance();
        evento[3] = LlegaACKaA.getInstance();
        evento[4] = LlegaFrameB.getInstance();
        evento[5] = VenceTimer.getInstance();

        for (Evento e : evento) {
            e.setHoraOcurrencia(Double.MAX_VALUE);
        }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        actual = evento[0];
    }
    void actualizarEstado(boolean modoLento){
        if(modoLento){
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(GoBackN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(reloj);
    }

}
