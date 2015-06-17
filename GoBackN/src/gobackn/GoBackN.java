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
import javax.swing.JOptionPane;

/**
 *
 * @author bana
 */
public class GoBackN {

    public double reloj;
    public double tMaxSimulacion;
    public double tTimer;
    public double[] timer;
    List<Mensaje> colaA;
    List<Mensaje> ventana;
    List<Mensaje> colaEnviador;
    List<Mensaje> colaB;
    List<Mensaje> HistorialRecibidosB;
    public boolean aLibre;
    public boolean bLibre;
    public int ultimoACKRecibidoPorA;
    public int ultimoACKProcesadoPorA;
    public int ultimoACKEnviadoPorB;
    Evento[] evento;
    Evento actual;
    private static GoBackN instance = null;
    Interfaz interfaz;
    int numeroMsj;
    int frameEsperado;
    public Estadisticador estadisticador;

    public static void main(String[] args) {
        GoBackN gbn = GoBackN.getInstance();
        String veces = JOptionPane.showInputDialog("Número de veces que se va a correr la simulación:", "10");
        if (veces == null) {
            JOptionPane.showMessageDialog(null, "Se canceló la simulación");
            System.exit(0);
        }
        String tMax = JOptionPane.showInputDialog("Tiempo total en segundos para correr cada vez la simulación:", "2000");
        if (tMax == null) {
            JOptionPane.showMessageDialog(null, "Se canceló la simulación");
            System.exit(0);
        }
        String timer = JOptionPane.showInputDialog("El tiempo de espera en A para reenvío de un frame:", "12");
        if (timer == null) {
            JOptionPane.showMessageDialog(null, "Se canceló la simulación");
            System.exit(0);
        }
        int modoLento = JOptionPane.showConfirmDialog(null, "¿Desea ver la simulación correr en modo lento?", "", JOptionPane.YES_NO_OPTION);
        boolean lento = false;
        if (modoLento == JOptionPane.YES_OPTION) {
            lento = true;
        }
        gbn.simular(Integer.parseInt(veces), Integer.parseInt(timer), Integer.parseInt(tMax), lento);// veces, timer, tiempo max, modo lento
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

        for (int i = 0; i < timer.length; ++i) {
            timer[i] = Double.MAX_VALUE;
        }
        ultimoACKRecibidoPorA = 0;
        ultimoACKProcesadoPorA = 0;
        ultimoACKEnviadoPorB = 0;
        colaA = new ArrayList<>();
        ventana = new ArrayList<>();
        colaEnviador = new ArrayList<>();
        colaB = new ArrayList<>();
        HistorialRecibidosB = new ArrayList<>();

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
        interfaz.requestFocus();

        for (Evento e : evento) {
            e.setHoraOcurrencia(Double.MAX_VALUE);
        }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        actual = evento[0];

        estadisticador = new Estadisticador();
    }

    public void simular(int veces, double tTimer, double tMax, boolean modoLento) {
        this.tTimer = tTimer;
        for (int i = 0; i < veces; i++) {
            //reiniciar 
            reiniciar();

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
                //actualizar estado
                actualizarEstado(veces, tTimer, tMax, modoLento, i);
            }
            //estadisticas de esta simulación
            interfaz.printT(this.estadisticador.estadisticasDelPrograma());

        }
        //imprimir estadisticas de todas

    }

    void actualizarEstado(int veces, double tTimer, double tMax, boolean modoLento, int vez) {
        if (modoLento) {
            try {
                Thread.sleep(666);
            } catch (InterruptedException ex) {
                Logger.getLogger(GoBackN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        interfaz.limpiar();
        interfaz.printL("Simulación: " + vez + " de " + veces);
        interfaz.printL("Tiempo total en segundos para correr cada vez la simulación: " + tMax);
        interfaz.printL("El tiempo de espera en A para reenvío de un frame: " + tTimer);
        interfaz.printL("Reloj: " + reloj);
        interfaz.printL("Último evento procesado: " + actual.getNombre());
        interfaz.printL("---");
        int longitudTotal = colaA.size() + ventana.size();
        interfaz.printL("Longitud cola A: " + longitudTotal);
        interfaz.printL("Último ACK procesado en A: " + ultimoACKProcesadoPorA);

        interfaz.printT("Mensajes en cola A: { ");
        for (int i = 20; i >= 0; i--) {
            try {
                interfaz.printT("[" + colaA.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printT("| ");//ventana
        for (int i = 7; i >= 0; i--) {
            try {
                interfaz.printT("[" + ventana.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");
        interfaz.printT("Cola del enviador: { ");
        for (int i = 20; i >= 0; i--) {
            try {
                interfaz.printT("[" + colaEnviador.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");
        interfaz.printL("---");
        interfaz.printT("Cola de frames por recibir en B: { ");
        for (int i = 0; i < 20; i++) {
            try {
                interfaz.printT("[" + colaB.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printL("}");
        interfaz.printL("Frame esperado: " + frameEsperado);
        interfaz.printL("Total de frames recibidos por B: " + HistorialRecibidosB.size());
        interfaz.printL("Último ACK enviado por B: " + ultimoACKEnviadoPorB);
        interfaz.printT("Historial de frames recibidos: { ");
        for (int i = HistorialRecibidosB.size() - 1; i > HistorialRecibidosB.size() - 21; i--) {
            try {
                interfaz.printT("[" + HistorialRecibidosB.get(i).getNumero() + "] ");
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

    void reiniciar() {
        numeroMsj = 0;
        frameEsperado = 1;
        for (int it = 0; it < timer.length; ++it) {
            timer[it] = Double.MAX_VALUE;
        }
        ultimoACKRecibidoPorA = 0;
        ultimoACKProcesadoPorA = 0;
        ultimoACKEnviadoPorB = 0;

        reloj = 0;
        aLibre = true;
        bLibre = true;
        colaA.clear();
        ventana.clear();
        colaEnviador.clear();
        colaB.clear();
        HistorialRecibidosB.clear();
        for (Evento e : evento) {
            e.setHoraOcurrencia(Double.MAX_VALUE);
        }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        actual = evento[0];
    }

}
