package protocolo2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Protocolo2 {

    public double reloj;
    public double tMaxSimulacion;
    List<Mensaje> colaA;
    List<Mensaje> colaB;
    List<Mensaje> colaEscritor;
    List<Mensaje> HistorialRecibidosB;
    public boolean aLibre;
    public boolean escritorLibre;

    Evento[] evento;
    Evento actual;
    private static Protocolo2 instance = null;
    Interfaz interfaz;
    int numeroMsj;
    int frameEsperado;
    public Estadisticador estadisticador;

    public static void main(String[] args) {
        
        Protocolo2 simulador = Protocolo2.getInstance();
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
        int modoLento = JOptionPane.showConfirmDialog(null, "¿Desea ver la simulación correr en modo lento?", "", JOptionPane.YES_NO_OPTION);
        boolean lento = false;
        if (modoLento == JOptionPane.YES_OPTION) {
            lento = true;
        }
        simulador.simular(Integer.parseInt(veces), Double.parseDouble(tMax), lento);// veces, timer, tiempo max, modo lento
    }

    public static Protocolo2 getInstance() {
        if (instance == null) {
            instance = new Protocolo2();
        }
        return instance;
    }

    protected Protocolo2() {
        reloj = 0;
        aLibre = true;
        escritorLibre = true;
        colaA = new ArrayList<>();        
        colaB = new ArrayList<>();
        colaEscritor = new ArrayList<>();
        HistorialRecibidosB = new ArrayList<>();

        evento = new Evento[4];
        evento[0] = LlegaMsjA.getInstance();
        evento[1] = LiberaA.getInstance();
        evento[2] = LiberaEscritor.getInstance();
        evento[3] = LlegaFrameB.getInstance();

        interfaz = new Interfaz();


        for (Evento e : evento) {
            e.setHoraOcurrencia(Double.MAX_VALUE);
        }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        actual = evento[0];

        estadisticador = new Estadisticador();
    }

    public void simular(int veces, double tMax, boolean modoLento) {

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
                actualizarEstado(veces, tMax, modoLento, i + 1);
            }
            //estadisticas de esta simulación
            this.estadisticador.calcularEstadisticas();
            imprimirEstadisticasUnaSimulacion(i + 1);

        }
        //imprimir estadisticas de todas
        this.estadisticador.calcularEstadisticasGlobales();
        imprimirEstadisticasGlobales();

    }

    void actualizarEstado(int veces, double tMax, boolean modoLento, int vez) {
        if (modoLento) {
            try {
                Thread.sleep(666);
            } catch (InterruptedException ex) {
                Logger.getLogger(Protocolo2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        interfaz.limpiarVar();
        interfaz.printLVar("Tiempo total en segundos para correr cada vez la simulación: " + tMax);
        interfaz.printLVar("---");
        interfaz.printLVar("Simulación: " + vez + " de " + veces);
        interfaz.printLVar("Reloj: " + reloj);
        interfaz.printLVar("Último evento procesado: " + actual.getNombre());
        interfaz.printLVar("---");
        int longitudTotal = colaA.size();
        interfaz.printLVar("Longitud cola A: " + longitudTotal);

        interfaz.printTVar("Mensajes en cola A: { ");
        for (int i = 20; i >= 0; i--) {
            try {
                interfaz.printTVar("[" + colaA.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }

        interfaz.printLVar("}");
        
        interfaz.printLVar("---");
        interfaz.printTVar("Cola de frames por recibir en B: { ");
        for (int i = 0; i < 20; i++) {
            try {
                interfaz.printTVar("[" + colaB.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printLVar("}");
        interfaz.printLVar("Frame esperado: " + frameEsperado);
        interfaz.printLVar("Total de frames recibidos por B: " + HistorialRecibidosB.size());
        
        interfaz.printTVar("Cola del proceso escritor en B: { ");
        for (int i = 0; i < 20; i++) {
            try {
                interfaz.printTVar("[" + colaEscritor.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printLVar("}");
        
        interfaz.printTVar("Historial de frames recibidos: { ");
        for (int i = HistorialRecibidosB.size() - 1; i > HistorialRecibidosB.size() - 21; i--) {
            try {
                interfaz.printTVar("[" + HistorialRecibidosB.get(i).getNumero() + "] ");
            } catch (IndexOutOfBoundsException e) {

            }

        }
        interfaz.printLVar("}");

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
       

        reloj = 0;
        aLibre = true;
        escritorLibre = true;
        colaA.clear();
        
        colaB.clear();
        HistorialRecibidosB.clear();
        for (Evento e : evento) {
            e.setHoraOcurrencia(Double.MAX_VALUE);
        }
        LlegaMsjA.getInstance().setHoraOcurrencia(0);
        actual = evento[0];
    }

    private void imprimirEstadisticasUnaSimulacion(int vez) {
        interfaz.printLEstad("Simulación: " + vez);
        interfaz.printLEstad(this.estadisticador.estadisticasLocales());
        interfaz.printLEstad("---");
    }

    private void imprimirEstadisticasGlobales() {
        interfaz.printLEstad("Estadísticas globales");
        interfaz.printLEstad(this.estadisticador.estadisticasGlobales());
        interfaz.printLEstad("---");
        interfaz.printLEstad("Fin");
    }

}
