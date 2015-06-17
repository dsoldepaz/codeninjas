/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

/**
 *
 * @author b06171
 */
public class LlegaMsjA extends Evento {

    private static LlegaMsjA instance = null;
    private GoBackN master;
    private Distribuidor distribuidor;
    private VenceTimer venceTimer;
    private LlegaFrameB llegaFrameB;
    private LiberaA liberaA;

    protected LlegaMsjA() {

    }

    public static LlegaMsjA getInstance() {
        if (instance == null) {
            instance = new LlegaMsjA();
        }
        return instance;
    }

    @Override
    public double getHoraOcurrencia() {
        return horaOcurrencia;
    }

    @Override
    public double setHoraOcurrencia(double val) {
        return horaOcurrencia = val;
    }

    @Override
    public void ejecutar() {
        master = GoBackN.getInstance();
        distribuidor = Distribuidor.getInstance();
        venceTimer = VenceTimer.getInstance();
        llegaFrameB = LlegaFrameB.getInstance();
        liberaA = LiberaA.getInstance();

        double Y = distribuidor.distribucionConvertirMensaje();
        double X = distribuidor.distribucionLlegaMensajeA();
        master.estadisticador.tiempoUltimoAgregado = master.reloj;
        master.reloj = horaOcurrencia;
        ++master.numeroMsj;
        Mensaje nuevoMsj = new Mensaje(master.numeroMsj);
        double tam= (double)(master.colaA.size() + master.ventana.size());
        master.estadisticador.tamanyoLista.add(tam);
        master.estadisticador.tiempoTamanyoLista.add(master.reloj - master.estadisticador.tiempoUltimoAgregado);
        if (master.ventana.size() >= 8) {
            master.colaA.add(nuevoMsj);
        } else {
            master.ventana.add(nuevoMsj);
            master.colaEnviador.add(nuevoMsj);
            if (master.aLibre) {
                master.aLibre = false;
                Mensaje m = master.colaEnviador.get(0);
                master.timer[master.ventana.indexOf(m)] = master.reloj + master.tTimer + X + 1;
                liberaA.horaOcurrencia = master.reloj + Y + X + 1;
                Distribuidor.EstadoMensaje estado = distribuidor.distribucionLlegaMensajeB();
                if (estado == Distribuidor.EstadoMensaje.ERROR || estado == Distribuidor.EstadoMensaje.LLEGO) {
                    if (estado == Distribuidor.EstadoMensaje.ERROR) {
                        m.setConError(true);
                        m.setEnviado(true);
                    }
                    if (estado == Distribuidor.EstadoMensaje.LLEGO) {
                        m.setEnviado(true);
                        m.setConError(false);
                    }
                    
                    llegaFrameB.horaOcurrencia = master.reloj + X + Y + 2;
                    m.tiempoDeTransferencia += llegaFrameB.horaOcurrencia - master.reloj; 
                    venceTimer.setHoraOcurrencia(master.getMinimumValue(master.timer));
                    master.colaB.add(m);
                }
            }
        }
       
        this.horaOcurrencia = master.reloj + X;
    }

    @Override
    public String getNombre() {
        return "Llega Mensaje a A";
    }

}
