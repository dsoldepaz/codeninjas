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
   
   protected LiberaA() {
      distribuidor  = Distribuidor.getInstance();
      venceTimer = VenceTimer.getInstance();
   }
   public static LiberaA getInstance() {
      if(instance == null) {
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
        return horaOcurrencia=val;
    }

    @Override
    public void ejecutar() {
        master = GoBackN.getInstance();
        double Y = distribuidor.distribucionConvertirMensaje();
        if(master.colaEnviador.isEmpty()){
             master.aLibre= true;
         }
         else{
            this.horaOcurrencia = master.reloj + distribuidor.distribucionConvertirMensaje();
            master.aLibre= false;
            
            //poner el timer
         }
    }
    @Override
    public String getNombre() {
        return "Se libera A";
    }
    
}
