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
    List<Mensaje> totalRecibidosB;
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
        gbn.simular(1, 12, 1000, true);//una vez, timer, tiempo max, modo lento
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
        numeroMsj = 0;

        for (int i = 0; i < timer.length; ++i) {
            timer[i] = Double.MAX_VALUE;
        }
        ultimoACKRecibido = 0;
        ultimoACKEnviado = 0;
        colaA = new ArrayList<>();
        ventana = new ArrayList<>();
        colaEnviador = new ArrayList<>();
        colaB = new ArrayList<>();
        totalRecibidosB = new ArrayList<>();
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
            this.tTimer = tTimer;
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
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GoBackN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        interfaz.limpiar();
        interfaz.printL("Simulación: ");
        interfaz.printL("Reloj: " + reloj);
        interfaz.printL("Último evento procesado: " + actual.getNombre());
        interfaz.printL("---");
        interfaz.printL("Longitud cola A: " + colaA.size());
        interfaz.printL("Último ACK recibido en A: " + ultimoACKRecibido);

        interfaz.printT("Mensajes en cola A: {");
        for (int i = 20; i > 8; i--) {
            try {
                interfaz.printT(colaA.get(i).getNumero() + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printT("|");//ventana
        for (int i = 7; i > 0; i--) {
            try {
                interfaz.printT(ventana.get(i).getNumero() + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");

        interfaz.printL("---");
        interfaz.printL("Último ACK enviado por B: " + ultimoACKEnviado);
        interfaz.printL("Total de frames recibidos por B: " + totalRecibidosB.size());
        interfaz.printT("Cola de frames por recibir en B: {");
        for (int i = 0; i < 20; i++) {
            try {
                interfaz.printT(colaB.get(i).getNumero() + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("Total de frames recibidos por B: " + totalRecibidosB.size());
        interfaz.printT("Historial de frames recibidos: {");
        for (int i = totalRecibidosB.size() - 21; i < totalRecibidosB.size() - 1; i++) {
            try {
                interfaz.printT(totalRecibidosB.get(i).getNumero() + ", ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");

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
