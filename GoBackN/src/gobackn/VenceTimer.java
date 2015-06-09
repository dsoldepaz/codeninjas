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
public class VenceTimer extends Evento {

    private static VenceTimer instance = null;
    private GoBackN master;
    private Distribuidor distribuidor;
    private LiberaA liberaA;
    private LlegaFrameB llegaFrameB;

    protected VenceTimer() {
    }

    public static VenceTimer getInstance() {
        if (instance == null) {
            instance = new VenceTimer();
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
        liberaA=LiberaA.getInstance();
        llegaFrameB = LlegaFrameB.getInstance();
        double Y = distribuidor.distribucionConvertirMensaje();
        
        master.reloj= horaOcurrencia;
        for(Mensaje m: master.ventana){
            m.setEnviado(false);
        }
        if(master.aLibre){
            master.aLibre=false;            
            master.timer[0] = master.reloj + master.tTimer + Y;
            liberaA.setHoraOcurrencia(master.reloj+Y+1);
            Distribuidor.EstadoMensaje estado = distribuidor.distribucionLlegaMensajeB();
            if (estado == Distribuidor.EstadoMensaje.ERROR || estado == Distribuidor.EstadoMensaje.LLEGO) {
                if (estado == Distribuidor.EstadoMensaje.ERROR) {
                    master.ventana.get(0).setConError(true);                    
                }
                if (estado == Distribuidor.EstadoMensaje.LLEGO) {
                    master.ventana.get(0).setConError(false);
                }
                master.ventana.get(0).setEnviado(true);
                master.colaB.add(master.ventana.get(0));
                llegaFrameB.horaOcurrencia = master.reloj + Y + 2;                
            }
        }else{
                master.colaA.add(master.ventana.get(0));
        }
        
    }

    @Override
    public String getNombre() {
        return "Se vence timer";
    }

}
