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
        liberaA =LiberaA.getInstance();
        llegaFrameB = LlegaFrameB.getInstance();
        double Y = distribuidor.distribucionConvertirMensaje();
        int timerMenor = 0;
        master.reloj= horaOcurrencia;
        
        //solo puede vencerse el primero y de igual forma hay que enviarlos todos
        for(Mensaje m: master.ventana){
            master.colaEnviador.add(m);
        }
        for (int i = 0 ; i < master.timer.length; ++i) {
            master.timer[i] = Double.MAX_VALUE;
        }        
        for(int i = 1; i < master.timer.length; ++i){
            if(master.timer[timerMenor] > master.timer[i] )
                timerMenor = i;
        }
        if(master.aLibre){
            master.aLibre=false;            
            master.timer[timerMenor] = master.reloj + master.tTimer + Y;
            liberaA.horaOcurrencia=master.reloj+Y+1;
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
                master.timer[timerMenor] = Double.MAX_VALUE;
                
        }
        horaOcurrencia = master.reloj + master.getMinimumValue(master.timer);
    }

    @Override
    public String getNombre() {
        return "Se vence timer";
    }

}
