/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bana
 */
public class GoBackN {

    public double reloj;
    public double tMax;
    public double tTimer;
    public double[] timer;
    List<Mensaje> colaA;
    List<Mensaje> ventana;
    List<Mensaje> colaEnviador;
    List<Mensaje> colaB;
    public boolean aLibre;
    public boolean bLibre;
    public int ultimoACKRecibido;
    public int ultimoACKEnviado;
    Evento[] evento;
    Evento actual;
    private static GoBackN instance = null;
    
    public static void main(String[] args) {
        GoBackN gbn = GoBackN.getInstance();
        gbn.simular(1,1,1);
    }
    
    public static GoBackN getInstance() {
      if(instance == null) {
         instance = new GoBackN();
      }
      return instance;
   }
    protected GoBackN() {
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
    public void simular (int veces, double tTimer, double tMax){
        for (int i = 0; i < veces; i++) {
            //inicializar(tTimer, tMax);
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
                actualizarEstado();
            }
            //guardar estadisticas de esta simulación
            
        }
        //imprimir estadisticas de todas

    }
    
    void actualizarEstado(){
        System.out.println(reloj);
    }

}
