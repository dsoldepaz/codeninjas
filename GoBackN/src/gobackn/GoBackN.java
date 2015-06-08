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

    public double reloj;
    public double tMax;
    public double tTimer;
    public double[] timer;
    List<Mensaje> colaA;
    List<Mensaje> ventana;
    List<Mensaje> colaEnviador;
    List<Mensaje> colaB;
    List<Mensaje> mensajesEnviados;
    List<Mensaje> ACKenviados;
    public boolean aLibre;
    public boolean bLibre;
    public int ultimoACKRecibido;
    public int ultimoACKEnviado;
    Evento[] evento;
    Evento actual;
    private static GoBackN instance = null;
    Interfaz interfaz;
    int numeroMsj;

    public static void main(String[] args) {
        GoBackN gbn = GoBackN.getInstance();
        gbn.simular(1, 12, 50, true);
    }

    public static GoBackN getInstance() {
        if (instance == null) {
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
        numeroMsj = 0;

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
        interfaz = new Interfaz();
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);

        for (Evento e : evento) {
            e.setHoraOcurrencia(Double.MAX_VALUE);
        }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        actual = evento[0];
    }

    public void simular(int veces, double tTimer, double tMax, boolean modoLento) {
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
                //actualizar estado
                actualizarEstado(modoLento);
            }
            //guardar estadisticas de esta simulación

        }
        //imprimir estadisticas de todas

    }

    void actualizarEstado(boolean modoLento) {
        if (modoLento) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(GoBackN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        interfaz.limpiar();
        interfaz.printL("Simulación: #");
        interfaz.printL("Reloj: " + reloj);
        interfaz.printL("Longitud cola A: " + colaA.size());

        interfaz.printT("Mensajes en cola A: {");
        for (int i = 20; i > 8; i--) {
            try {
                interfaz.printT(colaA.get(i) + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printT("|");
        for (int i = 7; i > 0; i--) {
            try {
                interfaz.printT(ventana.get(i) + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");

        interfaz.printL("Último ACK recibido en A: " + ultimoACKRecibido);
        interfaz.printL("Último ACK enviado por B: " + ultimoACKEnviado);
        interfaz.printL("Total de frames recibidos por B: " + colaB.size());
        interfaz.printT("Últimos frames recibidos por B: {");
        for (int i = 0; i < 20; i++) {
            try {
                interfaz.printT(colaB.get(i) + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");

        interfaz.printL("Tipo de evento procesado: " + actual.getNombre());
    }

    double getMinimumValue(double[] a) {
        double val = Double.MAX_VALUE;
        for (double b : a) {
            if (b < val) {
                val = b;
            }
        }
        return val;
    }

}
