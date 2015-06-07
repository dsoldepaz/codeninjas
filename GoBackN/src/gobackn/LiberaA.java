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
public class LiberaA extends Evento {

    private static LiberaA instance = null;
    private Distribuidor distribuidor;
    private GoBackN master;
    private VenceTimer venceTimer;
    private LlegaFrameB llegaFrameB;

    protected LiberaA() {

    }

    public static LiberaA getInstance() {
        if (instance == null) {
            instance = new LiberaA();
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
        double Y = distribuidor.distribucionConvertirMensaje();
        master.reloj = horaOcurrencia;
        
        
        if (master.colaEnviador.isEmpty()) {
            master.aLibre = true;
            horaOcurrencia = Double.MAX_VALUE;
        } 
        else {
            this.horaOcurrencia = master.reloj + Y +1;
            master.aLibre = false;
            Mensaje m = master.colaEnviador.get(0);
            master.timer[master.ventana.indexOf(m)] =  master.timer[master.ventana.indexOf(m)] = master.reloj + master.tTimer + Y +1;;
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
                llegaFrameB.horaOcurrencia = master.reloj + Y + 2;
                master.mensajesEnviados.add(m);
            }
            if (estado == Distribuidor.EstadoMensaje.PERDIDO) {
                m.setEnviado(false);
                m.setConError(false);
            }
            master.colaEnviador.remove(m);
            venceTimer.setHoraOcurrencia(master.getMinimumValue(master.timer));
        }
    }

    @Override
    public String getNombre() {
        return "Se libera A";
    }

}
